package cn.rongcapital.mkt.job.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.rongcapital.mkt.biz.WechatMemberBiz;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.common.util.HttpUtils;
import cn.rongcapital.mkt.common.util.NumUtil;
import cn.rongcapital.mkt.dao.TenementDao;
import cn.rongcapital.mkt.dao.WebchatAuthInfoDao;
import cn.rongcapital.mkt.dao.WechatGroupDao;
import cn.rongcapital.mkt.dao.WechatMemberDao;
import cn.rongcapital.mkt.dao.WechatRegisterDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.job.vo.in.H5MktPubFansListResponse;
import cn.rongcapital.mkt.job.vo.in.H5PubFan;
import cn.rongcapital.mkt.job.vo.in.UserGroup;
import cn.rongcapital.mkt.po.WebchatAuthInfo;
import cn.rongcapital.mkt.po.WechatGroup;
import cn.rongcapital.mkt.po.WechatMember;
import cn.rongcapital.mkt.po.WechatRegister;
import cn.rongcapital.mkt.vo.weixin.WXFansListVO;

/*************************************************
 * @功能及特点的描述简述: 同步微信公众号接口
 * 
 * @see （与该类关联的类): TaskService
 * @对应项目名称: MC系统
 * @author: 白云峰
 * @version: v1.2 @date(创建、开发日期): 2016-6-17 最后修改日期: 2016-09-02
 * @复审人: 丛树林
 *************************************************/
@Service
public class GetPubFansListServiceImpl implements TaskService {

	// 同步粉丝列表
	// Todo:1.调用Http请求获取接口返回值 (checked)
	// Todo:2.对接口返回值进行JSON解析，保存到entity类中(checked)
	// Todo:3.将获得的数据首先同步到粉丝表，其次推算出group表，最后再将group表同步到
	@Autowired
	private TenementDao tenementDao;
	@Autowired
	private WechatMemberDao wechatMemeberDao;
	@Autowired
	private WechatGroupDao wechatGroupDao;
	@Autowired
	private WechatRegisterDao wechatRegisterDao;

	@Autowired
	private WebchatAuthInfoDao webchatAuthInfoDao;

	@Autowired
	private WechatMemberDao wechatMemberDao;

	@Autowired
	private WechatMemberBiz wechatMemberBiz;

	private Logger logger = LoggerFactory.getLogger(getClass());
	private final String bitmap = "00000011000000000";

	@Override
	public void task(Integer taskId) {
		// 获取授权的微信公众号的appid
		List<WebchatAuthInfo> selectListByIdList = webchatAuthInfoDao.selectList(new WebchatAuthInfo());
		if (!CollectionUtils.isEmpty(selectListByIdList)) {
			this.synFansInfoMethodNew(selectListByIdList);
		}
	}

	private void synFansInfoMethodNew(List<WebchatAuthInfo> list) {
		for (WebchatAuthInfo info : list) {
			if(null == info){
				continue;
			}
			/**
			 * 获取公众号
			 */
			String pubId = "";
			List<WechatMember> wechatMemberList = wechatMemberBiz.getUserList(info.getAuthorizerAppid(),
					info.getAuthorizerRefreshToken());
			
			if(!CollectionUtils.isEmpty(wechatMemberList)){
				WechatMember wechatMember = wechatMemberList.get(0);
				if(wechatMember!=null){
					pubId = wechatMember.getPubId();
				}
			}
			/**
			 * 注入粉丝的打标签状态
			 */
			wechatMemberList = setWechatMemberListSelected(pubId,wechatMemberList);
			/**
			 * 注入粉丝的扩展属性
			 */
			wechatMemberList = setWechatMembersProperties(wechatMemberList);
			/**
			 * 添加或者更新粉丝属性,对于数据库操作来说更新操作就是先删除,后插入,对于本地 还有添加操作 所以我们这里直接执行先删除后插入(节约判断是插入还是更新的操作)
			 */
			saveOrUpdateWechatMembers(pubId,wechatMemberList);
			/**
			 * 更新分组下粉丝数量
			 */
			if(StringUtils.isNotEmpty(pubId)){
				this.updateUngroupedCountNew(pubId);
			}			
		}
	}
	
