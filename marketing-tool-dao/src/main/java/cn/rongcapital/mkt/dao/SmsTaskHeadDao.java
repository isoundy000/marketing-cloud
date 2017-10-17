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

import cn.rongcapital.mkt.dao.base.BaseDao;
import cn.rongcapital.mkt.po.SmsTaskBody;
import cn.rongcapital.mkt.po.SmsTaskHead;
import cn.rongcapital.mkt.vo.sms.out.SmsTaskSendStatusVo;

public interface SmsTaskHeadDao extends BaseDao<SmsTaskHead>{
	
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
	 * 根据ID统计任务的发送消息的个数（成功、失败、等待发送）
	 * @param id
	 * @return
	 */
	public List<SmsTaskSendStatusVo> countStatusById(Long id);
	
	/**
	 * 根据素材ID，任务执行状态查询任务列表
	 * @param t
	 * @return
	 */
	public List<SmsTaskHead> selectListByMaterial(SmsTaskHead t);
	
	
	/**
	 * 根据细分id查询未发布和已结束的所有短信个数 
	 * 
	 * @param campaignAudienceTarget
	 * @return count
	 */
	public int selectTaskStatusCount(SmsTaskBody smsTaskBody);
}