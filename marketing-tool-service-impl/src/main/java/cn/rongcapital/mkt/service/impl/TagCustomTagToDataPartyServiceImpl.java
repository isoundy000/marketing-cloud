package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.po.base.BaseTag;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.service.TagCustomTagToDataPartyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Yunfeng on 2016-9-23.
 */
@Service
public class TagCustomTagToDataPartyServiceImpl implements TagCustomTagToDataPartyService{

    private Logger logger = LoggerFactory.getLogger(TagCustomTagToDataPartyServiceImpl.class);
    private ConcurrentHashMap<String, Field[]> filedMap = new ConcurrentHashMap<>();

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public boolean tagCustomTagToDataParty(DataParty dataParty, BaseTag baseTag) {
        if(baseTag == null || dataParty == null || baseTag.getTagId() == null || dataParty.getMid() == null) return false;
        Query query = new Query(Criteria.where("mid").is(dataParty.getMid()));
        List<String> customTagList = dataParty.getCustomTagList();
        if(CollectionUtils.isEmpty(customTagList)){
            customTagList = new ArrayList<>();
            dataParty.setCustomTagList(customTagList);
        }
        if(!customTagList.contains(baseTag.getTagId())){
            customTagList.add(baseTag.getTagId());
            mongoTemplate.updateFirst(query,buildBaseUpdate(dataParty),DataParty.class);
            return true;
        }
        return false;
    }

    @Override
    public boolean tagCustomTagToDataPartyById(String tagId, Integer dataPartyId) {
        Query dataPartyQuery = new Query(Criteria.where("mid").is(dataPartyId));
        DataParty targetDataParty = mongoTemplate.findOne(dataPartyQuery,DataParty.class);
        if(targetDataParty == null) return false;
        Query baseTagQuery = new Query(Criteria.where("tag_id").is(tagId));
        BaseTag baseTag = mongoTemplate.findOne(baseTagQuery,BaseTag.class);
        if(baseTag == null) return false;
        return tagCustomTagToDataParty(targetDataParty,baseTag);
    }


    private <T> Update buildBaseUpdate(T t) {
        Update update = new Update();
        String className = t.getClass().getName();
        Field[] fields = filedMap.get(className);
        if (fields == null) {
            fields = t.getClass().getDeclaredFields();
            filedMap.putIfAbsent(className, fields);
        }
        for (Field field : fields) {
            if(field.getName().equals("serialVersionUID")) continue;
            field.setAccessible(true);
            try {
                Object value = field.get(t);
                if (value != null) {
                    update.set(field.getName(), value);
                }
            } catch (Exception e) {
                logger.error("buildBaseUpdate failed", e);
            }
        }
        return update;
    }
}