	/**
	 * 更新分组粉丝数据
	 * @param count
	 * @param pubId
	 */
	private void updateUngroupedCountNew(String pubId){
		// Todo:获取wechat_wegister表中的状态是0的公众号信息
		WechatMember member = new WechatMember();
		member.setWxGroupId(ApiConstant.WECHAT_GROUP_M);
		member.setPubId(pubId);
		int count = wechatMemberDao.selectListCount(member);
		WechatGroup wechatGroup = new WechatGroup();
		wechatGroup.setCount(count);
		wechatGroup.setWxAcct(pubId);
		wechatGroup.setGroupId(ApiConstant.WECHAT_GROUP);
		
		wechatGroupDao.updateInfoByGroupWxCode(wechatGroup);	
		
		
	}
	
	/**
	 * 添加或者更新粉丝
	 * @param pubId
	 * @param wechatMembers
	 * @return
	 */
	private Integer saveOrUpdateWechatMembers(String pubId,List<WechatMember> wechatMembers){
		Integer count =0;
		if(!CollectionUtils.isEmpty(wechatMembers)){
			count = wechatMembers.size();
			int fromIndex = 0;
            int size = ApiConstant.WEIXIN_BATCH_INSERT_USER_INFO_SIZE;/** 微信定义的接口size是10000，所以我们这里也定义的10000,微信获取粉丝信息接口调用每天500次，10000*500=500W粉丝 */
            while (fromIndex < wechatMembers.size()) {
                if ((wechatMembers.size() - fromIndex) < ApiConstant.WEIXIN_BATCH_INSERT_USER_INFO_SIZE) {
                    size = wechatMembers.size();
                }
                List<WechatMember> fansListTemps = wechatMembers.subList(fromIndex, size);
                fromIndex = fromIndex + ApiConstant.WEIXIN_BATCH_INSERT_USER_INFO_SIZE;;
                size= fromIndex+ApiConstant.WEIXIN_BATCH_INSERT_USER_INFO_SIZE;                
                logger.info("now page number add list size: " + fansListTemps.size());
				try {
					if(StringUtils.isNotEmpty(pubId)&&!CollectionUtils.isEmpty(fansListTemps)){
						Map<String, Object> paramMap = new HashMap<String, Object>();
						paramMap.put("pubId", pubId);
						paramMap.put("wechatMembers", fansListTemps);
						wechatMemberDao.deleteWechatMembersByIdsAndPubId(paramMap);
						wechatMemberDao.batchInsert(fansListTemps);
					}
				} catch (Exception e) {
					continue;
				}				
            } 
		}
		return count;		
	}
	
	
	/**
	 * 注入粉丝集合扩展属性
	 * @param wechatMemberList
	 * @return
	 */
	private List<WechatMember> setWechatMembersProperties(List<WechatMember> wechatMemberList){
		if(!CollectionUtils.isEmpty(wechatMemberList)){
			for(WechatMember wechatMember : wechatMemberList){
				wechatMember = setWechatMemberProperties(wechatMember);
			}
		}		
		return wechatMemberList;		
	}
	
	/**
	 * 注入粉丝扩展属性
	 * @param wechatMember
	 * @return
	 */
	private WechatMember setWechatMemberProperties(WechatMember wechatMember){
		if(wechatMember!=null){
			if (wechatMember.getSex()!=null&&wechatMember.getSex() == 0) {
				wechatMember.setSex(3);
			}
			String wxGroupId = wechatMember.getWxGroupId();
			wxGroupId = wxGroupId == null || "".equals(wxGroupId) ? ApiConstant.WECHAT_GROUP_M : wxGroupId;
			wechatMember.setWxGroupId(wxGroupId);
			wechatMember.setSubscribeYn("Y");
			wechatMember.setActivity48hYn("N");
			wechatMember.setBitmap(bitmap);
		}
		return wechatMember;		
	}
	
