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
import cn.rongcapital.mkt.po.SmsTaskDetail;
import cn.rongcapital.mkt.vo.out.MessageSendRecordGetOut;

import org.apache.ibatis.annotations.Param;

public interface SmsTaskDetailDao extends BaseDao<SmsTaskDetail>{
	
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
     * 返回当前任务的发送记录列表(如果手机号为空则查本次任务全部记录)
     *
     * @param smsTaskDetail
     * @return
     */
    List<MessageSendRecordGetOut> messageSendRecordGet(SmsTaskDetail smsTaskDetail);

    void batchInsert(@Param("list") List<SmsTaskDetail> smsTaskDetails);
}