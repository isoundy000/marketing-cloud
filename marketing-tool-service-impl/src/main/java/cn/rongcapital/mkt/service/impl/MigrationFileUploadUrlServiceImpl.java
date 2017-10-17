package cn.rongcapital.mkt.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.ImportDataHistoryDao;
import cn.rongcapital.mkt.service.MigrationFileUploadUrlService;
import cn.rongcapital.mkt.vo.BaseInput;
import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * Created by Yunfeng on 2016-5-30.
 */
@Service
public class MigrationFileUploadUrlServiceImpl implements MigrationFileUploadUrlService {

    @Autowired
    private ImportDataHistoryDao importDataHistoryDao;

    @Override
    public Object getMigrationFileUploadUrl(BaseInput input) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(),1,null);
        String fileUnique = RandomStringUtils.random(5,true,true) + System.currentTimeMillis();
        insertImportFileUnique(fileUnique);

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("file_unique",fileUnique);
        map.put("file_url", ApiConstant.FILE_UPLOAD_URL);
        baseOutput.getData().add(map);
        baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
        baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
        return Response.ok().entity(baseOutput).build();
    }

    private void insertImportFileUnique(String fileUnique) {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("import_start_time",new Date(System.currentTimeMillis()));
        paramMap.put("file_unique",fileUnique);
        paramMap.put("status",Integer.valueOf(1));
        importDataHistoryDao.insertFileUnique(paramMap);
    }
}
