package cn.rongcapital.mkt.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.TaskTypeEnum;
import cn.rongcapital.mkt.dao.TaskRunLogDao;
import cn.rongcapital.mkt.po.TaskRunLog;
import cn.rongcapital.mkt.service.TaskGetListService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class TaskGetListServiceImpl implements TaskGetListService {
    
    private static final Integer NOT_CHECKED = 0;

    @Autowired
    private TaskRunLogDao taskRunLogDao;

    @Override
    public BaseOutput getTaskList() {
        TaskRunLog paramTaskRunLog = new TaskRunLog();
        paramTaskRunLog.setOrderField("start_time");
        paramTaskRunLog.setOrderFieldType("DESC");
        paramTaskRunLog.setTaskType((byte)TaskTypeEnum.DISPLAY.getCode());
        List<TaskRunLog> taskRunLogs = taskRunLogDao.selectList(paramTaskRunLog);
        List<Map<String, Object>> resultList = new ArrayList<>();
        int totalCount = 0;

        if (!CollectionUtils.isEmpty(taskRunLogs)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (TaskRunLog taskRunLog : taskRunLogs) {
                Map<String, Object> map = new HashMap<>();
                map.put("task_name", taskRunLog.getTaskName());
                map.put("createtime", simpleDateFormat.format(taskRunLog.getCreateTime()));
                map.put("task_status", taskRunLog.getEndTime() == null ? "进行中" : "已完成");
                resultList.add(map);
            }

        }

        totalCount = taskRunLogDao.selectListCount(null);
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                        ApiConstant.INT_ZERO, null);
        result.getData().addAll(resultList);
        result.setTotalCount(totalCount);
        result.setTotal(resultList.size());

        return result;
    }

    @Override
    public BaseOutput checkTaskList() {
      
        TaskRunLog paramTaskRunLog = new TaskRunLog();
        paramTaskRunLog.setOrderField("start_time");
        paramTaskRunLog.setOrderFieldType("DESC");
        paramTaskRunLog.setTaskType((byte)TaskTypeEnum.DISPLAY.getCode());
        paramTaskRunLog.setIsChecked(NOT_CHECKED);
        List<TaskRunLog> taskRunLogs = taskRunLogDao.selectList(paramTaskRunLog);
        
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                        ApiConstant.INT_ZERO, null);
        result.setTotalCount(1);
        result.setTotal(1);
        Map<String, Object> map = new HashMap<>();
        if (!CollectionUtils.isEmpty(taskRunLogs)) {
            map.put("is_checked", Boolean.TRUE);
            result.getData().add(map);
            return result;
        }
        map.put("is_checked", Boolean.FALSE);
        result.getData().add(map);
        return result;
    }

    @Override
    public BaseOutput updateTaskListStatus() {
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                        ApiConstant.INT_ZERO, null);
        
        //更改任务查看状态
        taskRunLogDao.updateTaskStatus();
        
        return result;
    }

}
