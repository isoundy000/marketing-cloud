package cn.rongcapital.mkt.vo.in;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import cn.rongcapital.mkt.vo.BaseInput;

/**
 * 接口:mkt.tag.system.flag.set
 * 输入参数
 * @param body
 * @param securityContext
 * @return
 * @author shuiyangyang
 * @Date 2016-11-08
 */
public class TagSystemFlagSetIn extends BaseInput{
    @NotEmpty
    private String tagId;
    
    @NotNull
    private Boolean flag;

    @JsonProperty("tag_id")
    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    @JsonProperty("flag")
    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
    
    
}
