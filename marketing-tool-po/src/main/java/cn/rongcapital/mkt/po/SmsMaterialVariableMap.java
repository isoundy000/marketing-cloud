package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;
import java.util.Date;

public class SmsMaterialVariableMap extends BaseQuery {
    private Long id;

    private Long smsMaterialId;

    private String smsVariableName;

    private String smsVariableValue;

    private Integer smsVariableType;

    private Byte status;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSmsMaterialId() {
        return smsMaterialId;
    }

    public void setSmsMaterialId(Long smsMaterialId) {
        this.smsMaterialId = smsMaterialId;
    }

    public String getSmsVariableName() {
        return smsVariableName;
    }

    public void setSmsVariableName(String smsVariableName) {
        this.smsVariableName = smsVariableName == null ? null : smsVariableName.trim();
    }

    public String getSmsVariableValue() {
        return smsVariableValue;
    }

    public void setSmsVariableValue(String smsVariableValue) {
        this.smsVariableValue = smsVariableValue == null ? null : smsVariableValue.trim();
    }

    public Integer getSmsVariableType() {
        return smsVariableType;
    }

    public void setSmsVariableType(Integer smsVariableType) {
        this.smsVariableType = smsVariableType;
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