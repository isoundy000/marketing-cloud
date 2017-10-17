package cn.rongcapital.mkt.service;


import cn.rongcapital.mkt.vo.BaseOutput;

public interface SmstempletCountGetService {
    /**
     * 计算模板数量
     * 
     * 接口：mkt.sms.smstemplet.count.get
     * @return
     * @author lijinkai
     * @Date 2016-12-07
     */
    public BaseOutput getSmsTempletCount(String channelType);
}
