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

import java.util.List;
import java.util.Map;

import cn.rongcapital.mkt.dao.base.BaseDao;
import cn.rongcapital.mkt.po.SegmentationHead;
import org.apache.ibatis.annotations.Param;

public interface SegmentationHeadDao extends BaseDao<SegmentationHead>{
	
	/**
	 * @功能简述:根据条件查询segmentation列表
	 * @param: Segmentation t
	 * @return: List<T>
	 */
	List<SegmentationHead> selectListByKeyword(SegmentationHead t);
	//自定义扩展
	/**
	 * 父类方法无法满足需求时使用,需在mapper.xml中扩展
	 * 查询对象list;
	 * 自定义条件查询,只要不为NULL与空则为条件,属性值之间and连接
	 * @param paramMap
	 * @return list
	 */
	//List<T> selectListBycustomMap(Map<String,Object> paramMap);
	
	//自定义扩展
	/**
	 * 父类方法无法满足需求时使用,需在mapper.xml中扩展
	 * 查询对象总数
	 * 自定义条件查询,只要不为NULL与空则为条件,属性值之间and连接
	 * @param paramMap
	 * @return list
	 */
	//List<T> selectListCountBycustomMap(Map<String,Object> paramMap);

	/**
	 * 主数据查询(主界面搜索栏里面的模糊查询)
	 * @param paramMap
	 * @return list
	 */
	List<Map<String,Object>> searchDataMain(Map<String,Object> paramMap);

    /**
     * 修改referCampaignCount
     * @param id
     * @param referCampaignCount
     * @return
     */
	int incrementReferCampaignCount(@Param("id") Integer id, @Param("referCampaignCount") Integer referCampaignCount);

}