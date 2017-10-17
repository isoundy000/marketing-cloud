package cn.rongcapital.mkt.vo;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import cn.rongcapital.mkt.vo.in.SystemValueIn;

public class SegmentGroupTagRedisVO {
    @NotEmpty
    private String tagId;

    @NotNull
    private Integer tagIndex;

    @NotEmpty
    private String tagName;

    @NotNull
    private boolean tagExclude;
    
    private Long  calcTagCoverCount;
    
    private String calcTagCoverIds;
    
    private Long tagCoverCount;
    
    private Long funnelCount;
    
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    private String tagCoverIds;
    
    private String groupId;

    @NotNull
    private List<SegmentGroupTagValueRedisVO> tagValueList;

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public Integer getTagIndex() {
        return tagIndex;
    }

    public void setTagIndex(Integer tagIndex) {
        this.tagIndex = tagIndex;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }


    public boolean isTagExclude() {
        return tagExclude;
    }

    public void setTagExclude(boolean tagExclude) {
        this.tagExclude = tagExclude;
    }

    public Long getCalcTagCoverCount() {
        return calcTagCoverCount;
    }

    public void setCalcTagCoverCount(Long calcTagCoverCount) {
        this.calcTagCoverCount = calcTagCoverCount;
    }

    public String getCalcTagCoverIds() {
        return calcTagCoverIds;
    }

    public void setCalcTagCoverIds(String calcTagCoverIds) {
        this.calcTagCoverIds = calcTagCoverIds;
    }

    public Long getTagCoverCount() {
        return tagCoverCount;
    }

    public void setTagCoverCount(Long tagCoverCount) {
        this.tagCoverCount = tagCoverCount;
    }

    public String getTagCoverIds() {
        return tagCoverIds;
    }

    public void setTagCoverIds(String tagCoverIds) {
        this.tagCoverIds = tagCoverIds;
    }

    public List<SegmentGroupTagValueRedisVO> getTagValueList() {
        return tagValueList;
    }

    public void setTagValueList(List<SegmentGroupTagValueRedisVO> tagValueList) {
        this.tagValueList = tagValueList;
    }

    public Long getFunnelCount() {
        return funnelCount;
    }

    public void setFunnelCount(Long funnelCount) {
        this.funnelCount = funnelCount;
    }
    
    
}
