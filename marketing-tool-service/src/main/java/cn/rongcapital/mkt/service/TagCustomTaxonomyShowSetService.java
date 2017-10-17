package cn.rongcapital.mkt.service;

import javax.ws.rs.core.SecurityContext;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.TagCustomTaxonomyShowSetIn;

public interface TagCustomTaxonomyShowSetService {
    /**
     * 功能描述：设置系统标签一级分类优先显示接口
     * 
     * 接口：mkt.tag.custom.taxonomy.show.set
     * 
     * @param body
     * @param securityContext
     * @return
     * @Date 2016.12.13
     * @author shuiyangyang
     */
    public BaseOutput tagCustomTaxonomyShowSet(TagCustomTaxonomyShowSetIn body, SecurityContext securityContext);
}
