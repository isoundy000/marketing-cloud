package cn.rongcapital.mkt.service.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.util.GenerateUUid;
import cn.rongcapital.mkt.mongodb.TagRecommendRepository;
import cn.rongcapital.mkt.mongodb.TagTreeRepository;
import cn.rongcapital.mkt.po.mongodb.TagRecommend;
import cn.rongcapital.mkt.po.mongodb.TagTree;
import cn.rongcapital.mkt.service.AnalysisTagFileService;

@Service
public class AnalysisTagFileServiceImpl implements AnalysisTagFileService{
    
    private static final Integer STATUS_VALID = 0;
    private static final Integer STATUS_INVALID = 1;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private TagRecommendRepository tagRecommendRepository;

	@Autowired
	private TagTreeRepository tagTreeRepository;

	@Autowired
	MongoOperations mongoOperations;

	/**
	 * Read the Excel 2010
	 * 
	 * @param path
	 *            the path of the excel file
	 * @return
	 * @throws IOException
	 * 
	 */
	@Override
	public List<TagTree> readXlsx(String path) throws IOException {
	    
	    // 删除一级二级标签的孩子
        mongoOperations.updateMulti(new Query(Criteria.where("_id").exists(true)), new Update().unset("children"), TagTree.class);
        
        // 设置数据为逻辑删除
        mongoOperations.updateMulti(new Query(Criteria.where("_id").exists(true)), new Update().set("status", STATUS_INVALID), TagTree.class);
        mongoOperations.updateMulti(new Query(Criteria.where("_id").exists(true)), new Update().set("status", STATUS_INVALID), TagRecommend.class);
	    
		InputStream is = new FileInputStream(path);
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		// Read the Sheet
		for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			if (xssfSheet == null) {
				continue;
			}
			// Read the Row
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				String uuid1 = "";
				String uuid2 = "";
				String uuid3 = "";
				if (xssfRow != null) {
					
					// 一级标签
					XSSFCell tagZero = xssfRow.getCell(0);
					String oneTagName = getCellValue(tagZero).trim();
					
					// 标签
					XSSFCell tagTwo = xssfRow.getCell(2);

					// 标签描述
					XSSFCell tagThree = xssfRow.getCell(3);
					// 标签内容
					XSSFCell tagFour = xssfRow.getCell(4);
					// 标签来源
					XSSFCell tagFive = xssfRow.getCell(5);
					// 是否推荐标签
					XSSFCell tagSix = xssfRow.getCell(6);
					
					String tagSource = getCellValue(tagSix);
					// 是否推荐标签
					XSSFCell tagSeven = xssfRow.getCell(7);
					
					// 排序
					XSSFCell tagEight = xssfRow.getCell(8);
					
					// 搜索模型
                    XSSFCell tagNine = xssfRow.getCell(9);

					if (tagTwo != null && tagFive != null) {
						String tagName = getCellValue(tagTwo).trim();
//						// 标签存在跳出本次循环
//						boolean flag = findTagRecommend(tagName);
//						if (flag)
//							continue;

						String tagNameEng = getCellValue(tagThree).trim();
						String tagDesc = getCellValue(tagFour).trim();
						String tagValue = getCellValue(tagFive).trim();

						String tagFlag = getCellValue(tagSeven).trim();
						String seq = getCellValue(tagEight).trim();
						String searchMod = getCellValue(tagNine).trim();
						uuid3 = insertTagRecommendMongoDB(tagName, tagNameEng, tagDesc, tagValue, tagSource, tagFlag, seq, searchMod);
//						uuid3 = tagName;
					}
					// -------------------------------------------------
					// 二级标签插入
					XSSFCell tagOne = xssfRow.getCell(1);

					if (tagOne != null) {
						String twoTagName = getCellValue(tagOne);
						String tagId = findTagTree(twoTagName);
						if ("".equals(tagId)) {
							ArrayList<String> arrayList = new ArrayList<String>();
							arrayList.add(uuid3);
							uuid2 = getTagTree(twoTagName, 2, null, oneTagName, arrayList, tagSource);
//							uuid2 = twoTagName;
						} else {
							// 更新Children标签
							TagTree tagTree = getTagTree(twoTagName);
							List<String> childrenLists = tagTree.getChildren();
							if(childrenLists != null && uuid3.length() > 0) {
								if(childrenLists.contains(uuid3) == false) {
									childrenLists.add(uuid3);
									uuid2 = getTagTree(twoTagName, 2, null, oneTagName, childrenLists, tagSource);
//									uuid2 = twoTagName;
								} else {} 
							}else {
								childrenLists = new ArrayList<String>();
								childrenLists.add(uuid3);
								uuid2 = getTagTree(twoTagName, 2, null, oneTagName, childrenLists, tagSource);
//								uuid2 = twoTagName;
							}
						}
					}
					
					
					// 一级标签插入
					if (tagZero != null) {
						String tagId = findTagTree(oneTagName);
						if ("".equals(tagId)) {
							ArrayList<String> arrayList = new ArrayList<String>();
							arrayList.add(uuid2);
							uuid1 = getTagTree(oneTagName, 1, null, null, arrayList, tagSource);

						} else {
							// 更新Children标签
							TagTree tagTree = getTagTree(oneTagName);
							List<String> childrenLists = tagTree.getChildren();
							if(childrenLists != null && uuid2.length() > 0) {
								if(childrenLists.contains(uuid2) == false) {
								    childrenLists.add(uuid2);
									getTagTree(oneTagName, 1, null, null, childrenLists, tagSource);
								} else {}
    						} else {
    							childrenLists = new ArrayList<String>();
    							childrenLists.add(uuid2);
    							getTagTree(oneTagName, 1, null, null, childrenLists, tagSource);
    						}
						}
					}
				}
			}
		}
		xssfWorkbook.close();
		
		mongoOperations.remove(new Query(Criteria.where("status").is(STATUS_INVALID)), TagTree.class);
		mongoOperations.remove(new Query(Criteria.where("status").is(STATUS_INVALID)), TagRecommend.class);
		
		return null;
	}

	public String findTagTree(String tagName) {
		TagTree tagTree = mongoOperations.findOne(new Query(Criteria.where("tag_name").is(tagName)), TagTree.class);

		if (tagTree != null) {
			return tagTree.getTagId();
		}
		return "";
	}
	
	/**
	 * 根据tagName获取TagTree
	 * @param tagName
	 * @return
	 */
	public TagTree getTagTree(String tagName) {
		TagTree tagTree = mongoOperations.findOne(new Query(Criteria.where("tag_name").is(tagName)), TagTree.class);

		return tagTree;
	}

	public boolean findTagRecommend(String tagName) {

		List<TagRecommend> findByTagName = tagRecommendRepository.findByTagName(tagName);
		if (CollectionUtils.isNotEmpty(findByTagName))
			return true;
		return false;

	}

	public String getTagTree(String tagName, int level, String path, String parent, List<String> children,
			String source) {
		TagTree tagTree = new TagTree();

		String uuid = GenerateUUid.generateShortUuid();

		tagTree.setTagName(tagName);
		tagTree.setLevel(level);
		tagTree.setPath(path);
		tagTree.setParent(parent);
		tagTree.setChildren(children);
		tagTree.setSource(source);
		tagTree.setStatus(STATUS_VALID);
		tagTree.setCreateTime(new Date());
		tagTree.setUpdateTime(new Date());

		TagTree tagTreeByTagName = getTagTree(tagName);
		if(tagTreeByTagName == null) {
			// 如果不存在，新插入
			tagTree.setTagId(uuid);
			tagTreeRepository.insert(tagTree);
		} else {
		    uuid = tagTreeByTagName.getTagId();
			// 如果已经存在，修改
			tagTree.setId(tagTreeByTagName.getId());
			tagTree.setTagId(uuid);
			tagTree.setCreateTime(tagTreeByTagName.getCreateTime());
			tagTreeRepository.save(tagTree);
			
		}
		return uuid;

	}

	public String insertTagRecommendMongoDB(String tagName, String tagNameEng, String tagDesc, String tagValue, String source,
			String flag, String seq , String searchMod) {
		TagRecommend tagRecommend = new TagRecommend();

		String uuid = GenerateUUid.generateShortUuid();

		tagRecommend.setTagId(uuid);
		tagRecommend.setTagName(tagName);
		tagRecommend.setTagNameEng(tagNameEng);
		tagRecommend.setStatus(STATUS_VALID);
		if ("true".equals(flag.toLowerCase())) {
			tagRecommend.setFlag(true);
		} else if ("false".equals(flag.toLowerCase())) {
			tagRecommend.setFlag(false);
		} else {
			tagRecommend.setFlag(false);
		}
		tagRecommend.setTagDesc(tagDesc);
		tagRecommend.setCreateTime(new Date());
		tagRecommend.setUpdateTime(new Date());
		tagRecommend.setSource(source);
		tagRecommend.setSeq(Integer.valueOf(seq));
		tagRecommend.setSearchMod(Integer.valueOf(searchMod));

		String[] split = tagValue.split("/");
		List<String> tagList = java.util.Arrays.asList(split);
		tagRecommend.setTagList(tagList);
		
		List<TagRecommend> tagRecommendLists = tagRecommendRepository.findByTagName(tagName);
		
		if(tagRecommendLists != null && tagRecommendLists.size() > 0) {
		    tagRecommend.setId(tagRecommendLists.get(0).getId());
		    uuid = tagRecommendLists.get(0).getTagId();
		    tagRecommend.setTagId(uuid);
		    tagRecommend.setFlag(tagRecommendLists.get(0).getFlag());
		    tagRecommend.setCreateTime(tagRecommendLists.get(0).getCreateTime());
		    mongoOperations.save(tagRecommend);
		} else {
		    tagRecommendRepository.insert(tagRecommend);
		}

		return uuid;
	}

	private String getCellValue(XSSFCell cell) {
		String cellValue = "";
		DataFormatter formatter = new DataFormatter();
		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					cellValue = formatter.formatCellValue(cell);
				} else {
					double value = cell.getNumericCellValue();
					int intValue = (int) value;
					cellValue = value - intValue == 0 ? String.valueOf(intValue) : String.valueOf(value);
				}
				break;
			case Cell.CELL_TYPE_STRING:
				cellValue = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				cellValue = String.valueOf(cell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA:
				cellValue = String.valueOf(cell.getCellFormula());
				break;
			case Cell.CELL_TYPE_BLANK:
				cellValue = "";
				break;
			case Cell.CELL_TYPE_ERROR:
				cellValue = "";
				break;
			default:
				cellValue = cell.toString().trim();
				break;
			}
		}
		return cellValue.trim();
	}

}
