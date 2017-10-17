package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

import java.util.Date;

public class SegmentDistribution extends BaseQuery {
    private Integer id;

    private Integer sagmentationId;

    private Integer audienceCount;

    private Byte status;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSagmentationId() {
        return sagmentationId;
    }

    public void setSagmentationId(Integer sagmentationId) {
        this.sagmentationId = sagmentationId;
    }

    public Integer getAudienceCount() {
        return audienceCount;
    }

    public void setAudienceCount(Integer audienceCount) {
        this.audienceCount = audienceCount;
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
