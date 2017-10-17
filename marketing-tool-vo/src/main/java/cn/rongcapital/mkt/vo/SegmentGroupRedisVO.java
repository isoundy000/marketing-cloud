package cn.rongcapital.mkt.vo;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import cn.rongcapital.mkt.vo.in.SystemTagIn;

public class SegmentGroupRedisVO {
    @NotEmpty
    private String groupId;

    @NotEmpty
    private String groupName;

    @NotNull
    private Integer groupIndex;
    
    private Long groupCoverCount;
    
    private String groupCoverIds;    
    
    private Integer groupChange;

    private List<SegmentGroupTagRedisVO> tagList;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getGroupIndex() {
        return groupIndex;
    }

    public void setGroupIndex(Integer groupIndex) {
        this.groupIndex = groupIndex;
    }

    public Long getGroupCoverCount() {
        return groupCoverCount;
    }

    public void setGroupCoverCount(Long groupCoverCount) {
        this.groupCoverCount = groupCoverCount;
    }

    public String getGroupCoverIds() {
        return groupCoverIds;
    }

    public void setGroupCoverIds(String groupCoverIds) {
        this.groupCoverIds = groupCoverIds;
    }

    public List<SegmentGroupTagRedisVO> getTagList() {
        return tagList;
    }

    public void setTagList(List<SegmentGroupTagRedisVO> tagList) {
        this.tagList = tagList;
    }

    public Integer getGroupChange() {
        return groupChange;
    }

    public void setGroupChange(Integer groupChange) {
        this.groupChange = groupChange;
    }
    
    

}
