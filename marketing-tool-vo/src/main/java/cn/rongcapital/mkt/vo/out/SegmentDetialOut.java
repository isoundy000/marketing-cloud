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
public class SegmentDetialOut {

    @NotNull
    private Long segmentHeadId;

    @NotEmpty
    private String segmentName;

    @NotNull
    private Integer publishStatus;

    private List<TagGroupsOut> filterGroups = new ArrayList<>();

    @JsonProperty("segment_head_id")
    public Long getSegmentHeadId() {
        return segmentHeadId;
    }

    public void setSegmentHeadId(Long segmentHeadId) {
        this.segmentHeadId = segmentHeadId;
    }

    @JsonProperty("segment_name")
    public String getSegmentName() {
        return segmentName;
    }

    public void setSegmentName(String segmentName) {
        this.segmentName = segmentName;
    }

    @JsonProperty("publish_status")
    public Integer getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(Integer publishStatus) {
        this.publishStatus = publishStatus;
    }

    @JsonProperty("filter_groups")
    public List<TagGroupsOut> getFilterGroups() {
        return filterGroups;
    }

    public void setFilterGroups(List<TagGroupsOut> filterGroups) {
        this.filterGroups = filterGroups;
    }
}
