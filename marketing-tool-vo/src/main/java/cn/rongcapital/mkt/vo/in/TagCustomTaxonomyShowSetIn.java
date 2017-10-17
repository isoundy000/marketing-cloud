package cn.rongcapital.mkt.vo.in;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonProperty;

import cn.rongcapital.mkt.vo.BaseInput;

public class TagCustomTaxonomyShowSetIn extends BaseInput {

    List<String> tagTreeId;

    @JsonProperty("tag_tree_id")
    public List<String> getTagTreeId() {
        return tagTreeId;
    }

    public void setTagTreeId(List<String> tagTreeId) {
        this.tagTreeId = tagTreeId;
    }

}
