package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.GenderEnum;
import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.common.util.FileUtil;
import cn.rongcapital.mkt.dao.WechatAssetGroupDao;
import cn.rongcapital.mkt.dao.WechatGroupDao;
import cn.rongcapital.mkt.dao.WechatMemberDao;
import cn.rongcapital.mkt.po.WechatAssetGroup;
import cn.rongcapital.mkt.po.WechatGroup;
import cn.rongcapital.mkt.po.WechatMember;
import cn.rongcapital.mkt.service.WechatPeopleDetailDownloadService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.DownloadFileName;
import cn.rongcapital.mkt.vo.out.WechatPeopleDetailDownloadOut;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-6-24.
 */
@Service
public class WechatPeopleDetailDownloadServiceImpl implements WechatPeopleDetailDownloadService {

    @Autowired
    private WechatAssetGroupDao wechatAssetGroupDao;
    @Autowired
    private WechatMemberDao wechatMemberDao;
    @Autowired
    private WechatGroupDao wechatGroupDao;
    
    @Override
    public Object downloadWechatPeopleDetail(String group_ids) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO,null);
        ArrayList<Long> groupIds = new ArrayList<Long>();
        File file = null;
        //1.将group_ids进行拆分，获取组id
        if(group_ids != null){
            if(group_ids.contains(",")){
                String[] ids = group_ids.split(",");
                for (String id : ids){
                    groupIds.add(Long.parseLong(id));
                }
            }else{
                groupIds.add(Long.parseLong(group_ids));
            }
            //通过asset_group_id查询这些group的总人数
            Long totalGroupCount = wechatAssetGroupDao.sumGroupMemberCount(groupIds);
            //通过组id查询import的group_id
            List<WechatAssetGroup> wechatAssetGroups = wechatAssetGroupDao.selectImportGroupsByIds(groupIds);
            //3.根据import_id选取出人。
            List<WechatMember> peopleDetails = wechatMemberDao.selectPeopleDetails(wechatAssetGroups);
            peopleDetails = this.getPeopleDetails(peopleDetails);
//            file = FileUtil.generateDownloadFile(peopleDetails,"wxDetails");
            List<Map<String, String>> columnNames = getHeadColumnsMap();
            file = FileUtil.generateFileforDownload(columnNames, peopleDetails, "wxDetails");
            baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
            baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
            WechatPeopleDetailDownloadOut wechatPeopleDetailDownloadOut = new WechatPeopleDetailDownloadOut();
            wechatPeopleDetailDownloadOut.setDownloadFileName(file.getName());
            wechatPeopleDetailDownloadOut.setTotalCount(totalGroupCount);
            baseOutput.getData().add(wechatPeopleDetailDownloadOut);
        }
        return baseOutput;
    }
    
    private List<WechatMember> getPeopleDetails(List<WechatMember> peopleDetails){
    	if(CollectionUtils.isNotEmpty(peopleDetails)){
    		WechatMember wechatMember0 = peopleDetails.get(0);
    		Map<String,String> wechatGroupMap = getWechatGroupMapByPubId(wechatMember0.getPubId());
    		for(WechatMember wechatMember : peopleDetails){
    			String wxGroupId = wechatMember.getWxGroupId();
    			if(StringUtils.isNotEmpty(wxGroupId)){
    				String groupNames = getGroupNames(wxGroupId,wechatGroupMap);
    				wechatMember.setWxGroupId(groupNames);
    			}
    			Integer sex = wechatMember.getSex();
    			if(sex!=null){
    				switch(sex){
	    				case(1):{
	    					wechatMember.setSexC(GenderEnum.MALE.getDescription());
	    					break;
	    				}
						case(2):{
							wechatMember.setSexC(GenderEnum.FEMALE.getDescription());
	    					break;    					
						}
						default:{
							wechatMember.setSexC(GenderEnum.UNSURE.getDescription());
	    					break;
						}    				
    				}
    			}
    		}
    	}
		return peopleDetails;    	
    }
    

    private String getGroupNames(String wxGroupId,Map<String,String> wechatGroupMap){   	
    	StringBuffer groupNames = new StringBuffer();
    	String[] groupIds = wxGroupId.split(",");
    	if(groupIds!=null&&groupIds.length>0){
    		for(int i=0;i<groupIds.length;i++){
    			String groupId = groupIds[i];
    			if(StringUtils.isNotEmpty(groupId)&&wechatGroupMap.containsKey(groupId)){
    				groupNames.append(wechatGroupMap.get(groupId)).append("、");
    			}
    		}
    	}
    	if(groupNames.length()>0){
    		groupNames = groupNames.deleteCharAt(groupNames.length()-1);
    	}
		return groupNames.toString();    	
    }
    
    private Map<String,String> getWechatGroupMapByPubId(String pubId){
    	Map<String,String> wechatGroupMap = new HashMap<String,String>();
    	WechatGroup t = new WechatGroup();
    	t.setWxAcct(pubId);
//    	t.setStatus(StatusEnum.ACTIVE.getStatusCode().byteValue());
    	t.setStartIndex(null);
    	t.setPageSize(null);
    	List<WechatGroup> wechatGroups = wechatGroupDao.selectList(t);
    	if(CollectionUtils.isNotEmpty(wechatGroups)){
    		for(WechatGroup wechatGroup:wechatGroups){
    			if(wechatGroup!=null&&!wechatGroupMap.containsKey(wechatGroup.getGroupId())){
    				wechatGroupMap.put(wechatGroup.getGroupId(), wechatGroup.getGroupName());
    			}
    		}
    	}
		return wechatGroupMap;
    }
    
    private List<Map<String, String>> getHeadColumnsMap(){
    	String[][] columnNameList = { { "wxName", "名称" }, { "country", "国家" }, { "city", "城市" },
    			{ "sexC", "性别" }, { "subscribeTime", "关注时间" },{ "headImageUrl", "头像地址" },
    			{ "wxGroupId", "分组" }, { "nickname", "昵称" }, { "wxCode", "OPEN_ID" }};

    	List<Map<String, String>> columnsMapList = new ArrayList<Map<String, String>>();

    	for (String[] column : columnNameList) {
    		Map<String, String> columnsNameMap = new HashMap<String, String>();
    		columnsNameMap.put(column[0], column[1]);
    		columnsMapList.add(columnsNameMap);
    	}
		return columnsMapList;

    }  
}
