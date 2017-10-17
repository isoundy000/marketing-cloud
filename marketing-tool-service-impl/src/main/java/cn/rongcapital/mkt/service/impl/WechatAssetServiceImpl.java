package cn.rongcapital.mkt.service.impl;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.tagsin.wechat_sdk.user.UserInfo;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.common.util.NumUtil;
import cn.rongcapital.mkt.dao.DataPopulationDao;
import cn.rongcapital.mkt.dao.WechatAssetDao;
import cn.rongcapital.mkt.dao.WechatAssetGroupDao;
import cn.rongcapital.mkt.dao.WechatMemberDao;
import cn.rongcapital.mkt.po.DataPopulation;
import cn.rongcapital.mkt.po.WechatAsset;
import cn.rongcapital.mkt.po.WechatAssetGroup;
import cn.rongcapital.mkt.po.WechatMember;
import cn.rongcapital.mkt.service.WechatAssetService;
import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * 粉丝取消关注
 * @author lijinkai
 * @version 1.2  2016-9-9
 *
 */
@Service
public class WechatAssetServiceImpl implements WechatAssetService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    //关注状态
    private static final byte FOLLOW_STATUS = 0;
    //取消关注
    private static final byte UN_FOLLOW_STATUS = 1;
    
    private final String BITMAP = "00000011000000000";
    
    private final String ACTIVITY_48H_YN = "N";
    
    @Autowired
    private WechatAssetDao wechatAssetDao;
    
    @Autowired
    private WechatMemberDao wechatMemberDao;
    
    @Autowired
    private WechatAssetGroupDao wechatAssetGroupDao;
    
    @Autowired
    private DataPopulationDao dataPopulationDao;
    
	/**
	 * 取消关注公众号
	 * @param wxCode 粉丝Code(wechat_member.wx_code)
	 * @param pubId 公众号ID(wechat_member.pub_id)
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public BaseOutput unfollow(String wxCode, String pubId) {
		
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
		
		WechatMember wechatMember = new WechatMember();
		wechatMember.setWxCode(wxCode);
		wechatMember.setPubId(pubId);
		wechatMember.setStatus(FOLLOW_STATUS);
		List<WechatMember> wechatMemberList = wechatMemberDao.selectList(wechatMember);
		
		if(wechatMemberList != null && wechatMemberList.size() > 0){
			
			wechatMember = wechatMemberList.get(0);
			wechatMember.setStatus(UN_FOLLOW_STATUS);//取消关注
			wechatMemberDao.updateById(wechatMember);
			
			Integer keyid = wechatMember.getKeyid();
			if(keyid!=null&&keyid!=0){
				DataPopulation t = new DataPopulation();
				t.setId(keyid);
				t.setStatus(1);
				dataPopulationDao.updateById(t);
			}
			
			//更新资产
			WechatAsset wechatAsset = new WechatAsset();
			wechatAsset.setWxAcct(wechatMember.getPubId());
			List<WechatAsset> wechatAssetList = wechatAssetDao.selectList(wechatAsset);
			
			if(wechatAssetList != null && wechatAssetList.size() > 0){
				
				wechatAsset = wechatAssetList.get(0);
				wechatAsset.setTotalCount(wechatAsset.getTotalCount() - 1);
				wechatAssetDao.updateById(wechatAsset);
			}
			
			//更新资产组
			WechatAssetGroup wechatAssetGroup = new WechatAssetGroup();
			wechatAssetGroup.setWxAcct(wechatMember.getPubId());
			if(StringUtils.isNotEmpty(wechatMember.getWxGroupId())){
				wechatAssetGroup.setImportGroupId(Long.parseLong(wechatMember.getWxGroupId()));
			}else{
				wechatAssetGroup.setImportGroupId(Long.parseLong(ApiConstant.WECHAT_GROUP));
			}
			List<WechatAssetGroup> wechatAssetGroupList = wechatAssetGroupDao.selectList(wechatAssetGroup);			
			if(wechatAssetGroupList != null && wechatAssetGroupList.size() > 0){				
				wechatAssetGroup = wechatAssetGroupList.get(0);
				wechatAssetGroup.setMembers(wechatAssetGroup.getMembers() - 1);
				wechatAssetGroupDao.updateById(wechatAssetGroup);
			}
			logger.info(ApiErrorCode.SUCCESS.getMsg()+ "wxCode:　+"+wxCode + "pubId:" + pubId);			
		}else{
			
			logger.info(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getMsg()+ "wxCode:　+"+wxCode + "pubId:" + pubId);
			result.setCode(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getCode());
			result.setMsg(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getMsg());
			return result;
			
		}

		return result;
	}

	/**
	 * 关注公众号
	 * @param UserInfo userInfo
	 * @param pubId 公众号ID(wechat_member.pub_id)
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public BaseOutput follow(UserInfo userInfo, String pubId) {

		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
		
		WechatMember wechatMember = new WechatMember();
		wechatMember.setWxCode(userInfo.getOpenid());
		wechatMember.setPubId(pubId);
		List<WechatMember> wechatMemberList = wechatMemberDao.selectList(wechatMember);
		
		//存在粉丝数据,修改关注状态,未关注过新增粉丝关注信息
		if(wechatMemberList != null && wechatMemberList.size() > 0){
			
			wechatMember = wechatMemberList.get(0);
			wechatMember.setStatus(FOLLOW_STATUS);
			wechatMemberDao.updateById(wechatMember);
			
		}else{
			
			wechatMember = getWechatMember(userInfo, pubId);
			wechatMember.setStatus(FOLLOW_STATUS);
			wechatMember.setBitmap(this.BITMAP);
			wechatMember.setActivity48hYn(this.ACTIVITY_48H_YN);
			wechatMember.setSelected(NumUtil.int2OneByte(0));
			wechatMemberDao.insert(wechatMember);
		}
			
		//更新资产
		WechatAsset wechatAsset = new WechatAsset();
		wechatAsset.setWxAcct(wechatMember.getPubId());
		List<WechatAsset> wechatAssetList = wechatAssetDao.selectList(wechatAsset);
			
		if(wechatAssetList != null && wechatAssetList.size() > 0){
				
			wechatAsset = wechatAssetList.get(0);
			wechatAsset.setTotalCount(wechatAsset.getTotalCount() + 1);
			wechatAssetDao.updateById(wechatAsset);
		}
			
		//更新资产组
		WechatAssetGroup wechatAssetGroup = new WechatAssetGroup();
		wechatAssetGroup.setWxAcct(wechatMember.getPubId());
		if(StringUtils.isNotEmpty(wechatMember.getWxGroupId())){
			wechatAssetGroup.setImportGroupId(Long.parseLong(wechatMember.getWxGroupId()));
		}else{
			wechatAssetGroup.setImportGroupId(Long.parseLong(ApiConstant.WECHAT_GROUP));
		}		
		List<WechatAssetGroup> wechatAssetGroupList = wechatAssetGroupDao.selectList(wechatAssetGroup);			
		if(wechatAssetGroupList != null && wechatAssetGroupList.size() > 0){				
			wechatAssetGroup = wechatAssetGroupList.get(0);
			wechatAssetGroup.setMembers(wechatAssetGroup.getMembers() + 1);			
			wechatAssetGroupDao.updateById(wechatAssetGroup);
		}
			
		return result;
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
        Long millisecond = new Long(userInfo.getSubscribe_time()) * 1000;
        Date data = new Date(millisecond);
        // 关注时间
        wechatMember.setSubscribeTime(DateUtil.getStringFromDate(data, "yyyy-MM-dd HH:mm:ss"));
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
        if(StringUtils.isNotEmpty(sb.toString())){
        	wechatMember.setWxGroupId(sb.toString());
        }else{
        	wechatMember.setWxGroupId(ApiConstant.WECHAT_GROUP_M);
        }
        wechatMember.setPubId(wxAcct);
        String fansJson = JSONObject.toJSONString(wechatMember);
        wechatMember.setFansJson(fansJson);
        return wechatMember;
    }
	
}
