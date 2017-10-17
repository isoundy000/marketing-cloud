package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface SmsSmstempletIdGetService {
    /**
     * 短信模板id查询模板
     * 
     * 接口：mkt.sms.smstemplet.id.get
     * 
     * @param id
     * @return
     * @Date 2016-11-11
     * @author shuiyangyang
     */
    public BaseOutput getSmsSmstempletById(Integer id);
}
