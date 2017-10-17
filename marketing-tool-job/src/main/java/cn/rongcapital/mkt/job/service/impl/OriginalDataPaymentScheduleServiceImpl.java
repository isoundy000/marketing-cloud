package cn.rongcapital.mkt.job.service.impl;

import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.dao.DataPaymentDao;
import cn.rongcapital.mkt.dao.OriginalDataPaymentDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.DataPayment;
import cn.rongcapital.mkt.po.OriginalDataPayment;
import cn.rongcapital.mkt.service.OriginalDataPaymentScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bianyulong on 16/6/23.
 */
@Service
@PropertySource("classpath:${conf.dir}/application-api.properties")
public class OriginalDataPaymentScheduleServiceImpl implements OriginalDataPaymentScheduleService,TaskService {

    @Autowired
    private OriginalDataPaymentDao originalDataPaymentDao;

    @Autowired
    private DataPaymentDao dataPaymentDao;
    
	@Autowired
	Environment env;
	
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void cleanData() {

        int BATCH_NUM = Integer.valueOf(env.getProperty("orginal.to.data.batch.num"));
        
        OriginalDataPayment paramOriginalDataPayment = new OriginalDataPayment();
        paramOriginalDataPayment.setStatus(StatusEnum.ACTIVE.getStatusCode());

        int totalCount = originalDataPaymentDao.selectListCount(paramOriginalDataPayment);
        int totalPages = (totalCount + BATCH_NUM - 1) / BATCH_NUM;
        paramOriginalDataPayment.setPageSize(BATCH_NUM);
        for (int i = 0; i < totalPages; i++) {
            paramOriginalDataPayment.setStartIndex(Integer.valueOf(i * BATCH_NUM));
            List<OriginalDataPayment> originalDataPayments =
                    originalDataPaymentDao.selectList(paramOriginalDataPayment);
            if (originalDataPayments.isEmpty()) {
                continue;
            }
            handleOriginalDataLogin(originalDataPayments);
        }

    }

    // 处理OriginalDataPayment的数据
    private void handleOriginalDataLogin(List<OriginalDataPayment> tmpOriginalDataPayments) {
        if (tmpOriginalDataPayments.isEmpty()) {
            return;
        }

        int batchCount = tmpOriginalDataPayments.size();

        List<DataPayment> dataLogins = new ArrayList<>(batchCount);
        // 将OriginalDataPayment的数据同步到DataPayment
        for (int i = 0; i < batchCount; i++) {
            DataPayment paramDataPayment = new DataPayment();
            OriginalDataPayment originalDataPayment = tmpOriginalDataPayments.get(i);
            BeanUtils.copyProperties(originalDataPayment, paramDataPayment);

            originalDataPayment.setStatus(StatusEnum.PROCESSED.getStatusCode());
            originalDataPaymentDao.updateById(originalDataPayment);
            dataLogins.add(paramDataPayment);
        }

        dataPaymentDao.cleanAndUpdateByOriginal(dataLogins);
    }

    @Override
    public void task(Integer taskId) {
        cleanData();
    }
}
