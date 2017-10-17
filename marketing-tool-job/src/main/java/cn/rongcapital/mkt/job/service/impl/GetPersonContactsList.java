package cn.rongcapital.mkt.job.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.util.HttpUtils;
import cn.rongcapital.mkt.dao.TenementDao;
import cn.rongcapital.mkt.dao.WechatMemberDao;
import cn.rongcapital.mkt.dao.WechatRegisterDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.job.vo.in.H5PersonalContactlistResponse;
import cn.rongcapital.mkt.job.vo.in.PersonalContact;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-6-20.
 */
@Service
public class GetPersonContactsList implements TaskService {

    @Autowired
    private TenementDao tenementDao;
    @Autowired
    private WechatMemberDao wechatMemberDao;
    @Autowired
    private GetUUidListServiceImpl getUUidListService;
    @Autowired
    private IsUuidEffectiveService isUuidEffectiveService;
    @Autowired
    private WechatRegisterDao wechatRegisterDao;
    @Autowired
    private GetGroupIdServiceImpl getGroupIdService;

    @Override
    public void task(Integer taskId) {
        Map<String,String> h5ParamMap = new HashMap<String,String>();
        h5ParamMap.put("pid",tenementDao.selectPid().get("pid"));
        h5ParamMap.put(ApiConstant.DL_API_PARAM_METHOD,ApiConstant.DL_PERSONAL_CONTACTLIST);

        List<String> uuids = getUUidListService.getUuidList();
        if(uuids == null) return ;

        for(String uuid : uuids){
            if(isUuidEffectiveService.isUuidEffective(uuid)){
                h5ParamMap.put("uuid",uuid);
                HttpResponse httpResponse = HttpUtils.requestH5Interface(h5ParamMap);
                if(httpResponse != null){
                    JSONObject obj = null;
                    try {
                        obj = JSON.parseObject(EntityUtils.toString(httpResponse.getEntity())).getJSONObject("hfive_mkt_personal_contactlist_response");
                        if(obj != null){
                            H5PersonalContactlistResponse h5PersonalContactlistResponse = JSON.parseObject(obj.toString(),H5PersonalContactlistResponse.class);
                            if(h5PersonalContactlistResponse.getContacts() != null && h5PersonalContactlistResponse.getContacts().getContact() != null){
                                batchInsertContacts(h5PersonalContactlistResponse,h5PersonalContactlistResponse.getUin());
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    //Todo:如何过滤掉微信好友名称为图片的好友
    private void batchInsertContacts(H5PersonalContactlistResponse h5PersonalContactlistResponse,String uin) {
        List<Map<String,Object>> paramContacts = new ArrayList<Map<String,Object>>();
        //1.首先通过uin和好友组确定是否已经存储了这个人的好友组，如果没有则先存储这个人的好友组，如果存储了则获取这个组的groupid
        Integer groupId = getGroupIdService.getGroupIdByOwnerIdAndGroupname(uin,"好友组");
        //2.获取了group_id以后需要根据group_id和微信号进行判重，重复了就不插入数据了
        for(PersonalContact personalContact : h5PersonalContactlistResponse.getContacts().getContact()){
            if(personalContact == null || groupId == null || personalContact.getUcode() == null) continue;
            if(!isFriendAlreadySave(groupId,personalContact.getUcode())){
                Map<String,Object> paramContact = new HashMap<String,Object>();
                paramContact.put("wx_group_id",groupId);
                paramContact.put("wx_code", personalContact.getUcode());
                if(personalContact.getNickname() != null){
                    paramContact.put("wx_name",personalContact.getNickname().replaceAll("[^\\u0000-\\uFFFF]", ""));
                }

                if("男".equals(personalContact.getSex())){
                    paramContact.put("sex", 1);
                }else if("女".equals(personalContact.getSex())){
                    paramContact.put("sex", 2);
                }else {
                    paramContact.put("sex", 3);
                }
                paramContact.put("signature", personalContact.getSignature());
                paramContact.put("province", personalContact.getProvince());
                paramContact.put("city", personalContact.getCity());
                paramContact.put("uin", uin);
                paramContacts.add(paramContact);
            }
        }
        if(paramContacts.size() != 0){
            wechatMemberDao.batchInsertContacts(paramContacts);
        }
    }

    private boolean isFriendAlreadySave(Integer groupId, String ucode) {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("wx_group_id",groupId);
        paramMap.put("wx_code",ucode);
        Long id = wechatMemberDao.selectIdByGroupIdAndWxAcct(paramMap);
        return !(id==null);
    }
}
