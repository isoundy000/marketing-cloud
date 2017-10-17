package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.common.util.GenderUtils;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.dao.WechatAssetGroupDao;
import cn.rongcapital.mkt.dao.WechatMemberDao;
import cn.rongcapital.mkt.po.WechatAssetGroup;
import cn.rongcapital.mkt.service.WechatAssetMemberSearchService;
import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * Created by Yunfeng on 2016-6-28.
 */
@Service
public class WechatAssetMemberSearchServiceImpl implements WechatAssetMemberSearchService{

    @Autowired
    private WechatAssetGroupDao wechatAssetGroupDao;

    @Autowired
    private WechatMemberDao wechatMemberDao;

    @Autowired
    private DataPartyDao dataPartyDao;

    @Override
    public BaseOutput searchWechatAssetMember(String groupIds,String searchField) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO,null);
        if(groupIds == null) return baseOutput;
        if(searchField == null || "".equals(searchField.trim())){
            baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
            baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
            return baseOutput;
        }

        ArrayList<Long> idList = new ArrayList<Long>();
        getIdList(groupIds, idList);

        List<WechatAssetGroup> wechatAssetGroups = wechatAssetGroupDao.selectImportGroupsByIds(idList);
        
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("wx_name", searchField);
        paramMap.put("wechatAssetGroups",wechatAssetGroups);
        List<Map<String,Object>> searchResult = wechatMemberDao.selectSearchInfo(paramMap);
        if(searchResult != null && searchResult.size() > 0){
            for(Map<String,Object> map : searchResult){
                Integer id = (Integer) map.get("contact_id");
                map.put("id",id);
                if(map.get("sex") != null){
                    Integer sex = (Integer) map.get("sex");
                    map.put("gender", GenderUtils.byteToChar(sex.byteValue()));
                    map.remove("sex");
                }
                if(map.get("head_image_url") == null){
                    map.put("head_image_url","http://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png");
                }
                if(map.get("birthday") != null){
                    try {
                        Integer age = 0;
                        String date = (String) map.get("birthday");
                        Date now = new Date(System.currentTimeMillis());
                        String nowYear = DateUtil.getStringFromDate(now,"yyyy");
                        if(date.length() > 5){
                            age = Integer.parseInt(nowYear) - Integer.parseInt(date.substring(0,4));
                        }
                        map.remove("birthday");
                        map.put("age",age);
                    }catch (Throwable throwable){
                        throwable.printStackTrace();
                    }
                }
                baseOutput.getData().add(map);
            }
        }

        baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
        baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
        baseOutput.setTotal(baseOutput.getData().size());
        return baseOutput;
    }

    private Object transformToDataPartyId(Long id) {
        Integer dataParyId = dataPartyDao.selectIdByMappingId(id);
        return dataParyId;
    }

    private void getIdList(String groupIds, ArrayList<Long> idList) {
        if(groupIds.contains(",")){
            for(String id : groupIds.split(",")){
                idList.add(Long.parseLong(id));
            }
        }else{
            idList.add(Long.parseLong(groupIds));
        }
    }

}