	/**
	 * @功能简述 同步微信粉丝信息
	 * 
	 * @param: list
	 *             授权公众号信息
	 * @return:
	 * 
	 */
	private void synFansInfoMethod(List<WebchatAuthInfo> list) {

		for (WebchatAuthInfo info : list) {
			if(null == info){
				continue;
			}
			List<WechatMember> wechatMemberList = wechatMemberBiz.getUserList(info.getAuthorizerAppid(),
					info.getAuthorizerRefreshToken());
			
			String pubId = "";
			if(!CollectionUtils.isEmpty(wechatMemberList)){
				WechatMember wechatMember = wechatMemberList.get(0);
				if(wechatMember!=null){
					pubId = wechatMember.getPubId();
				}
			}

			wechatMemberList = setWechatMemberListSelected(pubId,wechatMemberList);
			List<Map<String, Object>> fansList = new ArrayList<Map<String, Object>>();
			
			if (!CollectionUtils.isEmpty(wechatMemberList)) {
			
				for (WechatMember wechatMember : wechatMemberList) {
					
					String wxCode = wechatMember.getWxCode();
					if (StringUtils.isBlank(wxCode))
						continue;
	
					Map<String, Object> paramFan = new HashMap<String, Object>();
	
					paramFan.put("wx_code", wxCode);
					if (wechatMember.getWxName() != null && wechatMember.getWxName().length() > 0) {
						paramFan.put("wx_name", wechatMember.getWxName().replaceAll("[^\\u0000-\\uFFFF]", ""));
					} else {
						paramFan.put("wx_name", null);
					}
					if (wechatMember.getNickname() != null && wechatMember.getNickname().length() > 0) {
						paramFan.put("nickname", wechatMember.getNickname().replaceAll("[^\\u0000-\\uFFFF]", ""));
					} else {
						paramFan.put("nickname", null);
					}
					if (wechatMember.getSex()!=null&&wechatMember.getSex() == 0) {
						paramFan.put("sex", 3);
					} else {
						paramFan.put("sex", wechatMember.getSex());
					}
					String country = wechatMember.getCountry();
					if (StringUtils.isBlank(country) || ApiConstant.NATIONALITY_CHINA.equals(country)) {
						paramFan.put("country", ApiConstant.NATIONALITY_CHINA);
						String province = wechatMember.getProvince();
						if (StringUtils.isBlank(province) || ApiConstant.PROVINCE_FOREIGN.equals(province)) {
							paramFan.put("province", ApiConstant.PROVINCE_CHINA_CAPITAL);
						} else {
							paramFan.put("province", wechatMember.getProvince());
						}
						String city = wechatMember.getCity();
						if (StringUtils.isBlank(city) || ApiConstant.CITY_CHINA_BEIHAI.equals(wechatMember.getCity())) {
							paramFan.put("city", ApiConstant.CITY_CHINA_CAPITAL);
						} else {
							if (ApiConstant.PROVINCE_CHINA_CAPITAL.equals(paramFan.get("province"))) {
								paramFan.put("city", ApiConstant.CITY_CHINA_CAPITAL);
							} else {
								paramFan.put("city", city + ApiConstant.CITY);
							}
						}
					} else {
						paramFan.put("country", wechatMember.getCountry());
						paramFan.put("province", wechatMember.getProvince());
						if (ApiConstant.PROVINCE_CHINA_CAPITAL.equals(paramFan.get("province"))) {
							paramFan.put("city", ApiConstant.CITY_CHINA_CAPITAL);
						} else {
							paramFan.put("city", wechatMember.getCity());
						}
					}
					String wxGroupId = wechatMember.getWxGroupId();
					wxGroupId = wxGroupId == null || "".equals(wxGroupId) ? ApiConstant.WECHAT_GROUP_M : wxGroupId;
					paramFan.put("wx_group_id", wxGroupId);
					paramFan.put("county", wechatMember.getCounty());
					paramFan.put("birthday", wechatMember.getBirthday());
					paramFan.put("subscribe_yn", "Y");
					paramFan.put("subscribe_time", wechatMember.getSubscribeTime());
					paramFan.put("active_time", wechatMember.getActiveTime());
					paramFan.put("activity_48h_yn", "N");
					paramFan.put("head_image_url", wechatMember.getHeadImageUrl());
					paramFan.put("remark", wechatMember.getRemark());
					paramFan.put("pub_id", wechatMember.getPubId());
					paramFan.put("bitmap", bitmap);
					paramFan.put("fans_json", wechatMember.getFansJson());
					paramFan.put("selected", wechatMember.getSelected());
					fansList.add(paramFan);
				}
			}
			if (fansList != null && fansList.size() > 0) {
				
				int fromIndex = 0;
                int size = ApiConstant.WEIXIN_BATCH_GET_USER_INFO_SIZE;/** 微信定义的接口size是10000，所以我们这里也定义的10000,微信获取粉丝信息接口调用每天500次，10000*500=500W粉丝 */
                while (fromIndex < fansList.size()) {
                    if ((fansList.size() - fromIndex) < ApiConstant.WEIXIN_BATCH_GET_USER_INFO_SIZE) {
                        size = fansList.size();
                    }
                    List<Map<String, Object>> fansListTemps = fansList.subList(fromIndex, size);
                    fromIndex = fromIndex + ApiConstant.WEIXIN_BATCH_GET_USER_INFO_SIZE;;
                    size= fromIndex+ApiConstant.WEIXIN_BATCH_GET_USER_INFO_SIZE;
                    
                    logger.info("now page number add list size: " + fansList.size());
    				WXFansListVO wxFansListVO = new WXFansListVO();
    				wxFansListVO.setFansList(fansListTemps);
    				Map<String, Object> mapTemp = fansListTemps.get(0);
    				wxFansListVO.setPubId(String.valueOf(mapTemp.get("pub_id")));				
    				try {
						wechatMemberDao.deleteFansByVO(wxFansListVO);
						wechatMemberDao.batchInsertFans(fansListTemps);
					} catch (Exception e) {
						logger.info("更新粉丝失败："+JSONObject.toJSONString(fansListTemps));				
						continue;
					}
                }    
				/*logger.info("now page number add list size: " + fansList.size());
				WXFansListVO wxFansListVO = new WXFansListVO();
				wxFansListVO.setFansList(fansList);
				Map<String, Object> mapTemp = fansList.get(0);
				wxFansListVO.setPubId(String.valueOf(mapTemp.get("pub_id")));				
				wechatMemberDao.deleteFansByVO(wxFansListVO);
				wechatMemberDao.batchInsertFans(fansList);*/
				fansList.clear();
			}			
			this.updateUngroupedCount();			
		}
	}
	
