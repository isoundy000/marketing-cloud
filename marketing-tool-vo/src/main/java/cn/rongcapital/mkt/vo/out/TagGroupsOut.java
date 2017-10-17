package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by byf on 11/8/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TagGroupsOut {

    @NotEmpty
    private String groupId;

    @NotEmpty
    private String groupName;

    @NotNull
    private Integer groupIndex;

    private List<SystemTagOut> tagList = new ArrayList<>();

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

    @JsonProperty("tag_list")
    public List<SystemTagOut> getTagList() {
        return tagList;
    }

    public void setTagList(List<SystemTagOut> tagList) {
        this.tagList = tagList;
    }
}
