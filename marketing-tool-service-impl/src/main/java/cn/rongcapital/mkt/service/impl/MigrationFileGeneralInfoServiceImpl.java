package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.ImportDataHistoryDao;
import cn.rongcapital.mkt.service.MigrationFileGeneralInfoService;
import cn.rongcapital.mkt.vo.BaseInput;
import cn.rongcapital.mkt.vo.BaseOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-5-30.
 */
@Service
public class MigrationFileGeneralInfoServiceImpl implements MigrationFileGeneralInfoService{

    @Autowired
    ImportDataHistoryDao importDataHistoryDao;

    @Override
    public Object getMigrationFileGeneralInfo(BaseInput baseInput) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO,null);
        Map<String,Object> generalInfos = importDataHistoryDao.selectMigrationFileGeneralInfo();
        if(generalInfos != null){
            generalInfos.put("migarated_row_count",generalInfos.remove("total_rows"));
            generalInfos.put("last_upload_time",generalInfos.remove("import_end_time"));
            baseOutput.getData().add(generalInfos);
        }else{
            generalInfos = new HashMap<String,Object>();
            generalInfos.put("migarated_row_count",new Integer(0));
            generalInfos.put("last_upload_time",null);
            baseOutput.getData().add(generalInfos);
        }
        baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
        baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
        baseOutput.setTotal(baseOutput.getData().size());
        return Response.ok().entity(baseOutput).build();
    }
}
