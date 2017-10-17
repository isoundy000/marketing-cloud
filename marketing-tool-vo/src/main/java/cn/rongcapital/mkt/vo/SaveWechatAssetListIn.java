package cn.rongcapital.mkt.vo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;

/**
 * Created by Yunfeng on 2016-6-1.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class SaveWechatAssetListIn extends BaseInput {

    private ArrayList<Long> groupIds;
    private String peopleGroupName;

    @JsonProperty("group_ids")
    public ArrayList<Long> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(ArrayList<Long> groupIds) {
        this.groupIds = groupIds;
    }

    @JsonProperty("name")
    public String getPeopleGroupName() {
        return peopleGroupName;
    }

    public void setPeopleGroupName(String peopleGroupName) {
        this.peopleGroupName = peopleGroupName;
    }
}
