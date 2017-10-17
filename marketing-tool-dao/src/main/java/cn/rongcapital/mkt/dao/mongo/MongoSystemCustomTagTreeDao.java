package cn.rongcapital.mkt.dao.mongo;

import java.util.List;

import cn.rongcapital.mkt.po.mongodb.SystemCustomTagTree;

public interface MongoSystemCustomTagTreeDao {

    // 插入<--------------------------------------------------------------------------------------------------->
    /**
     * 功能描述：插入一个SystemCustomTagTree
     * 
     * @param systemCustomTagTree
     */
    public void insert(SystemCustomTagTree systemCustomTagTree);

    /**
     * 功能描述：批量插入SystemCustomTagTree
     * 
     * @param SystemCustomTagTreeList
     */
    public void insert(List<SystemCustomTagTree> systemCustomTagTreeLists);

    // 查询<--------------------------------------------------------------------------------------------------->
    /**
     * 功能描述：根据tag_tree_id查找SystemCustomTagTree
     * 
     * @param tagTreeId
     * @return
     */
    public SystemCustomTagTree findOneByTagTreeId(String tagTreeId);
    
    /**
     * 功能描述：根据tag_tree_id,is_show查询SystemCustomTagTree
     * 
     * @param tagTreeId
     * @param isShow
     * @return
     */
    public SystemCustomTagTree findOneByTagTreeIdAndIsShow(String tagTreeId, Boolean isShow);

    /**
     * 功能描述：根据level合isShow查询,如果isShow为null则该字段不生效
     * 
     * @param level
     * @param isShow
     * @return
     */
    public List<SystemCustomTagTree> findByLeveLAndIsShow(int level, Boolean isShow);
    
    /**
     * 功能描述：根据is_show进行count
     * 
     * @param isShow
     * @return
     */
    public long countByIsShow(Boolean isShow);

    // 更新<--------------------------------------------------------------------------------------------------->

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
    public int updateByTagTreeId(String tagTreeName, String parent, List<String> children, String level, List<String> childrenTag,
            String tagTreeId);


    /**
     * 功能描述：删除children中的一个元素
     * 
     * @param children
     * @return 修改文档的个数
     */
    public int pullChildrenOne(String children);

    /**
     * 
     * 功能描述：根据level更新is_show字段
     * 
     * @param level
     * @return
     */
    public int updateIsShowByLevel(int level, Boolean isShow);

    /**
     * 功能描述：根据level tag_tree_id更新is_shows字段
     * 
     * @param level
     * @param tagTreeIdLists
     * @param isShow
     * @return
     */
    public int updateIsShowByLevelTagTreeTd(int level, List<String> tagTreeIdLists, Boolean isShow);

    // 删除<--------------------------------------------------------------------------------------------------->

    /**
     * 功能描述：根据tag_tree_id逻辑删除数据
     * 
     * @param tagTreeId
     * @return
     */
    public int deleteFlagByTagTreeId(String tagTreeId);

    /**
     * 功能描述：根据tag_tree_id物理删除数据
     * 
     * @param tagTreeId
     * @return
     */
    public int deleteByTagTreeId(String tagTreeId);



}