	private void updateUngroupedCount(){
		// Todo:获取wechat_wegister表中的状态是0的公众号信息
		WechatRegister wechatRinfo = new WechatRegister();
		wechatRinfo.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		List<WechatRegister> wechatRegisterList = wechatRegisterDao.selectList(wechatRinfo);
		if (!CollectionUtils.isEmpty(wechatRegisterList)) {

			for (WechatRegister wechatRegister : wechatRegisterList) {
				String pubId = wechatRegister.getWxAcct();
				
				if (StringUtils.isBlank(pubId))
					continue;
				WechatMember member = new WechatMember();
				member.setWxGroupId(ApiConstant.WECHAT_GROUP_M);
				member.setPubId(pubId);
				int count = wechatMemberDao.selectListCount(member);
				WechatGroup wechatGroup = new WechatGroup();
				wechatGroup.setCount(count);
				wechatGroup.setWxAcct(pubId);
				wechatGroup.setGroupId(ApiConstant.WECHAT_GROUP);
				
				wechatGroupDao.updateInfoByGroupWxCode(wechatGroup);	
			}
		}
	}
	
	

	private void callH5PlusMethod() {
		Map<String, String> h5ParamMap = new HashMap<String, String>();
		h5ParamMap.put("pid", tenementDao.selectPid().get("pid"));
		h5ParamMap.put(ApiConstant.DL_API_PARAM_METHOD, ApiConstant.DL_PUB_FANSLIST_API);
		h5ParamMap.put("page_size", ApiConstant.FANS_LIST_SYNC_SIZE + "");
		h5ParamMap.put("page_num", 1 + "");
		h5ParamMap.put("start_time", DateUtil.getStringFromDate(new Date(System.currentTimeMillis() - 24 * 3600 * 1000),
				"yyyy-MM-dd HH:mm:ss")); // Todo:这个增量时间怎么算
		h5ParamMap.put("end_time",
				DateUtil.getStringFromDate(new Date(System.currentTimeMillis()), "yyyy-MM-dd HH:mm:ss")); // Todo:这个增量怎么算
		HttpResponse httpResponse = HttpUtils.requestH5Interface(h5ParamMap);
		if (httpResponse != null) {
			JSONObject obj = null;
			try {
				String entityString = EntityUtils.toString(httpResponse.getEntity());
				if (entityString == null || "".equals(entityString))
					return;
				obj = JSON.parseObject(entityString).getJSONObject("hfive_mkt_pub_fanslist_response");
				if (obj != null) {
					H5MktPubFansListResponse h5MktPubFansListResponse = JSON.parseObject(obj.toString(),
							H5MktPubFansListResponse.class);
					long total = h5MktPubFansListResponse.getTotal();
					for (int pageNumber = (int) (total / ApiConstant.FANS_LIST_SYNC_SIZE
							+ 1); pageNumber >= 1; pageNumber--) {
						if (syncFansListByPageNum(h5ParamMap, entityString, pageNumber))
							continue;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean syncFansListByPageNum(Map<String, String> h5ParamMap, String entityString, int pageNumber)
			throws IOException {
		HttpResponse httpResponse;
		JSONObject obj;
		H5MktPubFansListResponse h5MktPubFansListResponse;
		h5ParamMap.put("page_num", pageNumber + "");
		httpResponse = HttpUtils.requestH5Interface(h5ParamMap);
		if (httpResponse != null) {
			JSONObject insertObj = null;
			String insertString = EntityUtils.toString(httpResponse.getEntity());
			if (entityString == null || "".equals(entityString))
				return true;
			obj = JSON.parseObject(insertString).getJSONObject("hfive_mkt_pub_fanslist_response");
			if (obj != null) {
				h5MktPubFansListResponse = JSON.parseObject(obj.toString(), H5MktPubFansListResponse.class);
				if (h5MktPubFansListResponse != null && h5MktPubFansListResponse.getFans() != null
						&& h5MktPubFansListResponse.getFans().getFan() != null
						&& h5MktPubFansListResponse.getFans().getFan().size() > 0) {
					logger.info("now pageNum :" + pageNumber);
					fansBatchInsert(h5MktPubFansListResponse);
				}
			}
		}
		return false;
	}

	private void fansBatchInsert(H5MktPubFansListResponse h5MktPubFansListResponse) {
		List<Map<String, Object>> fansList = new ArrayList<Map<String, Object>>();
		for (H5PubFan h5PubFan : h5MktPubFansListResponse.getFans().getFan()) {
			if (h5PubFan.getPubId() == null)
				continue;
			if (isIllegalPubFan(h5PubFan.getPubId()))
				continue;
			for (UserGroup userGroup : h5PubFan.getUserGroups().getUserGroup()) {
				Map<String, Object> paramGroup = new HashMap<String, Object>();
				paramGroup.put("wx_acct", h5PubFan.getPubId());
				paramGroup.put("group_name", userGroup.getUserGroup());
				Integer groupId = wechatGroupDao.selectGroupId(paramGroup);
				if (groupId == null) {
					wechatGroupDao.insertWechatGroup(paramGroup);
					groupId = wechatGroupDao.selectGroupId(paramGroup);
				}
				if (isFansAlreadyImported(h5PubFan.getPubId(), h5PubFan.getOpenId(), groupId))
					continue;
				Map<String, Object> paramFan = new HashMap<String, Object>();
				paramFan.put("wx_group_id", groupId);
				paramFan.put("wx_code", h5PubFan.getOpenId());
				if (h5PubFan.getName() != null && h5PubFan.getName().length() > 0) {
					paramFan.put("wx_name", h5PubFan.getName().replaceAll("[^\\u0000-\\uFFFF]", ""));
				} else {
					paramFan.put("wx_name", null);
				}
				if (h5PubFan.getNickName() != null && h5PubFan.getNickName().length() > 0) {
					paramFan.put("nickname", h5PubFan.getNickName().replaceAll("[^\\u0000-\\uFFFF]", ""));
				} else {
					paramFan.put("nickname", null);
				}
				if (h5PubFan.getSex() == 0) {
					paramFan.put("sex", 3);
				} else {
					paramFan.put("sex", h5PubFan.getSex());
				}
				if (h5PubFan.getCountry() == null || "中国".equals(h5PubFan.getCountry())) {
					paramFan.put("country", "中国");
					if (h5PubFan.getProvince() == null || "槟榔屿".equals(h5PubFan.getProvince())) {
						paramFan.put("province", "北京");
						paramFan.put("city", "北京市");
					} else {
						paramFan.put("province", h5PubFan.getProvince());
					}
					if (h5PubFan.getCity() == null || "北海".equals(h5PubFan.getCity())) {
						paramFan.put("province", "北京");
						paramFan.put("city", "北京市");
					} else {
						paramFan.put("city", h5PubFan.getCity());
					}
					if (h5PubFan.getProvince() != null && "北京".equals(h5PubFan.getProvince())) {
						paramFan.put("city", "北京市");
					}
				} else {
					paramFan.put("country", h5PubFan.getCountry());
				}
				// paramFan.put("province",h5PubFan.getProvince());
				// paramFan.put("city",h5PubFan.getCity());
				paramFan.put("county", h5PubFan.getCounty());
				paramFan.put("birthday", h5PubFan.getBirthday());
				paramFan.put("subscribe_yn", h5PubFan.getSubscribeYn());
				paramFan.put("subscribe_time", h5PubFan.getSubscribeTime());
				paramFan.put("active_time", h5PubFan.getActiveTime());
				paramFan.put("activity_48h_yn", h5PubFan.getActive48hYn());
				paramFan.put("head_image_url", h5PubFan.getHeadImageUrl());
				paramFan.put("remark", h5PubFan.getRemark());
				paramFan.put("pub_id", h5PubFan.getPubId());
				paramFan.put("bitmap", bitmap);
				fansList.add(paramFan);
			}
			if (fansList != null && fansList.size() > 0) {
				logger.info("now page number add list size: " + fansList.size());
				wechatMemeberDao.batchInsertFans(fansList);
				fansList.clear();
			}
		}

	}

	private boolean isIllegalPubFan(String pubId) {
		WechatRegister wechatRegister = new WechatRegister();
		wechatRegister.setWxAcct(pubId);
		Integer count = wechatRegisterDao.selectListCount(wechatRegister);
		if (count == null || count == 0)
			return true;
		if (count > 0) {
			List<WechatRegister> wechatRegisters = wechatRegisterDao.selectList(wechatRegister);
			if (!CollectionUtils.isEmpty(wechatRegisters)) {
				if (wechatRegisters.get(0).getType() == -1)
					return true;
			}
		}
		return false;
	}

	private boolean isFansAlreadyImported(String pubId, String openId, Integer groupId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pub_id", pubId);
		paramMap.put("wx_code", openId);
		paramMap.put("wx_group_id", groupId);
		List<Long> ids = wechatMemeberDao.selectIdByPubIdAndOpenId(paramMap);
		if (ids != null && ids.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public void task() {
		// 获取授权的微信公众号的appid
		List<WebchatAuthInfo> selectListByIdList = webchatAuthInfoDao.selectList(new WebchatAuthInfo());
		if (!CollectionUtils.isEmpty(selectListByIdList)) {
			this.synFansInfoMethod(selectListByIdList);
		}
	}
	
	
	
	/**
	 * @param wechatMemberList
	 * 如果粉丝信息有变化的时候设置Selected：0，表示 同步该粉丝的job该执行该方法了(粉丝必须是同一公众号下面的)
	 * @return
	 */
	private List<WechatMember> setWechatMemberListSelected(String pubId,List<WechatMember> wechatMemberList){
		List<WechatMember> wechatMemberListBack = new ArrayList<WechatMember>();
		if(!CollectionUtils.isEmpty(wechatMemberList)){
			Map<String,WechatMember> mapWechatMember = new HashMap<String,WechatMember>();
			WechatMember wechatMemberTemp = new WechatMember();
			wechatMemberTemp.setPubId(pubId);
			wechatMemberTemp.setSubscribeYn("Y");
			wechatMemberTemp.setPageSize(null);
			wechatMemberTemp.setStartIndex(null);
			List<WechatMember> wechatMemberListTemp = wechatMemeberDao.selectList(wechatMemberTemp);
			if(!CollectionUtils.isEmpty(wechatMemberListTemp)){
				for(Iterator<WechatMember> iter =wechatMemberListTemp.iterator();iter.hasNext();){
					WechatMember wechatMember = iter.next();
					if(!mapWechatMember.containsKey(wechatMember.getWxCode())){
						mapWechatMember.put(wechatMember.getWxCode(), wechatMember);
					}					
				}
				for(Iterator<WechatMember> iter =wechatMemberList.iterator();iter.hasNext();){
					WechatMember wechatMember = iter.next();
					if(mapWechatMember.containsKey(wechatMember.getWxCode())){
						WechatMember wechatMemberCS =  mapWechatMember.get(wechatMember.getWxCode());
						String fansJson = wechatMemberCS.getFansJson();
						if(StringUtils.isNotEmpty(fansJson)){
							if(!fansJson.equals(wechatMember.getFansJson())){
								wechatMember.setSelected(NumUtil.int2OneByte(0));
								wechatMemberListBack.add(wechatMember);
							}else{
								wechatMember.setSelected(wechatMemberCS.getSelected());
							}
						}else{
						    wechatMember.setSelected(NumUtil.int2OneByte(0));
						    wechatMemberListBack.add(wechatMember);
						}
					}else{
						wechatMember.setSelected(NumUtil.int2OneByte(0));
						wechatMemberListBack.add(wechatMember);
					}					
				}
			}else{
				for(Iterator<WechatMember> iter =wechatMemberList.iterator();iter.hasNext();){
					WechatMember wechatMember = iter.next();
					wechatMember.setSelected(NumUtil.int2OneByte(0));
					wechatMemberListBack.add(wechatMember);
				}
			}
		}
		return wechatMemberListBack;		
	}
	
}
