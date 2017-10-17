package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.po.SmsTaskBody;
import cn.rongcapital.mkt.vo.BaseOutput;

public interface SmsTaskHeadService {

	/**
	 * 根据短信应用渠道、短信任务执行状态、任务名称模糊查询任务列表
	 * @param userId
	 * @param index
	 * @param size
	 * @param smsTaskAppType
	 * @param smsTaskStatus
	 * @param smsTaskName
	 * @return
	 */
	public BaseOutput smsTaskHeadList(String userId, Integer index, Integer size, String smsTaskAppType,String smsTaskStatus, String smsTaskName) throws Exception;
	
	/**
	 * 根据任务ID发布任务
	 * @param userId
	 * @param id
	 * @return
	 */
	public BaseOutput smsTaskHeadPublish(String userId, Integer id) throws Exception;
	
	/**
	 * 根据细分id查询除了未发布和已结束的所有短信个数 
	 * 
	 * @param smsTaskBody
	 * @return count
	 */
	public int getTaskStatusCount(SmsTaskBody smsTaskBody);

	public BaseOutput getSmsTaskHeadById(Integer id) throws Exception;
	
	
}
