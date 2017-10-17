package cn.rongcapital.mkt.dao.mongo;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.po.base.BaseTag;
import cn.rongcapital.mkt.po.mongodb.CustomTagLeaf;
import cn.rongcapital.mkt.po.mongodb.CustomTagTypeLayer;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Yunfeng on 2016-9-21.
 */
@Repository
public class MongoBaseTagDaoImpl implements MongoBaseTagDao{

    private Logger logger = LoggerFactory.getLogger(MongoBaseTagDaoImpl.class);
    private final String TAG_NAME="tag_name";
    private final String PATH = "path";
    private final String SOURCE = "source";
    private final String TAG_TYPE = "tag_type";
    private final String TAG_ID = "tag_id";
    private final String SERIAL_VERSION_UID = "serialVersionUID";
    private final String CUSTOM_TAG_LIST = "custom_tag_list";

    @Autowired
    private MongoTemplate mongoTemplate;

    private ConcurrentHashMap<String, Field[]> filedMap = new ConcurrentHashMap<>();

    @Override
    public boolean insertBaseTagDao(BaseTag baseTag) {
        Query checkQuery = null;
        if(baseTag instanceof CustomTagTypeLayer){
            checkQuery = new Query(Criteria.where(TAG_NAME).is(baseTag.getTagName()).and(PATH).is(baseTag));
            mongoTemplate.upsert(checkQuery,buildBaseUpdate(baseTag),BaseTag.class);
        }else{
            checkQuery = new Query(Criteria.where(TAG_NAME).is(baseTag.getTagName()).and(PATH).is(baseTag.getPath()).and(SOURCE).is(baseTag.getSource()));
            mongoTemplate.upsert(checkQuery,buildBaseUpdate(baseTag),BaseTag.class);
        }
        BaseTag insertedTag = null;
        Query query = new Query(Criteria.where(TAG_NAME).is(baseTag.getTagName()).and(PATH).is(baseTag.getPath()));
        insertedTag = mongoTemplate.findOne(query,BaseTag.class);
        if(insertedTag == null) return false;
        return true;
    }

    @Override
    public BaseTag findOneBaseTag(BaseTag baseTag) {
        BaseTag targetTag = null;
        if(baseTag instanceof CustomTagTypeLayer){
            Query query = null;
            if(baseTag.getPath() != null){
                query = new Query(Criteria.where(TAG_NAME).is(baseTag.getTagName()).and(PATH).is(baseTag.getPath()));
            }else{
                query = new Query(Criteria.where(TAG_NAME).is(baseTag.getTagName()));
            }
            targetTag = mongoTemplate.findOne(query,BaseTag.class);
        }else if(baseTag instanceof CustomTagLeaf){
            Query query = new Query(Criteria.where(TAG_NAME).is(baseTag.getTagName()).and(PATH).is(baseTag.getPath()).and(SOURCE).is(baseTag.getSource()));
            targetTag = mongoTemplate.findOne(query,BaseTag.class);
        }
        return targetTag;
    }

    @Override
    public List<BaseTag> findBaseTagListByTagType(Integer tagType) {
        Query query = new Query(Criteria.where(TAG_TYPE).is(tagType));
        List<BaseTag> baseTags = mongoTemplate.find(query,BaseTag.class);
        return baseTags;
    }
    
    /**
     * 根据自定义标签名，模糊查询所有叶子节点的自定义标签
     */
    @Override
    public List<BaseTag> findCustomTagLeafListByFuzzyTagName(String tagName)
    {
    	Query query = new Query(Criteria.where(TAG_TYPE).is(ApiConstant.CUSTOM_TAG_LEAF_TYPE).and(TAG_NAME).regex(tagName));
        List<BaseTag> baseTags = mongoTemplate.find(query,BaseTag.class);
        return baseTags;
    }

    @Override
    public BaseTag updateBaseTag(BaseTag baseTag) {
        BaseTag updatedTag = null;
        Query query = new Query(Criteria.where(TAG_NAME).is(baseTag.getTagName()).and(PATH).is(baseTag.getPath()));
        Update update = buildBaseUpdate(baseTag);
        mongoTemplate.updateFirst(query,update,baseTag.getClass());
        return null;
    }

    @Override
    public BaseTag findCustomTagLeafByTagId(String tagId) {
        BaseTag targetTag = null;
        Query query = new Query(Criteria.where(TAG_ID).is(tagId));
        targetTag = mongoTemplate.findOne(query,BaseTag.class);
        return targetTag;
    }

    @Override
    public List<DataParty> findMDataByTagId(String tagId, Integer startIndex, Integer pageSize) {
        List<DataParty> dataPartyList = null;
        Query query = new Query(Criteria.where(CUSTOM_TAG_LIST).is(tagId));
        if(startIndex == null || pageSize == null){
            dataPartyList = mongoTemplate.find(query,DataParty.class);
        }else{
            query = query.skip(startIndex).limit(pageSize);
            dataPartyList = mongoTemplate.find(query,DataParty.class);
        }
        return dataPartyList;
    }

    @Override
    public Long findTotalMDataCount(String tagId) {
        Long totalCount = null;
        Query query = new Query(Criteria.where(CUSTOM_TAG_LIST).is(tagId));
        totalCount = mongoTemplate.count(query,DataParty.class);
        return totalCount;
    }
    
    /*
     * 根据tag_name和tag_source查询custom_tag
     * @see cn.rongcapital.mkt.dao.mongo.MongoBaseTagDao#findOneCustomTagBySource(cn.rongcapital.mkt.po.base.BaseTag)
     */
    @Override    
    public BaseTag findOneCustomTagBySource(BaseTag baseTag)
    {
         BaseTag targetTag = null;
         if(baseTag instanceof CustomTagTypeLayer){            
             Query query = new Query(Criteria.where(TAG_NAME).is(baseTag.getTagName()).and(SOURCE).is(baseTag.getSource()));
             targetTag = mongoTemplate.findOne(query,BaseTag.class);        
         }
         return targetTag;         
    }

    @Override
    public void deleteCustomTag(BaseTag baseTag) {
        if(baseTag == null || StringUtils.isEmpty(baseTag.getTagName()) || StringUtils.isEmpty(baseTag.getPath())) return;
        Query query = null;
        if(baseTag instanceof CustomTagTypeLayer){
            query = new Query(Criteria.where(TAG_NAME).is(baseTag.getTagName()).and(PATH).is(baseTag.getPath()));
            mongoTemplate.findAllAndRemove(query,BaseTag.class);
        }else if( baseTag instanceof  CustomTagLeaf && !StringUtils.isEmpty(baseTag.getSource())){
            query = new Query(Criteria.where(TAG_NAME).is(baseTag.getTagName()).and(PATH).is(baseTag.getPath()).and(SOURCE).is(baseTag.getSource()));
            mongoTemplate.findAllAndRemove(query,BaseTag.class);
        }
    }

    @Override
    public void deleteCustomTagLeafByTagId(String tagId) {
        Query query = new Query(Criteria.where(TAG_ID).is(tagId));
        mongoTemplate.findAllAndRemove(query,BaseTag.class);
    }

    //Todo:这个方法要改的可以获取父类的属性,并且去除掉static final这样的属性
    private Update buildBaseUpdate(BaseTag t) {
        Update update = new Update();
        String className = BaseTag.class.getName();
        Field[] fields = filedMap.get(className);
        if (fields == null) {
            fields = BaseTag.class.getDeclaredFields();
            filedMap.putIfAbsent(className, fields);
        }
        for (Field field : fields) {
            if(field.getName().equals(SERIAL_VERSION_UID)) continue;
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
