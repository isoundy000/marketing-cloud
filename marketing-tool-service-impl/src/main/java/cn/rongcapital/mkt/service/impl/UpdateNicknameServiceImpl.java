package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.WechatAssetDao;
import cn.rongcapital.mkt.service.UpdateNicknameService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.UpdateNicknameIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-6-1.
 */
@Service
public class UpdateNicknameServiceImpl implements UpdateNicknameService{

    @Autowired
    private WechatAssetDao wechatAssetDao;

    @Override
    public Object updateNickname(UpdateNicknameIn updateNicknameIn, SecurityContext securityContext) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO,null);
        if(!checkUpdateNickNameIn(updateNicknameIn)){
            return Response.ok().entity(baseOutput).build();
        }

        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("asset_id",updateNicknameIn.getAssetId());
        paramMap.put("nickname",updateNicknameIn.getNickname());
        int effectRows = wechatAssetDao.updateNicknameById(paramMap);
        if(effectRows > 0){
            baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
            baseOutput.setTotal(effectRows);
            baseOutput.setMsg("昵称修改成功");
        }else{
            baseOutput.setMsg("昵称修改失败,原因不明");
        }
        return Response.ok().entity(baseOutput).build();
    }

    private boolean checkUpdateNickNameIn(UpdateNicknameIn updateNicknameIn) {
        if(updateNicknameIn.getAssetId() == null || updateNicknameIn.getNickname()==null){
            return false;
        }
        return true;
    }
}
