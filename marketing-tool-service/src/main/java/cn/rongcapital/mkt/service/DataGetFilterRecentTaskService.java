package cn.rongcapital.mkt.service;

import java.util.List;

import cn.rongcapital.mkt.po.TaskRunLog;

public interface DataGetFilterRecentTaskService {

    /**
     * mkt.data.filter.recenttask.get
     * 
     * @功能简述 : 查询最近完成的数据接入任务
     * @author nianjun
     * @param method
     * @param userToken
     * @param index
     * @param size
     * @param ver
     * @param condition
     * @return
     */
    
    public List<TaskRunLog> getFilterRecentTask(String method, String userToken, String ver , String condition);

}
