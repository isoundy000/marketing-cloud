package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;
import java.util.Date;

public class SmsTaskTargetAudienceCache extends BaseQuery {
    private Long id;

    private Long dataPartyId;

    private String mobile;

    private Long targetId;

    private Byte status;

    private Date createTime;

    private Date updateTime;

    private Integer targetType;

    private Long taskHeadId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDataPartyId() {
        return dataPartyId;
    }

    public void setDataPartyId(Long dataPartyId) {
        this.dataPartyId = dataPartyId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
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

    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }

    public Long getTaskHeadId() {
        return taskHeadId;
    }

    public void setTaskHeadId(Long taskHeadId) {
        this.taskHeadId = taskHeadId;
    }
}