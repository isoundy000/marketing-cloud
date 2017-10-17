package cn.rongcapital.mkt.dao.mongo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.mongodb.WriteResult;

import cn.rongcapital.mkt.po.mongodb.SystemCustomTagTree;

/**
 * 
 * 备注：默认只更新未逻辑删除的数据
 * 
 * @author shuiyangyang
 *
 */
@Service
public class MongoSystemCustomTagTreeDaoImpl implements MongoSystemCustomTagTreeDao {

    private Logger logger = LoggerFactory.getLogger(MongoBaseTagDaoImpl.class);

    private final String TAG_TREE_ID = "tag_tree_id";
    private final String TAG_TREE_NAME = "tag_tree_name";
    private final String LEVEL = "level";
    private final String IS_DELETED = "is_deleted";
    private final String PARENT = "parent";
    private final String CREATE_TIME = "create_time";
    private final String UPDATE_TIME = "update_time";
    private final String CHILDREN = "children";
    private final String IS_SHOW = "is_show";
    private final String CHILDREN_TAG = "children_tag";

    private static final int DATA_VALID = 0;
    private static final int DATA_NOT_VALID = 1;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 功能描述：插入一个SystemCustomTagTree
     * 
     * @param systemCustomTagTree
     */
    @Override
    public void insert(SystemCustomTagTree systemCustomTagTree) {
        logger.debug("insert--> : {}", systemCustomTagTree);
        if (systemCustomTagTree != null) {
            mongoTemplate.insert(systemCustomTagTree);
        }
    }

    /**
     * 功能描述：批量插入SystemCustomTagTree
     * 
     * @param SystemCustomTagTreeList
     */
    @Override
    public void insert(List<SystemCustomTagTree> systemCustomTagTreeLists) {
        logger.debug("insert--> : {}", systemCustomTagTreeLists);
        if (CollectionUtils.isNotEmpty(systemCustomTagTreeLists)) {
            mongoTemplate.insert(systemCustomTagTreeLists);
        }
    }

    /**
     * 功能描述：根据tag_tree_id查找一个
     * 
     * @param tagTreeId
     * @return
     */
    @Override
    public SystemCustomTagTree findOneByTagTreeId(String tagTreeId) {
        SystemCustomTagTree systemCustomTagTree =
                mongoTemplate.findOne(new Query(new Criteria(TAG_TREE_ID).is(tagTreeId).and(IS_DELETED).is(DATA_VALID)),
                        SystemCustomTagTree.class);
        logger.debug("findOneByTagTreeId--> param:[tagTreeId:{}] --return:{} ", tagTreeId, systemCustomTagTree);
        return systemCustomTagTree;
    }
    
    /**
     * 功能描述：根据tag_tree_id,is_show查询SystemCustomTagTree
     * 
     * @param tagTreeId
     * @param isShow
     * @return
     */
    @Override
    public SystemCustomTagTree findOneByTagTreeIdAndIsShow(String tagTreeId, Boolean isShow) {
        SystemCustomTagTree systemCustomTagTree =
                mongoTemplate.findOne(new Query(new Criteria(TAG_TREE_ID).is(tagTreeId).and(IS_SHOW).is(isShow).and(IS_DELETED).is(DATA_VALID)),
                        SystemCustomTagTree.class);
        logger.debug("findOneByTagTreeId--> param:[tagTreeId:{},isShow:{}] --return:{} ", tagTreeId, systemCustomTagTree);
        
        return systemCustomTagTree;
    }

    /**
     * 功能描述：根据level合isShow查询,如果isShow为null则该字段不生效
     * 
     * @param level
     * @param isShow
     * @return
     */
    @Override
    public List<SystemCustomTagTree> findByLeveLAndIsShow(int level, Boolean isShow) {
        Criteria criteria = new Criteria();
        // 查询正常数据
        criteria.and(IS_DELETED).is(DATA_VALID);
        criteria.and(LEVEL).is(level);
        // 判断是否只显示is_show为true的数据
        if (isShow != null) {
            criteria.and(IS_SHOW).is(isShow);
        }
        List<SystemCustomTagTree> systemCustomTagTreeLists =
                mongoTemplate.find(new Query(criteria), SystemCustomTagTree.class);
        logger.debug("findByLeveLAndIsShow--> param:[level:{},isShow:{}] --return:{} ", level, isShow,
                systemCustomTagTreeLists);
        return systemCustomTagTreeLists;
    }

    /**
     * 功能描述：根据is_show进行count
     * 
     * @param isShow
     * @return
     */
    @Override
    public long countByIsShow(Boolean isShow) {
        long count = mongoTemplate.count(new Query(new Criteria(IS_DELETED).is(DATA_VALID).and(IS_SHOW).is(isShow)),
                SystemCustomTagTree.class);
        logger.debug("countByIsShow--> param:[isShow:{}] --return:{} ", isShow, count);
        return count;
    }

