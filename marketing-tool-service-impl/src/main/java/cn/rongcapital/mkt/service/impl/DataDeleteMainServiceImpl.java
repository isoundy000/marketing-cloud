package cn.rongcapital.mkt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.service.DataDeleteMainService;

@Service
public class DataDeleteMainServiceImpl implements DataDeleteMainService {

    @Autowired
    private DataPartyDao dataPartyDao;

    @Override
    public Object deleteMain(String method, String userToken, String ver, Integer dataId) {

//        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
//                        ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
//
//        DataParty paramDataParty = new DataParty();
//        paramDataParty.setId(dataId);
//        paramDataParty.setDeleted(Boolean.TRUE);
//        paramDataParty.setDeleteTime(new Date());
//
//        int rowEffected = dataPartyDao.logicDeleteById(paramDataParty);
//        if (rowEffected == 0) {
//            result.setMsg("不存在id为" + dataId + "的数据");
//        }
//
//        result.setTotal(result.getData().size());

//        return Response.ok().entity(result).build();
        return null;
    }

}
