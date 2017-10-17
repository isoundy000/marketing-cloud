package cn.rongcapital.mkt.job.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.DataPartyTagRuleMapDao;
import cn.rongcapital.mkt.dao.TagDao;
import cn.rongcapital.mkt.dao.TagGroupMapDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.job.service.vo.TagRuleExtraVO;
import cn.rongcapital.mkt.po.DataPartyTagRuleMap;
import cn.rongcapital.mkt.po.TagGroupMap;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.po.mongodb.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class DataPartyTagSyncMongoTaskImpl implements TaskService {
	
	private static Logger logger = LoggerFactory.getLogger(DataPartyTagSyncMongoTaskImpl.class);

	@Autowired
	private TagGroupMapDao tagGroupMapDao;

	@Autowired
	private DataPartyTagRuleMapDao dataPartyTagRuleMapDao;

	@Autowired
	private TagDao tagDao;

	@Autowired
	private MongoTemplate mongoTemplate;

	private static final int pageSize = 100;

    private ScriptEngine nashornEngine = new ScriptEngineManager().getEngineByName("nashorn");

    private Lock lock = new ReentrantLock();

    private String JS_ENGINE_DATA_FIELD = "dataValue";

	@Override
	public void task(Integer taskId) {
        Map<String, List<TagRuleExtraVO>> tagRuleMap = getDataPartyTagRuleMap();
        if(tagRuleMap == null || tagRuleMap.isEmpty()) {
            return;
        }
		long totalRecord = mongoTemplate.count(null, DataParty.class);
        long totalPage = (totalRecord + pageSize -1) / pageSize;
		for(int index = 0; index < totalPage; index++) {
			List<DataParty> dataPartyList = mongoTemplate.find(
                    new Query().skip(index * pageSize).limit(pageSize), DataParty.class);
			if(CollectionUtils.isEmpty(dataPartyList)) {
				break;
			}
            for(DataParty dp : dataPartyList) {
                try {
                    setTagToMongoDataParty(dp, tagRuleMap);
                } catch (Exception e) {
                    logger.error(e.getMessage(),e);
                }
            }
		}
	}
	
	private Map<String, List<TagRuleExtraVO>> getDataPartyTagRuleMap() {
		DataPartyTagRuleMap dataPartyTagRuleMapT = new DataPartyTagRuleMap();
		dataPartyTagRuleMapT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        dataPartyTagRuleMapT.setStartIndex(null);
        dataPartyTagRuleMapT.setPageSize(null);
		List<DataPartyTagRuleMap> dataPartyTagRuleMapList = dataPartyTagRuleMapDao.selectList(dataPartyTagRuleMapT);
        if (CollectionUtils.isEmpty(dataPartyTagRuleMapList)) {
            return null;
        }
        Map<String, Tag> tagInfoMap = new HashMap<>();
        Map<String, List<TagRuleExtraVO>> tagRuleMap = new HashMap<>();
        for (DataPartyTagRuleMap tempDataPartyTagRuleMap : dataPartyTagRuleMapList) {
            String fileName = tempDataPartyTagRuleMap.getFieldName().toLowerCase();
            if (fileName == null || fileName.trim().equals("")) {
                continue;
            }
            List<TagRuleExtraVO> tempRuleList = tagRuleMap.get(fileName);
            if (tempRuleList == null) {
                tempRuleList = new ArrayList<>();
                tagRuleMap.put(fileName, tempRuleList);
            }
//            String tagName = tempDataPartyTagRuleMap.getTagName();
//            Tag tagInfo = tagInfoMap.get(tagId);
//            if (tagInfo == null) {
//                tagInfo = new Tag();
//                tagInfo.setTagId(tagId.toString());
//
//                cn.rongcapital.mkt.po.Tag tagOfMysqlT = new cn.rongcapital.mkt.po.Tag();
//                tagOfMysqlT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
//                tagOfMysqlT.setId(tagId);
//                List<cn.rongcapital.mkt.po.Tag> tagOfMysqlList =  tagDao.selectList(tagOfMysqlT);
//                if(CollectionUtils.isNotEmpty(tagOfMysqlList)) {
//                    tagInfo.setTagName(tagOfMysqlList.get(0).getName());
//                }
//                TagGroupMap tagGroupMapT = new TagGroupMap();
//                tagGroupMapT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
//                tagGroupMapT.setTagId(tagId);
//                List<TagGroupMap> tagGroupMapList = tagGroupMapDao.selectList(tagGroupMapT);
//                if (CollectionUtils.isNotEmpty(tagGroupMapList)) {
//                    tagInfo.setTagGroupId(tagGroupMapList.get(0).getGroupId());
//                }
//
//                tagInfoMap.put(tagId.toString(), tagInfo);
            //}

//            TagRuleExtraVO tagRuleExtraVO = new TagRuleExtraVO();
//            tagRuleExtraVO.setRuleMap(tempDataPartyTagRuleMap);
//            tagRuleExtraVO.setTag(tagInfo);
//            tempRuleList.add(tagRuleExtraVO);
        }

		return tagRuleMap;
	}

	/**
	 * 给mongodb里的data_party表里增加tagList
	 * @param dp
	 * @throws  
	 * @throws IllegalArgumentException 
	 */
	private void setTagToMongoDataParty(DataParty dp, Map<String, List<TagRuleExtraVO>> tagRuleMap) throws Exception {
		Integer mid = dp.getMid();
		if(mid == null) {
			return;
		}
		Field fields[] = DataParty.class.getDeclaredFields();
		List<Tag> tagList = new ArrayList<Tag>();
		for(Field f : fields) {
			f.setAccessible(true);
			String fieldName = f.getName().toLowerCase();
            List<TagRuleExtraVO> ruleList = tagRuleMap.get(fieldName);
            if (CollectionUtils.isEmpty(ruleList)) {
                continue;
            }
            Object dataValue = f.get(dp);
			for(TagRuleExtraVO tagRuleExtraVO : ruleList) {
                DataPartyTagRuleMap dataPartyTagRuleMap = tagRuleExtraVO.getRuleMap();
                Tag tag = tagRuleExtraVO.getTag();
                if(isMatchTagRule(dataValue, dataPartyTagRuleMap)) {
                    tagList.add(tag);
                }
			}
		}
		if(CollectionUtils.isNotEmpty(tagList)) {
			Update update = new Update().set("tagList", tagList);
			Criteria criteria = Criteria.where("mid").is(mid);
			mongoTemplate.updateMulti(new Query(criteria),update,DataParty.class);
		}
	}
	
	private boolean isMatchTagRule(Object dataValue, DataPartyTagRuleMap dataPartyTagRuleMap) {
        String fieldValueOfRule = dataPartyTagRuleMap.getFieldValue();
        Byte ruleType = dataPartyTagRuleMap.getRuleType();
        if (ApiConstant.DATA_PARTY_TAG_RULE_TYPE_COMMON == ruleType) {
            if(fieldValueOfRule == null) {
                return dataValue == null;
            } else if(dataValue == null) {
                return false;
            } else {
                if (dataValue instanceof String) {
                    return fieldValueOfRule.equals(dataValue);
                } else {
                    return dataValue.toString().equals(fieldValueOfRule);
                }
            }
        } else if(ApiConstant.DATA_PARTY_TAG_RULE_TYPE_JS == ruleType) {
            try {
                lock.lock();
                SimpleBindings bindings = new SimpleBindings();
                bindings.put(JS_ENGINE_DATA_FIELD, dataValue);
                return (boolean) nashornEngine.eval(fieldValueOfRule, bindings);
            } catch (ScriptException e) {
                logger.error("parse tag rule error!", e);
                return false;
            } finally {
                lock.unlock();
            }
        } else {
            return false;
        }
	}
	
}
