package cn.rongcapital.mkt.biz.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tagsin.wechat_sdk.App;
import com.tagsin.wechat_sdk.WxComponentServerApi;

import cn.rongcapital.mkt.biz.WechatGroupBiz;
import cn.rongcapital.mkt.dao.WechatRegisterDao;
import cn.rongcapital.mkt.po.WechatGroup;
import cn.rongcapital.mkt.po.WechatInterfaceLog;
import cn.rongcapital.mkt.po.WechatRegister;
import cn.rongcapital.mkt.vo.weixin.WXTag;

@Service
public class WechatGroupBizImpl extends BaseBiz implements WechatGroupBiz {

	@Autowired
	private WechatRegisterDao wechatRegisterDao;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.rongcapital.mkt.biz.WechatGroupBiz#getTags(java.lang.String) \
	 * 获取公众号的标签
	 * {
		    "tags": [
		        {
		            "id": 2,
		            "name": "星标组",
		            "count": 1
		        },
		        {
		            "id": 100,
		            "name": "王华初测试",
		            "count": 1
		        },
		        {
		            "id": 101,
		            "name": "闫俊测试组",
		            "count": 0
		        },
		        {
		            "id": 103,
		            "name": "开发测试组",
		            "count": 1
		        },
		        {
		            "id": 104,
		            "name": "不明粉丝",
		            "count": 1
		        }
		    ]
		}
	 */	
	@Override
	public List<WechatGroup> getTags(String authAppId,String authorizer_refresh_token) {
		List<WechatGroup> list = new ArrayList<WechatGroup>();
		App app = this.getApp();
		app.setAuthAppId(authAppId);
		app.setAuthRefreshToken(authorizer_refresh_token);
		String tagsString = WxComponentServerApi.getBaseWxSdk().getTags(app);
		/**
		 * 记入接口日志到数据库
		 */
		WechatInterfaceLog wechatInterfaceLog = new WechatInterfaceLog("WechatGroupBizImpl","getTags",tagsString,new Date());
		wechatInterfaceLogService.insert(wechatInterfaceLog);
		/**
		 * 组装标签数据
		 */
		JSONObject jsonObject = JSONObject.parseObject(tagsString);
		Integer jsonInt = jsonObject.getInteger("errcode");
		if(StringUtils.isNotBlank(tagsString) && jsonInt == null) {
			list = this.getTagsFromTagsString(app, tagsString);
		}
		return list;
	}

	public List<WechatGroup> getTagsFromTagsString(App app,String tagsString) {
		WechatRegister wechatRegister = new WechatRegister();
		wechatRegister.setAppId(app.getAuthAppId());
		List<WechatRegister> wechatRegisterLists = wechatRegisterDao.selectList(wechatRegister);
		List<WechatGroup> list = new ArrayList<WechatGroup>();
		if(wechatRegisterLists!=null&&wechatRegisterLists.size()>0){
			WechatRegister wg = wechatRegisterLists.get(0);			
			JSONObject jsonObject = JSONObject.parseObject(tagsString);
			JSONArray jsonArray = jsonObject.getJSONArray("tags");
			if(jsonArray != null){
				List<WXTag> wxTags = JSONArray.parseArray(jsonArray.toJSONString(), WXTag.class);
				if (wxTags != null && wxTags.size() > 0) {
					for (Iterator<WXTag> iter = wxTags.iterator(); iter.hasNext();) {
						WXTag wxTag = iter.next();
						if (wxTag != null) {
							WechatGroup wechatGroup = new WechatGroup();
							wechatGroup.setGroupName(wxTag.getName());
							wechatGroup.setGroupId(String.valueOf(wxTag.getId()));
							wechatGroup.setWxAcct(wg.getWxAcct());
							wechatGroup.setGroupNickname(wxTag.getAlias());
							wechatGroup.setHeaderImage(wxTag.getHead_img());
							wechatGroup.setCreateTime(new Date());
							wechatGroup.setCount(wxTag.getCount());
							// BeanUtils.copyProperties(wxTag, wechatGroup);
							list.add(wechatGroup);
						}		
					}
				}
			}	
		}
		return list;
	}

}
