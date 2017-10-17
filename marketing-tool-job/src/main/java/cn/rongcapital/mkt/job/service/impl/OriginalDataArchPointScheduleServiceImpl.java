package cn.rongcapital.mkt.job.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.rongcapital.mkt.common.enums.StatusEnum;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.dao.DataArchPointDao;
import cn.rongcapital.mkt.dao.OriginalDataArchPointDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.DataArchPoint;
import cn.rongcapital.mkt.po.OriginalDataArchPoint;
import cn.rongcapital.mkt.service.OriginalDataArchPointScheduleService;

@Service
@PropertySource("classpath:${conf.dir}/application-api.properties")
public class OriginalDataArchPointScheduleServiceImpl implements OriginalDataArchPointScheduleService, TaskService {

    @Autowired
    private OriginalDataArchPointDao originalDataArchPointDao;

    @Autowired
    private DataArchPointDao dataArchPointDao;

	@Autowired
	Environment env;
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void cleanData() {

        int BATCH_NUM = Integer.valueOf(env.getProperty("orginal.to.data.batch.num"));
        
        // 1. 取出需要处理的数据
        OriginalDataArchPoint paramOriginalDataArchPoint = new OriginalDataArchPoint();
        paramOriginalDataArchPoint.setStatus(StatusEnum.ACTIVE.getStatusCode());
        int totalCount = originalDataArchPointDao.selectListCount(paramOriginalDataArchPoint);
        int totalPages = (totalCount + BATCH_NUM - 1) / BATCH_NUM;
        paramOriginalDataArchPoint.setPageSize(BATCH_NUM);
        for (int i = 0; i < totalPages; i++) {
            // 查询没有被处理过的数据 (未删除的)
            paramOriginalDataArchPoint.setStartIndex(Integer.valueOf(i * BATCH_NUM));
            List<OriginalDataArchPoint> originalDataArchPoints =
                    originalDataArchPointDao.selectList(paramOriginalDataArchPoint);
            if (originalDataArchPoints.isEmpty()) {
                // 根本没有要处理的数据
                continue;
            }
            handleOriginalDataArchPoint(originalDataArchPoints);
        }
    }

    // 处理OriginalDataArchPoint的数据
    private void handleOriginalDataArchPoint(List<OriginalDataArchPoint> tmpOriginalDataArchPoints) {
        if (tmpOriginalDataArchPoints.isEmpty()) {
            return;
        }

        int batchCount = tmpOriginalDataArchPoints.size();

        List<DataArchPoint> dataArchPoints = new ArrayList<>(batchCount);
        // 将OriginalDataArchPoint的数据同步到DataArchPoint
        for (int i = 0; i < batchCount; i++) {
            DataArchPoint paramDataArchPoint = new DataArchPoint();
            OriginalDataArchPoint tmpOriginalDataArchPoint = tmpOriginalDataArchPoints.get(i);
            BeanUtils.copyProperties(tmpOriginalDataArchPoint, paramDataArchPoint);

            // 因为在一个事务里 , 直接修改OriginalDataArchPoint的状态
            tmpOriginalDataArchPoint.setStatus(StatusEnum.PROCESSED.getStatusCode());
            originalDataArchPointDao.updateById(tmpOriginalDataArchPoint);
            dataArchPoints.add(paramDataArchPoint);
        }

        Map<String, List<DataArchPoint>> paramMap = new HashMap<>();
        paramMap.put("list", dataArchPoints);

        dataArchPointDao.cleanAndUpdateByOriginal(paramMap);
    }

    @Override
    public void task(Integer taskId) {
        cleanData();
    }

}
