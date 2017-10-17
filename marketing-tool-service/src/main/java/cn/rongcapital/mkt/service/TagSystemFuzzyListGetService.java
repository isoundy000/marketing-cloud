package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface TagSystemFuzzyListGetService {
    /**
     * 根据页面输入值模糊查询标签，返回标签或者标签值 （全路径的标签或者标签值《带有标签类型：标签值，标签》分页
     * 
     * 接口：mkt.tag.system.fuzzy.list.get
     * 
     * @param tagName
     * @param index
     * @param size
     * @return
     * @author shuiyangyang
     * @date 2016-11-11
     */
    public BaseOutput getTagSystemFuzzyList(String tagName,String choiceShow, Integer index, Integer size);
}
