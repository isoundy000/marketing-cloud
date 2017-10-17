package cn.rongcapital.mkt.event.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

import java.util.Date;

public class EventObject extends BaseQuery {
    private Long id;

    private String code;

    private String name;

    private String instanceNameProp;

    private String instanceNameLabel;

    private Byte status;

    private Date createTime;

    private Date updateTime;

    private Boolean systemObject;

    private String attributes;

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

    public String getInstanceNameProp() {
        return instanceNameProp;
    }

    public void setInstanceNameProp(String instanceNameProp) {
        this.instanceNameProp = instanceNameProp == null ? null : instanceNameProp.trim();
    }

    public String getInstanceNameLabel() {
        return instanceNameLabel;
    }

    public void setInstanceNameLabel(String instanceNameLabel) {
        this.instanceNameLabel = instanceNameLabel == null ? null : instanceNameLabel.trim();
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

    public Boolean getSystemObject() {
        return systemObject;
    }

    public void setSystemObject(Boolean systemObject) {
        this.systemObject = systemObject;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes == null ? null : attributes.trim();
    }
}