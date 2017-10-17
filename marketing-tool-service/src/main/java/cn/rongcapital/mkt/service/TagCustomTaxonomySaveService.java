package cn.rongcapital.mkt.service;

import javax.ws.rs.core.SecurityContext;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.TagCustomTaxonomySaveIn;

public interface TagCustomTaxonomySaveService {
    /**
     * 功能描述：创建自定义分类
     * 
     * 接口：mkt.tag.custom.taxonomy.save
     * 
     * @param body
     * @param securityContext
     * @return
     * @Date 2016.12.13
     * @author shuiyangyang
     */
    public BaseOutput tagCustomTaxonomySave(TagCustomTaxonomySaveIn body, SecurityContext securityContext);
}
