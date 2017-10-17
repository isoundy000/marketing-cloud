package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.AudienceListDao;
import cn.rongcapital.mkt.dao.CampaignHeadDao;
import cn.rongcapital.mkt.dao.SegmentationHeadDao;
import cn.rongcapital.mkt.dao.base.BaseDao;
import cn.rongcapital.mkt.service.GetDataMainSearchService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.DataMainSearchIn;

/**
 * Created by Yunfeng on 2016-6-7.
 */
@Service
public class GetDataMainSearchServiceImpl implements GetDataMainSearchService {
    @Autowired
    private SegmentationHeadDao segmentationHeadDao;
    @Autowired
    private CampaignHeadDao campaignHeadDao;
    @Autowired
    private AudienceListDao audienceListDao;

    @Override
    public BaseOutput searchDataMain(@NotEmpty DataMainSearchIn dataMainSearchIn) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO,null);

        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("name","%" + dataMainSearchIn.getName() + "%");
        paramMap.put("size",3);

        searchMainDataFromTable(segmentationHeadDao,paramMap,baseOutput);
        searchMainDataFromTable(campaignHeadDao,paramMap,baseOutput);
        searchMainDataFromTable(audienceListDao,paramMap,baseOutput);

        baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
        baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
        baseOutput.setTotal(baseOutput.getData().size());
        return baseOutput;
    }

    private void searchMainDataFromTable(BaseDao<?> baseDao,Map<String,Object> paramMap,BaseOutput baseOutput){
        int type = -1;
        List<Map<String,Object>> resultList = null;
        if(baseDao instanceof  SegmentationHeadDao){
            resultList = ((SegmentationHeadDao)baseDao).searchDataMain(paramMap);
            type = 0;
        }
        if(baseDao instanceof CampaignHeadDao){
            resultList = ((CampaignHeadDao)baseDao).searchDataMain(paramMap);
            type = 1;
        }
        if(baseDao instanceof AudienceListDao){
            resultList = ((AudienceListDao)baseDao).searchDataMain(paramMap);
            type = 2;
        }
        addSearchResultToResponse(baseOutput, resultList,type);
    }

    private void addSearchResultToResponse(BaseOutput baseOutput, List<Map<String, Object>> resultList,int type) {
        if(resultList != null && resultList.size() != 0){
            for(Map<String,Object> map : resultList){
                map.put("type",type);
                baseOutput.getData().add(map);
            }
        }
    }
}
