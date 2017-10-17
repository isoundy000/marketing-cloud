package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.CampaignAudienceTargetDao;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.po.CampaignAudienceTarget;
import cn.rongcapital.mkt.po.DataCountBySource;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.mongodb.NodeAudience;
import cn.rongcapital.mkt.service.GetCampaignCustomerSourceListService;
import cn.rongcapital.mkt.vo.out.CampaignCustomSourceData;
import cn.rongcapital.mkt.vo.out.CampaignCustomSourceListOut;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yunfeng on 2016-7-25.
 */
@Service
public class GetCampaignCustomerSourceListServiceImpl implements GetCampaignCustomerSourceListService{

    //1.构建返回值
    //2.获取相应人群的itemId，成功则继续，失败返回相应的错误代码
    //3.根据itemId获取dataId
    //4.根据dataId在mysql中查询source和相应的人数
    //5.根据查询结果返回相应的VO对象

    @Autowired
    private CampaignAudienceTargetDao campaignAudienceTargetDao;

    @Autowired
    private DataPartyDao dataPartyDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public CampaignCustomSourceListOut getCampaignCustomSourceInfo(Integer campaignHeadId) {
        //1.构建返回值
        CampaignCustomSourceListOut campaignCustomSourceListOut = new CampaignCustomSourceListOut(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO);

        //2.获取相应人群的itemId，成功则继续，失败返回相应的错误代码
        CampaignAudienceTarget campaignAudienceTarget = new CampaignAudienceTarget();
        campaignAudienceTarget.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        campaignAudienceTarget.setPageSize(Integer.MAX_VALUE);
        campaignAudienceTarget.setCampaignHeadId(campaignHeadId);
        List<String> campaignAudienceTargetItemIdList = campaignAudienceTargetDao.selectItemIdList(campaignAudienceTarget);
        if (isQueryResultEmpty(campaignCustomSourceListOut,"数据库查询显示这个活动没有对应的目标人群，请检查活动编排是否合法！", campaignAudienceTargetItemIdList))
            return campaignCustomSourceListOut;

        //3.根据itemId获取dataId
        Query query = Query.query(Criteria.where("campaignHeadId").is(campaignHeadId).and("itemId").in(campaignAudienceTargetItemIdList));
        List<NodeAudience> nodeAudienceList = mongoTemplate.find(query,NodeAudience.class);
        if(isQueryResultEmpty(campaignCustomSourceListOut,"数据库查询显示选定活动的目标人群包含人数为0，请检查活动的有效性",nodeAudienceList))
            return campaignCustomSourceListOut;
        List<Integer> dataPartyIdList = new ArrayList<Integer>(nodeAudienceList.size());
        for(NodeAudience nodeAudience : nodeAudienceList){
            if(nodeAudience.getDataId() != null){
                dataPartyIdList.add(nodeAudience.getDataId());
            }
        }
        if(isQueryResultEmpty(campaignCustomSourceListOut,"数据库查询显示选定活动的目标人群包含人数为0，请检查活动的有效性",dataPartyIdList))
            return campaignCustomSourceListOut;

        //构建测试的dataPartyIdList
//        List<Integer> dataPartyIdList = getTestIdList();


        //4实现方案一:通过Mysql进行来源统计，根据dataId在mysql中查询source和相应的人数
        List<DataCountBySource> dataCountBySourceList = dataPartyDao.selectDataSourceAndSourceCount(dataPartyIdList);
        if(isQueryResultEmpty(campaignCustomSourceListOut,"数据库查询显示来源统计为空，请检查活动数据的有效性",dataCountBySourceList))
            return campaignCustomSourceListOut;
        List<CampaignCustomSourceData> campaignCustomSourceDataList = new ArrayList<CampaignCustomSourceData>(dataCountBySourceList.size());
        for(DataCountBySource dataCountBySource : dataCountBySourceList){
            if(StringUtils.isEmpty(dataCountBySource.getSource())) continue;
            CampaignCustomSourceData campaignCustomSourceData = new CampaignCustomSourceData();
            BeanUtils.copyProperties(dataCountBySource,campaignCustomSourceData);
            campaignCustomSourceDataList.add(campaignCustomSourceData);
        }

        campaignCustomSourceListOut.getDataCustom().addAll(campaignCustomSourceDataList);
        return campaignCustomSourceListOut;
    }

    private <T> boolean isQueryResultEmpty(CampaignCustomSourceListOut campaignCustomSourceListOut,String errorMsg, List<T> validateList) {
        if(CollectionUtils.isEmpty(validateList)){
            campaignCustomSourceListOut.setCode(ApiErrorCode.VALIDATE_ERROR.getCode());
            campaignCustomSourceListOut.setMsg(errorMsg);
            return true;
        }
        return false;
    }

    private List<Integer> getTestIdList() {
        DataParty dataParty = new DataParty();
        dataParty.setStatus(new Integer(2).byteValue());
        dataParty.setPageSize(Integer.MAX_VALUE);
        List<DataParty> dataPartyList = dataPartyDao.selectList(dataParty);
        List<Integer> dataPartyIdList = new ArrayList<Integer>(dataPartyList.size());
        for(DataParty party : dataPartyList){
            dataPartyIdList.add(party.getId());
        }
        return dataPartyIdList;
    }
}
