package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.CampaignActionSendPubDao;
import cn.rongcapital.mkt.dao.CampaignAudienceTargetDao;
import cn.rongcapital.mkt.po.CampaignActionSendPub;
import cn.rongcapital.mkt.po.CampaignAudienceTarget;
import cn.rongcapital.mkt.po.mongodb.NodeAudience;
import cn.rongcapital.mkt.service.GetCampaignConvertChartListService;
import cn.rongcapital.mkt.vo.out.CampaignConvertChartListData;
import cn.rongcapital.mkt.vo.out.CampaignConvertChartListOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yunfeng on 2016-7-25.
 */
@Service
public class GetCampaignConvertChartListServiceImpl implements GetCampaignConvertChartListService {

    //1创立返回的VO对象，完成初始化操作
    //2查询这个活动截止目前为止覆盖的人群总数
    //Todo:3查询这个活动截止目前为止收到了微信图文的总人数

    @Autowired
    private CampaignAudienceTargetDao campaignAudienceTargetDao;

    @Autowired
    private CampaignActionSendPubDao campaignActionSendPubDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final Integer ALREADY_SEND_PUB_STATUS = 1;

    @Override
    public CampaignConvertChartListOut getCompaignConvertChartList(Integer campaignHeadId) {

        //1创立返回的VO对象，完成初始化操作
        CampaignConvertChartListOut campaignConvertChartListOut = new CampaignConvertChartListOut(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO);
        CampaignConvertChartListData campaignConvertChartListData = new CampaignConvertChartListData();

        //2查询这个活动截止目前为止覆盖的人群总数
        //2.1首先在campaign_audience_target表中查询出这个活动对应的itemId，如果没有提示数据库有效性错误，如果有继续
        CampaignAudienceTarget campaignAudienceTarget = new CampaignAudienceTarget();
        campaignAudienceTarget.setCampaignHeadId(campaignHeadId);
        campaignAudienceTarget.setPageSize(Integer.MAX_VALUE);
        campaignAudienceTarget.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        List<CampaignAudienceTarget> campaignAudienceTargetList = campaignAudienceTargetDao.selectList(campaignAudienceTarget);
        if(CollectionUtils.isEmpty(campaignAudienceTargetList)){
            campaignConvertChartListOut.setCode(ApiErrorCode.VALIDATE_ERROR.getCode());
            campaignConvertChartListOut.setMsg("经过查询缺少这个活动对应的目标人群，请检查活动编排是否合理");
            return campaignConvertChartListOut;
        }
        List<String> itemIdList = new ArrayList<>(campaignAudienceTargetList.size());
        for(CampaignAudienceTarget audienceTarget : campaignAudienceTargetList){
            itemIdList.add(audienceTarget.getItemId());
        }
        //2.2根据itemId在芒果中筛选出人群总数,然后将它放到campaignConvertChartData中
        Long totalCount = mongoTemplate.count(Query.query(Criteria.where("campaignHeadId").is(campaignHeadId).and("itemId").in(itemIdList)), NodeAudience.class);
        campaignConvertChartListData.setPeopleTotalCount(totalCount.intValue());

        //3查询这个活动截止目前为止收到了微信图文的总人数
        //3.1首先在campaign_action_send_pub中查询出发送微信图文这个动作对应的itemId，如果没有则这只人数为0，如果有则继续
        CampaignActionSendPub campaignActionSendPub = new CampaignActionSendPub();
        campaignActionSendPub.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        campaignActionSendPub.setCampaignHeadId(campaignHeadId);
        campaignActionSendPub.setPageSize(Integer.MAX_VALUE);
        List<CampaignActionSendPub> campaignActionSendPubList = new ArrayList<CampaignActionSendPub>();
        campaignActionSendPubList = campaignActionSendPubDao.selectList(campaignActionSendPub);
        if(CollectionUtils.isEmpty(campaignActionSendPubList)){
            campaignConvertChartListOut.setMsg("经过查询这个活动没有发送微信图文");
        }else{
            //Todo:3.2根据itemId在芒果中根据sent_status为1的值代表已经发送了微信图文的人，然后将它设置到campaignConvertChartData中(待测试，需要有一个新的活动)
            itemIdList = new ArrayList<String>(campaignActionSendPubList.size());
            for(CampaignActionSendPub actionSendPub : campaignActionSendPubList){
                itemIdList.add(actionSendPub.getItemId());
            }
            Long coverCount = mongoTemplate.count(Query.query(Criteria.where("campaignHeadId").is(campaignHeadId).and("sentStatus").is(ALREADY_SEND_PUB_STATUS).and("itemId").in(itemIdList)),NodeAudience.class);
            campaignConvertChartListData.setPeopleCoverCount(coverCount.intValue());
        }
        campaignConvertChartListOut.getDataCustom().add(campaignConvertChartListData);
        return campaignConvertChartListOut;
    }

}
