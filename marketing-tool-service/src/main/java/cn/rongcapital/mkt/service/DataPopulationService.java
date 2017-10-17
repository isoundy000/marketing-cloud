package cn.rongcapital.mkt.service;

import java.util.List;
import java.util.Map;

import cn.rongcapital.mkt.po.CityDic;
import cn.rongcapital.mkt.po.ProvinceDic;
import cn.rongcapital.mkt.po.WechatMember;

public interface DataPopulationService {

	/**
	 * 同步同步粉丝到data_population
	 * @param wechatMembers
	 * @return
	 */
	public Boolean synchronizeMemberToDataPopulation(List<WechatMember> wechatMembers);
	
	/**
	 * 同步粉丝到data_population
	 * @param wechatMembers
	 * @param provinceDicMap
	 * @param cityDicMap
	 * @return
	 */
	public Boolean synchronizeMemberToDataPopulation(List<WechatMember> wechatMembers,Map<String, ProvinceDic> provinceDicMap,Map<String, CityDic> cityDicMap);

	/**
	 * 同步粉丝到data_population和更新粉丝状态
	 * @param wechatMembers
	 * @param provinceDicMap
	 * @param cityDicMap
	 * @return
	 */
	public void synchronizeMemberToDataPopulationAndUpdateMember(List<WechatMember> wechatMembers);
	
	/**
	 * 同步粉丝到data_population和更新粉丝状态
	 * @param wechatMembers
	 * @param provinceDicMap
	 * @param cityDicMap
	 * @return
	 */
	public void synchronizeMemberToDataPopulationAndUpdateMember(List<WechatMember> wechatMembers,Map<String, ProvinceDic> provinceDicMap,Map<String, CityDic> cityDicMap);

}
