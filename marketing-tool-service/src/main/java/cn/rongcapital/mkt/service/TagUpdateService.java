package cn.rongcapital.mkt.service;

import javax.ws.rs.core.SecurityContext;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.TagBodyUpdateIn;

public interface TagUpdateService {

    /**
     * mkt.task.list.get
     * 
     * @功能简述 : 获取后台任务列表
     * @author nianjun
     * @return
     */
    public BaseOutput tagInfoUpdate(TagBodyUpdateIn body);
}
