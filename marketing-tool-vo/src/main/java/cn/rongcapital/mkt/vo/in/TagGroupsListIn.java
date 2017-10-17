package cn.rongcapital.mkt.vo.in;

import cn.rongcapital.mkt.vo.BaseInput;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * Created by byf on 11/14/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TagGroupsListIn extends BaseInput{

    @NotEmpty
    private List<TagGroupsIn> tagGroupsInList;

    @JsonProperty("filter_groups")
    public List<TagGroupsIn> getTagGroupsInList() {
        return tagGroupsInList;
    }

    public void setTagGroupsInList(List<TagGroupsIn> tagGroupsInList) {
        this.tagGroupsInList = tagGroupsInList;
    }
}
