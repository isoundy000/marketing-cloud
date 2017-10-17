package cn.rongcapital.mkt.service;

import javax.ws.rs.core.SecurityContext;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.TagCustomTaxonomyDelIn;

public interface TagCustomTaxonomyDelService {

    /**
     * 功能描述：删除自定义分类（逻辑删除）
     * 
     * 接口： mkt.tag.custom.taxonomy.del
     * 
     * @param body
     * @param securityContext
     * @return
     * @Date 2016.12.13
     * @author shuiyangyang
     */
    public BaseOutput tagCustomTaxonomyDel(TagCustomTaxonomyDelIn body, SecurityContext securityContext);
}
