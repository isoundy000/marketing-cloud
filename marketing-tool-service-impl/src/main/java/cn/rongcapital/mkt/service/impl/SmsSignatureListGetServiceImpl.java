package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.SmsSignatureDao;
import cn.rongcapital.mkt.po.SmsSignature;
import cn.rongcapital.mkt.service.SmsSignatureListGetService;
import cn.rongcapital.mkt.vo.out.SmsSignatureListOut;
import cn.rongcapital.mkt.vo.out.SmsSignatureOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by byf on 10/18/16.
 */
@Service
public class SmsSignatureListGetServiceImpl implements SmsSignatureListGetService {

    @Autowired
    private SmsSignatureDao smsSignatureDao;

    @Override
    public SmsSignatureListOut getSmsSignatureList() {
        SmsSignatureListOut smsSignatureListOut = new SmsSignatureListOut(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(),ApiConstant.INT_ZERO);

        SmsSignature paramSmsSignature = new SmsSignature();
        paramSmsSignature.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        paramSmsSignature.setPageSize(Integer.MAX_VALUE);

        List<SmsSignature> smsSignatureList = smsSignatureDao.selectList(paramSmsSignature);
        if(!CollectionUtils.isEmpty(smsSignatureList)){
            for(SmsSignature smsSignature : smsSignatureList){
                SmsSignatureOut smsSignatureOut = new SmsSignatureOut();
                smsSignatureOut.setId(smsSignature.getId());
                smsSignatureOut.setSignatrueName(smsSignature.getSmsSignatureName());
                smsSignatureListOut.getSmsSignatureOutList().add(smsSignatureOut);
            }
        }

        smsSignatureListOut.setTotal(smsSignatureListOut.getSmsSignatureOutList().size());
        return smsSignatureListOut;
    }

}
