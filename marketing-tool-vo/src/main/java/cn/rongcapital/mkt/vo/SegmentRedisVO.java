package cn.rongcapital.mkt.vo;

import java.util.List;

public class SegmentRedisVO {
    private Long segmentHeadId;
    private String segmentName;
    private Long segmentCoverCount;
    private String updateTime;
    private String segmentCoverIds;    
    private List<SegmentGroupRedisVO> segmentGroups;
    
    public Long getSegmentHeadId() {
        return segmentHeadId;
    }
    public void setSegmentHeadId(Long segmentHeadId) {
        this.segmentHeadId = segmentHeadId;
    }
    public String getSegmentName() {
        return segmentName;
    }
    public void setSegmentName(String segmentName) {
        this.segmentName = segmentName;
    }
    public Long getSegmentCoverCount() {
        return segmentCoverCount;
    }
    public void setSegmentCoverCount(Long segmentCoverCount) {
        this.segmentCoverCount = segmentCoverCount;
    }
    public String getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
    public String getSegmentCoverIds() {
        return segmentCoverIds;
    }
    public void setSegmentCoverIds(String segmentCoverIds) {
        this.segmentCoverIds = segmentCoverIds;
    }
    public List<SegmentGroupRedisVO> getSegmentGroups() {
        return segmentGroups;
    }
    public void setSegmentGroups(List<SegmentGroupRedisVO> segmentGroups) {
        this.segmentGroups = segmentGroups;
    }    
    
}
