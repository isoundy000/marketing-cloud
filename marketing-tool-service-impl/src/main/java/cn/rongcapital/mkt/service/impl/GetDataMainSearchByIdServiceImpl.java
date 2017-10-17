package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.AudienceListPartyMapDao;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.service.GetDataMainSearchByIdService;
import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * Created by Yunfeng on 2016-6-8.
 */
@Service
public class GetDataMainSearchByIdServiceImpl implements GetDataMainSearchByIdService{
    @Autowired
    private AudienceListPartyMapDao audienceListPartyMapDao;
    @Autowired
    private DataPartyDao dataPartyDao;

    @Override
    public BaseOutput searchDataMainById(int id, int type) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO,null);
        //Todo:1根据Type选用合适的Dao
        //Todo:先处理人群的查询
        switch (type){
            case 0:
                searchSegmentationDetail(id,baseOutput);
                break;
            case 1:
                searchCampaignDetail(id,baseOutput);
                break;
            case 2:
                searchAudienceDetail(id,baseOutput);
                break;
            default: baseOutput.setMsg("类型匹配失败，请检查参数！");
        }
        //Todo:2根据不同的Dao查询不同的表返回相应的数据
        return baseOutput;
    }

    private void searchSegmentationDetail(int id, BaseOutput baseOutput) {

    }

    private void searchCampaignDetail(int id, BaseOutput baseOutput) {

    }

    private void searchAudienceDetail(int id, BaseOutput baseOutput) {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("audience_list_id",id);
        List<Map<String,Object>> partyIds = audienceListPartyMapDao.searchPartyList(paramMap);
        for(Map<String,Object> map : partyIds){
            Map<String,Object> resultPerson = dataPartyDao.selectAudienceDetail(map);
            baseOutput.getData().add(resultPerson);
        }
    }
}
