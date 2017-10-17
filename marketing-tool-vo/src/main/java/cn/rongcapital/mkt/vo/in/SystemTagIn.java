package cn.rongcapital.mkt.vo.in;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by byf on 11/4/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SystemTagIn {

    @NotEmpty
    private String tagId;

    @NotNull
    private Integer tagIndex;

    @NotEmpty
    private String tagName;

    @NotNull
    private Integer tagExclude;

    @NotNull
    private List<SystemValueIn> tagValueList = new ArrayList<>();

    @JsonProperty("tag_id")
    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    @JsonProperty("tag_index")
    public Integer getTagIndex() {
        return tagIndex;
    }

    public void setTagIndex(Integer tagIndex) {
        this.tagIndex = tagIndex;
    }

    @JsonProperty("tag_name")
    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @JsonProperty("tag_exclude")
    public Integer getTagExclude() {
        return tagExclude;
    }

    public void setTagExclude(Integer tagExclude) {
        this.tagExclude = tagExclude;
    }

    @JsonProperty("tag_value_list")
    public List<SystemValueIn> getTagValueList() {
        return tagValueList;
    }

    public void setTagValueList(List<SystemValueIn> tagValueList) {
        this.tagValueList = tagValueList;
    }
}
