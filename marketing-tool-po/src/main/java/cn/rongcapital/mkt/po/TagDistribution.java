package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

import java.util.Date;

public class TagDistribution extends BaseQuery {
    private Long id;

    private Long tagId;

    private String coveragePercent;

    private String inuseTagPercent;

    private String audienceCount;

    private Byte status;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getCoveragePercent() {
        return coveragePercent;
    }

    public void setCoveragePercent(String coveragePercent) {
        this.coveragePercent = coveragePercent == null ? null : coveragePercent.trim();
    }

    public String getInuseTagPercent() {
        return inuseTagPercent;
    }

    public void setInuseTagPercent(String inuseTagPercent) {
        this.inuseTagPercent = inuseTagPercent == null ? null : inuseTagPercent.trim();
    }

    public String getAudienceCount() {
        return audienceCount;
    }

    public void setAudienceCount(String audienceCount) {
        this.audienceCount = audienceCount == null ? null : audienceCount.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