    /**
     * 功能描述：根据tag_tree_id更新数据(其中parent可选)
     * 
     * @param tagTreeName
     * @param parent
     * @param children
     * @param level
     * @param childrenTag
     * @param tagTreeId
     * @return
     */
    @Override
    public int updateByTagTreeId(String tagTreeName, String parent, List<String> children, String level,
            List<String> childrenTag, String tagTreeId) {
        Update update = new Update();
        update.set(TAG_TREE_NAME, tagTreeName);
        if (parent != null) {
            update.set(PARENT, parent);
        }
        update.set(CHILDREN, children == null ? new ArrayList<String>() : children);
        update.set(LEVEL, level);
        update.set(CHILDREN_TAG, childrenTag == null ? new ArrayList<String>() : childrenTag);
        update.set(UPDATE_TIME, new Date());

        WriteResult writeResult = mongoTemplate.updateFirst(
                new Query(new Criteria(TAG_TREE_ID).is(tagTreeId).and(IS_DELETED).is(DATA_VALID)), update,
                SystemCustomTagTree.class);
        logger.debug(
                "updateByTagTreeId--> param:[tagTreeName:{},parent:{},children:{},level:{},childrenTag:{},tagTreeId:{}] --return:{} ",
                tagTreeName, parent, children, level, childrenTag, tagTreeId, writeResult.getN());
        return writeResult.getN();
    }

    /**
     * 功能描述：删除children中的一个元素
     * 
     * @param children
     * @return 修改文档的个数
     */
    @Override
    public int pullChildrenOne(String children) {
        WriteResult writeResult =
                mongoTemplate.updateFirst(new Query(new Criteria(CHILDREN).is(children).and(IS_DELETED).is(DATA_VALID)),
                        new Update().pull(CHILDREN, children).set(UPDATE_TIME, new Date()), SystemCustomTagTree.class);
        logger.debug("pullChildrenOne--> param:[children:{}] --return:{}", children, writeResult.getN());
        return writeResult.getN();
    }

    /**
     * 
     * 功能描述：根据level更新is_show字段
     * 
     * @param level
     * @return
     */
    @Override
    public int updateIsShowByLevel(int level, Boolean isShow) {
        WriteResult writeResult =
                mongoTemplate.updateMulti(new Query(new Criteria(LEVEL).is(level).and(IS_DELETED).is(DATA_VALID)),
                        new Update().set(IS_SHOW, isShow).set(UPDATE_TIME, new Date()), SystemCustomTagTree.class);
        logger.debug("updateIsShowByLevel--> param:[level:{},isShow:{}] --return:{}", level, isShow,
                writeResult.getN());
        return writeResult.getN();
    }

    /**
     * 功能描述：根据level tag_tree_id更新is_shows字段
     * 
     * @param level
     * @param tagTreeIdLists
     * @param isShow
     * @return
     */
    @Override
    public int updateIsShowByLevelTagTreeTd(int level, List<String> tagTreeIdLists, Boolean isShow) {
        WriteResult writeResult = mongoTemplate.updateMulti(
                new Query(new Criteria(LEVEL).is(level).and(IS_DELETED).is(DATA_VALID).and(TAG_TREE_ID)
                        .in(tagTreeIdLists)),
                new Update().set(IS_SHOW, isShow).set(UPDATE_TIME, new Date()), SystemCustomTagTree.class);
        logger.debug("updateIsShowByLevelTagTreeTd--> param:[level:{},tagTreeIdLists:{},isShow:{}] --return:{}", level,
                tagTreeIdLists, isShow, writeResult.getN());
        return writeResult.getN();
    }


    /**
     * 功能描述：根据tag_tree_id逻辑删除数据
     * 
     * @param tagTreeId
     * @return
     */
    @Override
    public int deleteFlagByTagTreeId(String tagTreeId) {
        WriteResult writeResult = mongoTemplate.updateFirst(new Query(new Criteria(TAG_TREE_ID).is(tagTreeId)),
                new Update().set(IS_DELETED, DATA_NOT_VALID), SystemCustomTagTree.class);
        logger.debug("deleteFlagByTagTreeId--> param:[tagTreeId:{}] --return：{}", tagTreeId, writeResult.getN());
        return writeResult.getN();
    }

    /**
     * 功能描述：根据tag_tree_id物理删除数据
     * 
     * @param tagTreeId
     * @return
     */
    @Override
    public int deleteByTagTreeId(String tagTreeId) {
        WriteResult writeResult =
                mongoTemplate.remove(new Query(new Criteria(TAG_TREE_ID).is(tagTreeId)), SystemCustomTagTree.class);
        logger.debug("deleteByTagTreeId--> param:[tagTreeId:{}] --return:{}", tagTreeId, writeResult.getN());
        return writeResult.getN();
    }


}
