package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.*;
import cn.rongcapital.mkt.po.WechatAssetGroup;
import cn.rongcapital.mkt.po.WechatMember;
import cn.rongcapital.mkt.service.DataPopulationService;
import cn.rongcapital.mkt.service.SaveWechatAssetListService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.SaveWechatAssetListIn;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.*;

/**
 * Created by Yunfeng on 2016-6-1.
 */
@Service
public class SaveWechatAssetListServiceImpl implements SaveWechatAssetListService{

    @Autowired
    private AudienceListDao audienceListDao;
    @Autowired
    private AudienceListPartyMapDao audienceListPartyMapDao;
/*    @Autowired
    private DataPartyDao dataPartyDao;
    @Autowired
    private WechatGroupDao wechatGroupDao;*/
    @Autowired
    private WechatAssetGroupDao wechatAssetGroupDao;

    @Autowired
    private WechatMemberDao wechatMemberDao;
    @Autowired
    private DataPopulationDao dataPopulationDao;
    @Autowired
    private DataPopulationService dataPopulationService;
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Object saveWechatAssetList(SaveWechatAssetListIn saveWechatAssetListIn, SecurityContext securityContext) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO,null);

        //0.现根据传进来的name判断这个人群名称是否已经存在，存在返回，不存在继续下一步
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("audience_name",saveWechatAssetListIn.getPeopleGroupName());
        Long id = audienceListDao.selectIdByAudienceName(paramMap);
        if(id != null){
            baseOutput.setCode(ApiErrorCode.VALIDATE_ERROR.getCode());
            baseOutput.setMsg("人群名称已经重复");
            return Response.ok().entity(baseOutput).build();
        }

        //1.统计人群总数，将人群名称和人群总数保存到audience_list表中
        Long totalAudienceNumber = wechatAssetGroupDao.sumGroupMemberCount(saveWechatAssetListIn.getGroupIds());
        //微信保存人群总数默认是0，具体多少人通过后台job去计算
//        Long totalAudienceNumber = 0l;
        paramMap = new HashMap<String,Object>();
        paramMap.put("audience_name",saveWechatAssetListIn.getPeopleGroupName());
        paramMap.put("audience_rows",totalAudienceNumber);
        paramMap.put("create_time",new Date(System.currentTimeMillis()));
        paramMap.put("source", ApiConstant.WEIXIN_AUDIENCE_SOURCE);        
        audienceListDao.insertWechatGroups(paramMap);
        id = audienceListDao.selectIdByAudienceName(paramMap);

        //2.根据groupId选出import_groupId。
        List<WechatAssetGroup> wechatAssetGroups = wechatAssetGroupDao.selectImportGroupsByIds(saveWechatAssetListIn.getGroupIds());

        //3.根据import_groupId，选出member的Id
        List<Long> idLists = wechatMemberDao.selectIdListByGroupId(wechatAssetGroups);
        //4.根据ID筛选出需要同步到data_population的member,然后同步到data_population
        List<WechatMember> wechatMembers = wechatMemberDao.selectListByIdListNoSelected(idLists);
        if(CollectionUtils.isNotEmpty(wechatMembers)){
        	dataPopulationService.synchronizeMemberToDataPopulationAndUpdateMember(wechatMembers);
        }
        //Todo:4这块需要修改，从member中选出data_population的ID，然后从data_population中选出data_party
        //Todo: 的Id，然后将这些Ids插入到audience_map中去
        //1.获取data_population的Id
        List<Integer> memberKeyidList = wechatMemberDao.selectKeyidListByIdList(idLists);
        //2.获取data_party的Id
        List<Integer> populationKeyidList = dataPopulationDao.selectKeyidListByIdList(memberKeyidList);

        //Todo:5.将dataparty_id和audience_id存入audience_data_mapping表中
        List<Map<String,Object>> paramInsertLists = new ArrayList<Map<String,Object>>();
        for(Integer populationKeyid : populationKeyidList){
        	if(populationKeyid!=null){
                paramMap = new HashMap<String,Object>();
                paramMap.put("audience_list_id",id);
                paramMap.put("party_id",populationKeyid);
                paramMap.put("create_time",new Date(System.currentTimeMillis()));
                paramInsertLists.add(paramMap);
        	}
        }
        int effectRows = 0;
        if(paramInsertLists != null && paramInsertLists.size() > 0){
            effectRows = audienceListPartyMapDao.batchInsert(paramInsertLists);
        }

        baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
        baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
        baseOutput.setTotal(effectRows);
        return Response.ok().entity(baseOutput).build();
    }
}
