package cn.rongcapital.mkt.vo.in;


import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import cn.rongcapital.mkt.vo.BaseInput;

public class TagCustomTaxonomyDelIn extends BaseInput {
    @NotEmpty
    private String tagTreeId;

    @JsonProperty("tag_tree_id")
    public String getTagTreeId() {
        return tagTreeId;
    }

    public void setTagTreeId(String tagTreeId) {
        this.tagTreeId = tagTreeId;
    }


}
