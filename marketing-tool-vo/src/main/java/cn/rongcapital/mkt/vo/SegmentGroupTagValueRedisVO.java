package cn.rongcapital.mkt.vo;

import org.hibernate.validator.constraints.NotEmpty;

public class SegmentGroupTagValueRedisVO {
    @NotEmpty
    private String tagValueId;

    @NotEmpty
    private String tagValue;
    
    private Long tagValueCoverCount;
    
    private String tagValueCoverIds;
    
    private String tagId;
    
    private String groupId;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTagValueId() {
        return tagValueId;
    }

    public void setTagValueId(String tagValueId) {
        this.tagValueId = tagValueId;
    }

    public String getTagValue() {
        return tagValue;
    }

    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }

    public Long getTagValueCoverCount() {
        return tagValueCoverCount;
    }

    public void setTagValueCoverCount(Long tagValueCoverCount) {
        this.tagValueCoverCount = tagValueCoverCount;
    }

    public String getTagValueCoverIds() {
        return tagValueCoverIds;
    }

    public void setTagValueCoverIds(String tagValueCoverIds) {
        this.tagValueCoverIds = tagValueCoverIds;
    }
    
    
}
