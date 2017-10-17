package cn.rongcapital.mkt.biz.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tagsin.tutils.json.JsonUtils;
import com.tagsin.wechat_sdk.App;
import com.tagsin.wechat_sdk.WxComponentServerApi;
import com.tagsin.wechat_sdk.user.UserInfo;

import cn.rongcapital.mkt.biz.WechatMemberBiz;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.WechatRegisterDao;
import cn.rongcapital.mkt.po.WechatInterfaceLog;
import cn.rongcapital.mkt.po.WechatMember;
import cn.rongcapital.mkt.po.WechatRegister;

@Service
public class WechatMemberBizImpl extends BaseBiz implements WechatMemberBiz {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WechatRegisterDao wechatRegisterDao;

    /*
     * (non-Javadoc)
     * @see cn.rongcapital.mkt.biz.WechatMemberBiz#getUserList(java.lang.String, java.lang.String)\
     * 获取公众号下的粉丝列表：1：获取粉丝openID集合，2：轮询获取粉丝详细信息
     */
    @Override
    public List<WechatMember> getUserList(String authorizer_appid, String authRefreshToken) {
        App app = getApp();
        app.setAuthRefreshToken(authRefreshToken);
        app.setAuthAppId(authorizer_appid);
        List<String> openidLists = new ArrayList<String>();

        /**
         * 获取粉丝列表ID集合
         */
        openidLists = this.getOpenidLists(app);
        if (CollectionUtils.isEmpty(openidLists)) {
            return null;
        }
        /**
         * 根据粉丝ID集合，获取粉丝列表
         */
        List<WechatMember> wechatMemberLists = this.getWechatMemberLists(app, openidLists);
        logger.info("获取的粉丝信息共：{} 条", wechatMemberLists.size());
        return wechatMemberLists;
    }


    /**
     * @param app 获取粉丝openID列表,分页查询每页最多1000个
     * @return
     */
    private List<String> getOpenidLists(App app) {
        List<String> openidLists = new ArrayList<String>();
        String nextOpenId = "";// 此出应该设置为空
        String userString = WxComponentServerApi.getBaseWxSdk().getUserList(app, nextOpenId);
        WechatInterfaceLog wechatInterfaceLog = new WechatInterfaceLog("WechatMemberBizImpl",
                        "getOpenidLists", userString, new Date());
        wechatInterfaceLogService.insert(wechatInterfaceLog);
        logger.debug("根据app获取的粉丝列表内容为：{}", userString);
        JSONObject userJson = JSON.parseObject(userString);

        Integer jsonInt = userJson.getInteger("errcode");
        if (StringUtils.isNotBlank(userString) && jsonInt != null) {
            return null;
        }

        int count = userJson.getIntValue("count");
        if (count > 0) {
            JSONObject userDataJson = userJson.getJSONObject("data");
            String openidString = userDataJson.getString("openid");
            openidLists = JSONArray.parseArray(openidString, String.class);

            // 如果粉丝大于10000 继续获取粉丝
            while (count >= 10000) {
                nextOpenId = userJson.getString("next_openid");
                userString = WxComponentServerApi.getBaseWxSdk().getUserList(app, nextOpenId);
                wechatInterfaceLog = new WechatInterfaceLog("WechatMemberBizImpl", "getOpenidLists",
                                userString, new Date());
                wechatInterfaceLogService.insert(wechatInterfaceLog);

                logger.debug("根据app获取的粉丝列表内容为：{}", userString);
                userJson = JSON.parseObject(userString);
                count = userJson.getIntValue("count");
                if (count > 0) {
                    userDataJson = userJson.getJSONObject("data");
                    openidString = userDataJson.getString("openid");
                    openidLists.addAll(JSONArray.parseArray(openidString, String.class));
                }
            }
        }
        return openidLists;
    }

