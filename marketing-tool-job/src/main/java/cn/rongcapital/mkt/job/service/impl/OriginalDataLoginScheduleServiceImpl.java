package cn.rongcapital.mkt.job.service.impl;

import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.dao.DataLoginDao;
import cn.rongcapital.mkt.dao.OriginalDataLoginDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.DataLogin;
import cn.rongcapital.mkt.po.OriginalDataLogin;
import cn.rongcapital.mkt.service.OriginalDataLoginScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bianyulong on 16/6/22.
 */
@Service
@PropertySource("classpath:${conf.dir}/application-api.properties")
public class OriginalDataLoginScheduleServiceImpl implements OriginalDataLoginScheduleService,TaskService {

    @Autowired
    private OriginalDataLoginDao originalDataLoginDao;

    @Autowired
    private DataLoginDao dataLoginDao;
    
	@Autowired
	Environment env;
	
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void cleanData() {

        int BATCH_NUM = Integer.valueOf(env.getProperty("orginal.to.data.batch.num"));
        
        OriginalDataLogin paramOriginalDataLogin = new OriginalDataLogin();
        paramOriginalDataLogin.setStatus(StatusEnum.ACTIVE.getStatusCode());

        int totalCount = originalDataLoginDao.selectListCount(paramOriginalDataLogin);
        int totalPages = (totalCount + BATCH_NUM - 1) / BATCH_NUM;
        paramOriginalDataLogin.setPageSize(BATCH_NUM);
        for (int i = 0; i < totalPages; i++) {
            paramOriginalDataLogin.setStartIndex(Integer.valueOf(i * BATCH_NUM));
            List<OriginalDataLogin> originalDataLogins =
                    originalDataLoginDao.selectList(paramOriginalDataLogin);
            if (originalDataLogins.isEmpty()) {
                continue;
            }
            handleOriginalDataLogin(originalDataLogins);
        }

    }

    // 处理OriginalDataLogin的数据
    private void handleOriginalDataLogin(List<OriginalDataLogin> tmpOriginalDataLogins) {
        if (tmpOriginalDataLogins.isEmpty()) {
            return;
        }

        int batchCount = tmpOriginalDataLogins.size();

        List<DataLogin> dataLogins = new ArrayList<>(batchCount);
        // 将OriginalDataLogin的数据同步到DataLogin
        for (int i = 0; i < batchCount; i++) {
            DataLogin paramDataLogin = new DataLogin();
            OriginalDataLogin tmpOriginalDataLogin = tmpOriginalDataLogins.get(i);
            BeanUtils.copyProperties(tmpOriginalDataLogin, paramDataLogin);
            // 因为在一个事务里 , 直接修改OriginalDataLogin的状态
            tmpOriginalDataLogin.setStatus(StatusEnum.PROCESSED.getStatusCode());
            originalDataLoginDao.updateById(tmpOriginalDataLogin);
            dataLogins.add(paramDataLogin);
        }

        dataLoginDao.cleanAndUpdateByOriginal(dataLogins);
    }

    @Override
    public void task(Integer taskId) {
        cleanData();
    }
}
