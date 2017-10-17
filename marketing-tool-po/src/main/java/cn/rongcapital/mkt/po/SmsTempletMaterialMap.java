package cn.rongcapital.mkt.po;

import java.util.Date;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class SmsTempletMaterialMap extends BaseQuery{
    private Long id;

    private Long smsTempletId;

    private String smsVariableValue;

    private Integer materialId;

    private String materialType;

    private String materialName;

    private Integer materialPropertyId;

    private String materialPropertyCode;

    private String materialPropertyName;

    private Byte status;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSmsTempletId() {
        return smsTempletId;
    }

    public void setSmsTempletId(Long smsTempletId) {
        this.smsTempletId = smsTempletId;
    }

    public String getSmsVariableValue() {
        return smsVariableValue;
    }

    public void setSmsVariableValue(String smsVariableValue) {
        this.smsVariableValue = smsVariableValue == null ? null : smsVariableValue.trim();
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType == null ? null : materialType.trim();
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName == null ? null : materialName.trim();
    }

    public Integer getMaterialPropertyId() {
        return materialPropertyId;
    }

    public void setMaterialPropertyId(Integer materialPropertyId) {
        this.materialPropertyId = materialPropertyId;
    }

    public String getMaterialPropertyCode() {
        return materialPropertyCode;
    }

    public void setMaterialPropertyCode(String materialPropertyCode) {
        this.materialPropertyCode = materialPropertyCode == null ? null : materialPropertyCode.trim();
    }

    public String getMaterialPropertyName() {
        return materialPropertyName;
    }

    public void setMaterialPropertyName(String materialPropertyName) {
        this.materialPropertyName = materialPropertyName == null ? null : materialPropertyName.trim();
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