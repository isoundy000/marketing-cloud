package cn.rongcapital.mkt.service;

import javax.ws.rs.core.SecurityContext;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.TagSystemFlagSetIn;

public interface TagSystemFlagSetService {
    /**
     * 接口:mkt.tag.system.flag.set 
     * 推荐标签（设置）
     * 
     * @param body
     * @param securityContext
     * @return
     * @author shuiyangyang
     * @Date 2016-11-08
     */
    public BaseOutput updateFlag(TagSystemFlagSetIn body, SecurityContext securityContext);
}
