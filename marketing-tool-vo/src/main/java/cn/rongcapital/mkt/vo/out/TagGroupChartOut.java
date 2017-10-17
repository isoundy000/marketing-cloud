package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by byf on 11/14/16.
 */
public class TagGroupChartOut {

    private String groupId;
    private String groupName;
    private Integer groupIndex;
    private Integer groupChange;
    private List<TagChartDatas> chartData = new ArrayList<TagChartDatas>();

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

    @JsonProperty("chart_data")
    public List<TagChartDatas> getChartData() {
        return chartData;
    }

    public void setChartData(List<TagChartDatas> chartData) {
        this.chartData = chartData;
    }
}
