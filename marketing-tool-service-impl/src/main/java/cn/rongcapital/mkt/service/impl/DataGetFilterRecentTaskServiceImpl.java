package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.enums.TaskConditionEnum;
import cn.rongcapital.mkt.dao.TaskRunLogDao;
import cn.rongcapital.mkt.po.TaskRunLog;
import cn.rongcapital.mkt.service.DataGetFilterRecentTaskService;

@Service
public class DataGetFilterRecentTaskServiceImpl implements DataGetFilterRecentTaskService {

    @Autowired
    private TaskRunLogDao taskRunLogDao;

    @Override
    public List<TaskRunLog> getFilterRecentTask(String method, String userToken, String ver,
                    String condition) {
        
        List<TaskRunLog> taskRunLogList = new ArrayList<>();

        if (StringUtils.isEmpty(condition)) {
            return taskRunLogList;
        }

        TaskRunLog paramTaskRunLog = new TaskRunLog();
        paramTaskRunLog.setEndTime(TaskConditionEnum.getEnumByAbbreviation(condition).getTime());
        paramTaskRunLog.setOrderField("start_time");
        paramTaskRunLog.setOrderFieldType("DESC");
        taskRunLogList = taskRunLogDao.selectByEndtime(paramTaskRunLog);

        return taskRunLogList;
    }

}
