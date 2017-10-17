package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface TagSystemValueListGetService {
    /**
     * 获取标签值
     * 
     * 接口：mkt.tag.system.value.list.get
     * @param tagId
     * @return
     * @author shuiyangyang
     * @Date 2016-11-14
     */
    public BaseOutput getTagSystemValueList(String tagId);
}
