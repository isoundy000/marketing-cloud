package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface TaskGetListService {

    /**
     * mkt.task.list.get
     * 
     * @功能简述 : 获取后台任务列表
     * @author nianjun
     * @return
     */
    public BaseOutput getTaskList();
    
    /**
     * mkt.task.list.check
     * 
     * @功能简述 : 检查后台任务列表，是否有未查看的任务
     * @author lihaiguang
     * @return
     */
    public BaseOutput checkTaskList();
    
    /**
     * mkt.task.list.check.update
     * 
     * @功能简述 : 更改后台任务状态把未查看变成已查看
     * @author lihaiguang
     * @return
     */
    
    public BaseOutput updateTaskListStatus();
}
