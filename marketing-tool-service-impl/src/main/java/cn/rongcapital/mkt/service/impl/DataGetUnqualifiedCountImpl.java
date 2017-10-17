package cn.rongcapital.mkt.service.impl;

import java.util.Map;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.ImportDataHistoryDao;
import cn.rongcapital.mkt.po.ImportDataHistory;
import cn.rongcapital.mkt.service.DataGetUnqualifiedCountService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class DataGetUnqualifiedCountImpl implements DataGetUnqualifiedCountService {
    
    @Autowired
    private ImportDataHistoryDao importDataHistoryDao;

    @Override
    public Object getQualityCount(String method, String userToken, String ver, Long batchId) {
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
                        ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);

        ImportDataHistory paramImportDataHistory = new ImportDataHistory();
        paramImportDataHistory.setId(batchId);
        
        Map<String, Object> resultMap = importDataHistoryDao.selectUnqualifiedCount(paramImportDataHistory);
        result.getData().add(resultMap);
        result.setTotal(result.getData().size());

        return Response.ok().entity(result).build();
    }

}
