package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.FileNameEnum;
import cn.rongcapital.mkt.common.enums.IllegalDataHeadTypeEnum;
import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.common.util.FileUtil;
import cn.rongcapital.mkt.dao.IllegalDataDao;
import cn.rongcapital.mkt.po.IllegalData;
import cn.rongcapital.mkt.service.DataDownloadQualityIllegalDataService;
import cn.rongcapital.mkt.vo.BaseOutput;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@SuppressWarnings("unchecked")

public class DataDownloadQualityIllegalDataServiceImpl implements DataDownloadQualityIllegalDataService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IllegalDataDao illegalDataDao;

    @Override
    public BaseOutput downloadIllegalData(Long batchId, String type) {
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                        ApiConstant.INT_ZERO, null);

        IllegalData paramIllegalData = new IllegalData();
        paramIllegalData.setBatchId(batchId);
        paramIllegalData.setType(type);
        paramIllegalData.setStatus(StatusEnum.ACTIVE.getStatusCode());
        paramIllegalData.setStartIndex(null);
        paramIllegalData.setPageSize(null);
        List<IllegalData> illegalDatas = illegalDataDao.selectList(paramIllegalData);
        if (CollectionUtils.isEmpty(illegalDatas)) {
            return result;
        }

        String header = "";
        List<String> illegalRowData = new ArrayList<>(illegalDatas.size());
        for (IllegalData illegalData : illegalDatas) {
            if (IllegalDataHeadTypeEnum.HEADER.getCode().equals(illegalData.getHeadType())) {
                header = illegalData.getOriginData();
            } else {
                illegalRowData.add(illegalData.getOriginData());
            }
        }

        Path path = FileUtil.generateFileforDownload(header, illegalRowData, FileNameEnum.ILLEGAL_DATA.getDetailName());
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("download_file_name", path.getFileName().toString());
        result.getData().add(resultMap);

        return result;
    }

}
