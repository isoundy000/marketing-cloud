package cn.rongcapital.mkt.service;

import javax.ws.rs.core.SecurityContext;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.TagCustomTaxonomyRootSaveIn;

public interface TagCustomTaxonomyRootSaveService {
    /**
     * 功能描述：创建自定义分类跟分类
     * 
     * 接口：mkt.tag.custom.taxonomy.root.save
     * 
     * @param body
     * @param securityContext
     * @return
     * @Date 2016.12.21
     * @author shuiyangyang
     */
    public BaseOutput tagCustomTaxonomyRootSave(TagCustomTaxonomyRootSaveIn body, SecurityContext securityContext);
}
