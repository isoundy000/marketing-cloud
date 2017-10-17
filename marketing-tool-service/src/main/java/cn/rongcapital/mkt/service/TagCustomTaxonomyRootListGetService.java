package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface TagCustomTaxonomyRootListGetService {

    /**
     * 功能描述：获取自定义标签分类全部一级分类
     * 
     * 接口：mkt.tag.custom.taxonomy.root.list.get
     * 
     * @param nolyShow
     * @return
     * @Date 2016.12.13
     * @author shuiyangyang
     */
    public BaseOutput tagCustomTaxonomyRootListGet(Boolean nolyShow);
}
