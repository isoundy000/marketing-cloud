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
public class TagGroupsIn {

    @NotEmpty
    private String groupId;

    @NotEmpty
    private String groupName;

    @NotNull
    private Integer groupIndex;

    private Integer groupChange = 0;

    private List<SystemTagIn> tagList = new ArrayList<>();

    @JsonProperty("group_id")
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @JsonProperty("group_name")
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @JsonProperty("group_index")
    public Integer getGroupIndex() {
        return groupIndex;
    }

    public void setGroupIndex(Integer groupIndex) {
        this.groupIndex = groupIndex;
    }

    @JsonProperty("group_change")
    public Integer getGroupChange() {
        return groupChange;
    }

    public void setGroupChange(Integer groupChange) {
        this.groupChange = groupChange;
    }

    @JsonProperty("tag_list")
    public List<SystemTagIn> getTagList() {
        return tagList;
    }

    public void setTagList(List<SystemTagIn> tagList) {
        this.tagList = tagList;
    }
}
