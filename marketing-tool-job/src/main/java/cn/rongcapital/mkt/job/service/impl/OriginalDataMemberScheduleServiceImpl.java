package cn.rongcapital.mkt.job.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.rongcapital.mkt.common.enums.StatusEnum;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.dao.DataMemberDao;
import cn.rongcapital.mkt.dao.OriginalDataMemberDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.DataMember;
import cn.rongcapital.mkt.po.OriginalDataMember;
import cn.rongcapital.mkt.service.OriginalDataMemberScheduleService;

@Service
@PropertySource("classpath:${conf.dir}/application-api.properties")
public class OriginalDataMemberScheduleServiceImpl implements OriginalDataMemberScheduleService , TaskService{

    @Autowired
    private OriginalDataMemberDao originalDataMemberDao;

    @Autowired
    private DataMemberDao dataMemberDao;
    
	@Autowired
	Environment env;
	
	
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void cleanData() {
    	
        int BATCH_NUM = Integer.valueOf(env.getProperty("orginal.to.data.batch.num"));
        
        // 1. 取出需要处理的数据
        OriginalDataMember paramOriginalDataMember = new OriginalDataMember();
        paramOriginalDataMember.setStatus(StatusEnum.ACTIVE.getStatusCode());

        int totalCount = originalDataMemberDao.selectListCount(paramOriginalDataMember);
        int totalPages = (totalCount + BATCH_NUM - 1) / BATCH_NUM;
        paramOriginalDataMember.setPageSize(BATCH_NUM);
        for (int i = 0; i < totalPages; i++) {
            paramOriginalDataMember.setStartIndex(Integer.valueOf(i * BATCH_NUM));
            List<OriginalDataMember> originalDataMembers = originalDataMemberDao.selectList(paramOriginalDataMember);
            if (CollectionUtils.isEmpty(originalDataMembers)) {
                continue;
            }
            handleOriginalDataMember(originalDataMembers);
        }
    }

    // 处理OriginalDataMember的数据
    private void handleOriginalDataMember(List<OriginalDataMember> tmpOriginalDataMembers) {
        if (tmpOriginalDataMembers.isEmpty()) {
            return;
        }

        int batchCount = tmpOriginalDataMembers.size();

        List<DataMember> dataMembers = new ArrayList<>(batchCount);
        // 将OriginalDataMember的数据同步到DataMember
        for (int i = 0; i < batchCount; i++) {
            DataMember paramDataMember = new DataMember();
            OriginalDataMember tmpOriginalDataMember = tmpOriginalDataMembers.get(i);
            BeanUtils.copyProperties(tmpOriginalDataMember, paramDataMember);
            // 因为在一个事务里 , 直接修改OriginalDataMember的状态
            tmpOriginalDataMember.setStatus(StatusEnum.PROCESSED.getStatusCode());
            originalDataMemberDao.updateById(tmpOriginalDataMember);
            dataMembers.add(paramDataMember);
        }

        Map<String, List<DataMember>> paramMap = new HashMap<>();
        paramMap.put("list", dataMembers);

        dataMemberDao.cleanAndUpdateByOriginal(paramMap);
    }
    
    @Override
    public void task(Integer taskId) {
        cleanData();
    }

}
