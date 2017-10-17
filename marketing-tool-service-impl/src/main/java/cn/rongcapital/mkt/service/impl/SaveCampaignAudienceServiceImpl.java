package cn.rongcapital.mkt.service.impl;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.AudienceListDao;
import cn.rongcapital.mkt.po.AudienceList;
import cn.rongcapital.mkt.service.SaveCampaignAudienceService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.Audience;

/**
 * Created by Xu kun on 2016-6-1.
 */
@Service
public class SaveCampaignAudienceServiceImpl implements SaveCampaignAudienceService{

    @Autowired
    private AudienceListDao audienceListDao;
    

    @Override    
    public Object saveCampaignAudience(Audience audience, SecurityContext securityContext) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO,null);

        //1.现根据传进来的name判断这个人群名称是否已经存在，存在返回，不存在继续下一步
        AudienceList audienceListT = new AudienceList();
        audienceListT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        audienceListT.setAudienceName(audience.getAudience_name());
        int count = audienceListDao.selectListCount(audienceListT);
        if(count > 0) {
        	baseOutput.setCode(ApiErrorCode.VALIDATE_ERROR.getCode());
            baseOutput.setMsg("人群名称已经重复");
            return Response.ok().entity(baseOutput).build();
        }
        
        //2.保存人群名称到audience_list表中        
        audienceListT.setAudienceRows(0);
        audienceListT.setSource(ApiConstant.AUDIENCE_SOUCE_NAME_CAMPAIGN);
        audienceListDao.insert(audienceListT);

        baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
        baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
        baseOutput.setTotal(0);
        return Response.ok().entity(baseOutput).build();
    }
}
