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

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.rongcapital.mkt.dao.base.BaseDao;
import cn.rongcapital.mkt.po.CampaignHead;

public interface CampaignHeadDao extends BaseDao<CampaignHead>{
	
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
	 * 查询compaign_head表，对status=0(代表本条数据有效)的数据，
	 * 根据publish_status(各个值得含义下表中已经给出)做统计
	 * @param 
	 * @return list
	 */
	public List<Object> selectCampaignHeadCountGroupByPublishStatus();
	
	/**
	 * 对campaign_head表做count统计，获取总有多少个活动
	 * @param 
	 * @return int
	 */
	public int selectCampaignCount();
	
	/**
	 * 根据publish_status以及campaign_name(如果有要用like做模糊查询)从campaign_head表中查询
	 * @param paramMap
	 * @return list
	 */
	public List<CampaignHead> selectCampaignProgressStatusListByPublishStatus(CampaignHead campaignHead);

	/**
	 * 根据publish_status以及campaign_name(如果有要用like做模糊查询)从campaign_head表中查询 总数
	 * @param paramMap
	 * @return list
	 */
	public int selectCampaignProgressStatusListByPublishStatusCount(CampaignHead campaignHead);
	
	/**
	 * 主数据查询(主界面搜索栏里面的模糊查询)
	 * @param paramMap
	 * @return list
	 */
	List<Map<String,Object>> searchDataMain(Map<String, Object> paramMap);
	
	/**
     * 查询正在进行中,待进行的活动.先进行中（按时间先后）、后待进行
     * @param paramMap
     * @return list
     */
	List<CampaignHead> selectInProgressandPrepareStatusCampaignHead(Map<String, Date> paramMap);
	
	/**
     * 查询正在进行中,待进行的活动.先进行中（按时间先后）、后待进行
     * @param paramMap
     * @return list
     */
    List<CampaignHead> selectInProgressandPrepareStatusCampaignHeadLimitation(Map<String, Date> paramMap);
    
    /**
     * 通过传入月份查询活动
     * @param searchDate
     * @return
     */
    List<CampaignHead> selectCampaignHeadListBySearchDate(@Param("searchDate") String searchDate);
    
}