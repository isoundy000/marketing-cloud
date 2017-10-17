package cn.rongcapital.mkt.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.CustomTagDao;
import cn.rongcapital.mkt.dao.CustomTagMapDao;
import cn.rongcapital.mkt.dao.TagDao;
import cn.rongcapital.mkt.dao.TagGroupMapDao;
import cn.rongcapital.mkt.dao.TaggroupDao;
import cn.rongcapital.mkt.dao.mongo.MongoBaseTagDao;
import cn.rongcapital.mkt.dao.mongo.MongoBaseTagDaoImpl;
import cn.rongcapital.mkt.mongodb.TagRecommendRepository;
import cn.rongcapital.mkt.mongodb.TagTreeRepository;
import cn.rongcapital.mkt.po.Tag;
import cn.rongcapital.mkt.po.TagGroupMap;
import cn.rongcapital.mkt.po.Taggroup;
import cn.rongcapital.mkt.po.base.BaseTag;
import cn.rongcapital.mkt.po.mongodb.TagRecommend;
import cn.rongcapital.mkt.po.mongodb.TagTree;
import cn.rongcapital.mkt.service.GroupTagsSearchService;
import cn.rongcapital.mkt.vo.out.SerarchTagGroupTagsDataOut;
import cn.rongcapital.mkt.vo.out.SerarchTagGroupTagsOut;
import cn.rongcapital.mkt.vo.out.TagOut;

@Service
public class GroupTagsSearchServiceImpl implements GroupTagsSearchService {

	@Autowired
	private TagDao tagDao;
	@Autowired
	private TaggroupDao taggroupDao;
	@Autowired
	private TagGroupMapDao tagGroupMapDao;

	@Autowired
	private CustomTagDao customTagDao;
	
	@Autowired
	private MongoBaseTagDaoImpl mongoBaseTagDao;
	
	
	@Autowired
	private TagTreeRepository tagTreeRepository;
	
	@Autowired
	private TagRecommendRepository tagRecommendRepository;

	@Autowired
	private CustomTagMapDao customTagMapDao;

	public SerarchTagGroupTagsOut groupTagsSearch(String method, String userToken, String tagGroupName) {
		SerarchTagGroupTagsOut out = new SerarchTagGroupTagsOut(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO);
		Taggroup taggroup = new Taggroup();
		taggroup.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		taggroup.setLevel(ApiConstant.INT_ONE);
		if (StringUtils.isNotBlank(tagGroupName)) {
			taggroup.setName(tagGroupName);
		}
		
		SerarchTagGroupTagsDataOut serarchTagGroupTagsDataOut = new SerarchTagGroupTagsDataOut();
		
		//获得系统标签
		List<TagTree> tagTrees=tagTreeRepository.findByLevelAndStatusAndTagNameLike(ApiConstant.INT_ONE, ApiConstant.TABLE_DATA_STATUS_VALID,tagGroupName);
		
		if(tagTrees!=null && tagTrees.size()>0) {
			for(TagTree tgt:tagTrees){

				List<String> childen=tgt.getChildren();
				for(String childTagId:childen){					
										
					List<TagRecommend> tagList=tagRecommendRepository.findByTagId(childTagId);
					if (null != tagList && tagList.size() > 0) {
						TagOut tagOut = new TagOut();
						tagOut.setTagId(childTagId);
						tagOut.setTagName(tagList.get(0).getTagName());
						serarchTagGroupTagsDataOut.getTagList().add(tagOut);
					}
				}				
			}			
		}	
		
		//获得自定义标签
		List<BaseTag> customTags =mongoBaseTagDao.findCustomTagLeafListByFuzzyTagName(tagGroupName);
		if(customTags!=null && customTags.size()>0) {
			for(BaseTag customTag:customTags){
				TagOut tagOut = new TagOut();
				tagOut.setTagId(customTag.getTagId());
				tagOut.setTagName(customTag.getTagName());				
				serarchTagGroupTagsDataOut.getTagList().add(tagOut);
			}
		}
			
		out.getDataCustom().add(serarchTagGroupTagsDataOut);		
		out.setTotal(out.getDataCustom().size());
		return out;
	}
}
