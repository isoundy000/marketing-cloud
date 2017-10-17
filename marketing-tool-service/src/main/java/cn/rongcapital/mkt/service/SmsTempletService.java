package cn.rongcapital.mkt.service;

import java.util.List;

import cn.rongcapital.mkt.po.SmsTemplet;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.SmsTempletOut;
import cn.rongcapital.mkt.vo.sms.in.SmsTempletIn;
import cn.rongcapital.mkt.vo.sms.in.SmsTempletListIn;

public interface SmsTempletService {
	
	/**
	 * @param userId
	 * @param index
	 * @param size
	 * @param channelType
	 * @param type
	 * @param name
	 * @return
	 */
	public SmsTempletOut smsTempletList(String userId,Integer index,Integer size,String channelType,String type,String name,String content);
	
	/**
	 * @param smsTempletIn
	 * @return
	 */
	public BaseOutput saveOrUpdateSmsTemplet(SmsTempletIn smsTempletIn);
	
	/**
	 * 验证模板是否可以被删除或者编辑
	 * @param id
	 * @return
	 */
	public boolean smsTempletValidate(Integer id);
	
	/**
	 * 返回结果的组装
	 * @param dataList
	 * @return
	 */
	public List<SmsTemplet> setSmsTempletView(List<SmsTemplet> dataList);
	
}
