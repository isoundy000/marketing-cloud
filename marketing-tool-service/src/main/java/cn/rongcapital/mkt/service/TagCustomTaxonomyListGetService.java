package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface TagCustomTaxonomyListGetService {
    /**
     * 功能描述：获得自定义分类子分类
     * 
     * 接口：mkt.tag.custom.taxonomy.list.get
     * 
     * @param tagTreeId
     * @return
     * @Date 2016.12.13
     * @author shuiyangyang
     */
    public BaseOutput tagCustomTaxonomyListGet(String tagTreeId,Integer pageSourceType);
}
