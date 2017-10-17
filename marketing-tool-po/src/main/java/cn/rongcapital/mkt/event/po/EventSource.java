package cn.rongcapital.mkt.event.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

import java.util.Date;

public class EventSource extends BaseQuery {
    private Long id;

    private String code;

    private String name;

    private Long platformId;

    private Byte status;

    private Date createTime;

    private Date updateTime;

    private Boolean systemSource;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Long platformId) {
        this.platformId = platformId;
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

    public Boolean getSystemSource() {
        return systemSource;
    }

    public void setSystemSource(Boolean systemSource) {
        this.systemSource = systemSource;
    }
}