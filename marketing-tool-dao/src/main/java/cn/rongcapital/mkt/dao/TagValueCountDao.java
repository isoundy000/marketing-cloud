/*************************************************
 * @功能简述: DAO接口类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 宋世涛
 * @version: 0.0.1
 * @date: 2016/5/16
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.dao;

import org.apache.ibatis.annotations.Param;

import cn.rongcapital.mkt.dao.base.BaseDao;
import cn.rongcapital.mkt.po.TagValueCount;

import java.util.List;
import java.util.Map;

public interface TagValueCountDao extends BaseDao<TagValueCount>{
	
	//清空存量数据
	int clearStockData();
	
	//获取标签值id
	String selectTagValueId(@Param("tagId")String tagId,@Param("tagValue")String tagValue);

	//模糊搜索标签值和标签值Id
    List<TagValueCount> selectTagValueCountListByKeyWord(TagValueCount paramTagValueCount);
    
    /**
     * 模糊搜索标签值和标签名
     * @param tagValue
     * @return
     * @Date 2016-11-10
     * @author shuiyangyang
     */
    List<TagValueCount> selectFuzzyTagValue(TagValueCount tagValueCount);
    
    /**
     * 模糊搜索标签值和标签名    统计总数
     * @param tagValue
     * @return
     * @Date 2016-11-11
     * @author shuiyangyang
     */
    int selectFuzzyTagValueCount(TagValueCount tagValueCount);
    
    TagValueCount selectTagByTagId(@Param("tagId") String tagId);
    
    /**
     * 通过tagId删除标签
     * @param tagId
     * @return
     */
    int deleteTagByTagId(@Param("tagId") String tagId);
    
    /**
     * 通过标签ID查询标签覆盖率
     * @param tagId
     * @return
     */
    Long getTagCountByTagId(@Param("tagId") String tagId);
    
    /**
     * 通过标签ID修改是否可编辑状态
     * @param paramMap 参数集合
     * @return
     */
    int changeUpdateFlagByTagId(TagValueCount tagValueCount);
    
    
}