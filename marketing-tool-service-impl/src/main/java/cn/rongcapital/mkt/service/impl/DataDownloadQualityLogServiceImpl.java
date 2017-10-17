package cn.rongcapital.mkt.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.FileNameEnum;
import cn.rongcapital.mkt.common.util.FileUtil;
import cn.rongcapital.mkt.dao.ImportDataHistoryDao;
import cn.rongcapital.mkt.dao.ImportDataModifyLogDao;
import cn.rongcapital.mkt.po.ImportDataHistory;
import cn.rongcapital.mkt.po.ImportDataModifyLog;
import cn.rongcapital.mkt.service.DataDownloadQualityLogService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class DataDownloadQualityLogServiceImpl implements DataDownloadQualityLogService {

    @Autowired
    private ImportDataHistoryDao importDataHistoryDao;

    @Autowired
    private ImportDataModifyLogDao importDataModifyLogDao;

    @Override
    public BaseOutput downloadQualityLog(Long importDataId) {

        ImportDataHistory paramImportDataHistory = new ImportDataHistory();
        paramImportDataHistory.setId(importDataId);
        paramImportDataHistory.setPageSize(0);
        List<ImportDataHistory> importDataHistories = importDataHistoryDao.selectList(paramImportDataHistory);
        List<String> historyColumnNames = importDataHistoryDao.selectColumns();

        ImportDataModifyLog paramImportDataModifyLog = new ImportDataModifyLog();
        paramImportDataModifyLog.setImportDataId(importDataId);
        paramImportDataModifyLog.setPageSize(0);

        List<ImportDataModifyLog> importDataModifyLogs = importDataModifyLogDao.selectList(paramImportDataModifyLog);
        List<String> modifyLogColumnNames = importDataModifyLogDao.selectColumns();

        File file = FileUtil.generateFileforDownload(FileUtil.transferNameListtoMap(historyColumnNames),
                        importDataHistories, FileNameEnum.IMPORT_DATA_HISTORY_LOG.getDetailName());

        file = FileUtil.generateFile(FileUtil.transferNameListtoMap(modifyLogColumnNames), importDataModifyLogs, file);

        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                        ApiConstant.INT_ZERO, null);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("download_file_name", file.getName());

        baseOutput.getData().add(resultMap);

        return baseOutput;
    }
}
