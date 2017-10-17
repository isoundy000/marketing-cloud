package cn.rongcapital.mkt.job.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.util.HttpUtils;
import cn.rongcapital.mkt.dao.TenementDao;
import cn.rongcapital.mkt.dao.WechatGroupDao;
import cn.rongcapital.mkt.dao.WechatMemberDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.job.vo.in.H5MktPersonGroupListResponse;
import cn.rongcapital.mkt.job.vo.in.H5PersonalGroup;
import cn.rongcapital.mkt.job.vo.in.H5PersonalGroupMember;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-6-25.
 */
@Service
public class GetH5PersonalGroupListImpl implements TaskService {

    @Autowired
    private TenementDao tenementDao;
    @Autowired
    private WechatMemberDao wechatMemberDao;
    @Autowired
    private WechatGroupDao wechatGroupDao;
    @Autowired
    private GetUUidListServiceImpl getUUidListService;
    @Autowired
    private IsUuidEffectiveService isUuidEffectiveService;
    @Autowired
    private GetGroupIdServiceImpl getGroupIdService;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void task(Integer taskId) {
        Map<String,String> h5ParamMap = new HashMap<String,String>();
        Map<String,String> pidMap = tenementDao.selectPid();
        if(pidMap != null && pidMap.size() > 0 ){
            h5ParamMap.put("pid",tenementDao.selectPid().get("pid"));
        }else{
            return;
        }
        h5ParamMap.put(ApiConstant.DL_API_PARAM_METHOD,ApiConstant.DL_PERSONAL_GROUPLIST);
        List<String> uuids = getUUidListService.getUuidList();
        if(uuids == null) return ;

        for(String uuid : uuids){
            if(isUuidEffectiveService.isUuidEffective(uuid)){
                h5ParamMap.put("uuid",uuid);
                HttpResponse httpResponse = HttpUtils.requestH5Interface(h5ParamMap);
                if(httpResponse != null){
                    JSONObject obj = null;
                    try {
                        String entityString = EntityUtils.toString(httpResponse.getEntity());
                        obj = JSON.parseObject(entityString).getJSONObject("hfive_mkt_personal_grouplist_response");
                        if(obj != null){
                            H5MktPersonGroupListResponse h5MktPersonGroupListResponse = JSON.parseObject(obj.toString(),H5MktPersonGroupListResponse.class);
                            if(h5MktPersonGroupListResponse.getGroups() != null && h5MktPersonGroupListResponse.getGroups().getGroup() != null && h5MktPersonGroupListResponse.getUin() != null){
                                batchInsertWxGroups(h5MktPersonGroupListResponse.getGroups().getGroup(),h5MktPersonGroupListResponse.getUin());
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void batchInsertWxGroups(ArrayList<H5PersonalGroup> h5PersonalGroups, String uin) {
        for(H5PersonalGroup h5PersonGroup : h5PersonalGroups){
            List<Map<String,Object>> paramGroupMembers = new ArrayList<Map<String,Object>>();
            //1.首先通过uin和好友组确定是否已经存储了这个人的好友组，如果没有则先存储这个人的好友组，如果存储了则获取这个组的groupid
            Long groupId = getGroupIdService.getGroupIdByOwnerIdAndGroupId(uin,h5PersonGroup.getUcode());
            //1.5 更新群组的相关信息
            Map<String,Object> updateMap = new HashMap<String,Object>();
            updateMap.put("id",groupId);
            updateMap.put("group_name",h5PersonGroup.getNickname());
            updateMap.put("header_image",h5PersonGroup.getHeadImage());
            wechatGroupDao.updateInfoById(updateMap);
            //2.获取了group_id以后需要根据group_id和微信号进行判重，重复了就不插入数据了
            for(H5PersonalGroupMember h5PersonalGroupMember : h5PersonGroup.getMembers().getMember()){
                if(!isGroupMemberAlreadySaved(h5PersonalGroupMember.getUcode(),groupId)){
                    Map<String,Object> paramGroupMember = new HashMap<String,Object>();
                    paramGroupMember.put("wx_group_id",groupId);
                    paramGroupMember.put("wx_code", h5PersonalGroupMember.getUcode());
                    paramGroupMember.put("wx_name",h5PersonalGroupMember.getNickname().replaceAll("[^\\u0000-\\uFFFF]", ""));
                    paramGroupMember.put("head_image_url", h5PersonalGroupMember.getHeadImage());
                    paramGroupMember.put("nickname",h5PersonalGroupMember.getDisplayName().replaceAll("[^\\u0000-\\uFFFF]", ""));
                    paramGroupMember.put("is_friend", h5PersonalGroupMember.getIsFriend());
                    paramGroupMember.put("uin", uin);
                    paramGroupMembers.add(paramGroupMember);
                }
            }
            if(paramGroupMembers != null && paramGroupMembers.size() > 0){
                wechatMemberDao.batchInsertGroupMember(paramGroupMembers);
            }
        }
    }

    private boolean isGroupMemberAlreadySaved(String ucode, Long groupId) {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("wx_group_id",groupId);
        paramMap.put("wx_code",ucode);
        Long id = wechatMemberDao.selectIdByGroupIdAndWxAcct(paramMap);
        return !(id==null);
    }
}