    /**
     * @param app 根据openID列表获取粉丝的基本信息列表
     * @param openidLists
     * @return
     */
    private List<WechatMember> getWechatMemberLists(App app, List<String> openidLists) {
        List<WechatMember> wechatMemberLists = new ArrayList<WechatMember>();

        WechatRegister wechatRegister = new WechatRegister();
        wechatRegister.setAppId(app.getAuthAppId());
        List<WechatRegister> wechatRegisterLists = wechatRegisterDao.selectList(wechatRegister);
        if (CollectionUtils.isNotEmpty(wechatRegisterLists)&&CollectionUtils.isNotEmpty(openidLists)) {
            WechatRegister wg = wechatRegisterLists.get(0);
            int fromIndex = 0;
            int size = ApiConstant.WEIXIN_BATCH_GET_USER_INFO_SIZE;/** 微信定义的接口size是100,批量获取用户信息 */
            while (fromIndex < openidLists.size()) {
                if ((openidLists.size() - fromIndex) < ApiConstant.WEIXIN_BATCH_GET_USER_INFO_SIZE) {
                    size = openidLists.size();
                }
                List<String> openidListTemps = openidLists.subList(fromIndex, size);
                fromIndex = fromIndex + ApiConstant.WEIXIN_BATCH_GET_USER_INFO_SIZE;;
                size= fromIndex+ApiConstant.WEIXIN_BATCH_GET_USER_INFO_SIZE;
                String userInfoesStr = WxComponentServerApi.getBaseWxSdk().getBatchGetUserInfoResult(app, openidListTemps);
				/**
				 * 去掉特殊字符 例如表情符等等
				 */
                userInfoesStr = super.replaceAllUTF8mb4(userInfoesStr);
                /**
                 * 转成用户对象
                 */
    			List<UserInfo> userInfoes = this.getUserInfoesByStr(userInfoesStr);
                /**
                 * 记入获取微信公众号的粉丝日志
                 */
                WechatInterfaceLog wechatInterfaceLog = new WechatInterfaceLog(
                                "WechatMemberBizImpl", "getWechatMemberLists",
                                userInfoesStr, new Date());
                wechatInterfaceLogService.insert(wechatInterfaceLog);

                if (CollectionUtils.isNotEmpty(userInfoes)) {
                    for (Iterator<UserInfo> iter = userInfoes.iterator(); iter.hasNext();) {
                        UserInfo userInfo = iter.next();
                        if (userInfo != null) {
                            WechatMember wechatMember = new WechatMember();
                            wechatMember = this.getWechatMember(userInfo, wg.getWxAcct());								
                            wechatMemberLists.add(wechatMember);
                        }else{
                        	logger.info("userInfo is null");
                        }
                    }
                }                
            }
        }
        return wechatMemberLists;
    }

    private List<UserInfo> getUserInfoesByStr(String userInfoesStr){
		JSONObject jsonObject  = JSONObject.parseObject(userInfoesStr);
		String json = jsonObject.getString("user_info_list");
		List<UserInfo> userInfoes = JsonUtils.fromJson(json,new TypeReference<List<UserInfo>>() {});
		return userInfoes;
    }
    
    public UserInfo getUserInfoeByOpenid(App app, String openid){
    	UserInfo userInfoBack = null;
    	List<String> openidListTemps = new ArrayList<String>();
    	openidListTemps.add(openid);
    	/**
    	 * 调用wechat_sdk接口获取用户信息
    	 */
    	String userInfoesStr = WxComponentServerApi.getBaseWxSdk().getBatchGetUserInfoResult(app, openidListTemps);
		/**
		 * 去掉特殊字符 例如表情符等等
		 */
        userInfoesStr = super.replaceAllUTF8mb4(userInfoesStr);
        /**
         * 转成用户对象
         */
		List<UserInfo> userInfoes = this.getUserInfoesByStr(userInfoesStr);
		if(CollectionUtils.isNotEmpty(userInfoes)){
			userInfoBack = userInfoes.get(0);
		}
		return userInfoBack;   	
    }
    
    /**
     * @param userInfo 获取粉丝基本信息
     * @param wxAcct
     * @return
     */
    private WechatMember getWechatMember(UserInfo userInfo, String wxAcct) {
        WechatMember wechatMember = new WechatMember();
        wechatMember.setWxCode(userInfo.getOpenid());
        wechatMember.setWxName(userInfo.getNickname());
        wechatMember.setNickname(userInfo.getNickname());
        wechatMember.setSex(userInfo.getSex());
        wechatMember.setCity(userInfo.getCity());
        wechatMember.setCountry(userInfo.getCountry());
        wechatMember.setProvince(userInfo.getProvince());
        // language
        wechatMember.setHeadImageUrl(userInfo.getHeadimgurl());
    	if(userInfo.getSubscribe_time()!=null){
			Long millisecond = new Long(userInfo.getSubscribe_time()) * 1000;
			Date data = new Date(millisecond);
			// 关注时间
			wechatMember.setSubscribeTime(DateUtil.getStringFromDate(data, "yyyy-MM-dd HH:mm:ss"));        		
    	}
		
        // unionid
        wechatMember.setRemark(userInfo.getRemark());
        StringBuffer sb = new StringBuffer();
        List<String> tagList = userInfo.getTagid_list();

        if (tagList != null && tagList.size() > 0) {
            for (int i = 0; i < tagList.size(); i++) {
                sb.append(tagList.get(i));
                sb.append(",");
            }
//            sb.deleteCharAt(sb.length() - 1);
        }
        wechatMember.setWxGroupId(sb.toString());
        wechatMember.setPubId(wxAcct);
        String fansJson = JSONObject.toJSONString(wechatMember);
        wechatMember.setFansJson(fansJson);
        return wechatMember;
    }

}
