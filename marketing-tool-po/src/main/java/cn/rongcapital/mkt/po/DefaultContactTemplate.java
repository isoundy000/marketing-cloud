package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class DefaultContactTemplate extends BaseQuery {
    private Integer id;

    private String fieldName;

    private String fieldCode;

    private Byte isSelected;

    private Byte isRequired;

    private Byte isChecked;

    private Integer defaultShownSeq;

    private Integer fixedShownSeqInRight;

    private Byte status;

    private Byte isPrimaryKey;

    private Integer fieldType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName == null ? null : fieldName.trim();
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode == null ? null : fieldCode.trim();
    }

    public Byte getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Byte isSelected) {
        this.isSelected = isSelected;
    }

    public Byte getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(Byte isRequired) {
        this.isRequired = isRequired;
    }

    public Byte getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Byte isChecked) {
        this.isChecked = isChecked;
    }

    public Integer getDefaultShownSeq() {
        return defaultShownSeq;
    }

    public void setDefaultShownSeq(Integer defaultShownSeq) {
        this.defaultShownSeq = defaultShownSeq;
    }

    public Integer getFixedShownSeqInRight() {
        return fixedShownSeqInRight;
    }

    public void setFixedShownSeqInRight(Integer fixedShownSeqInRight) {
        this.fixedShownSeqInRight = fixedShownSeqInRight;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getIsPrimaryKey() {
        return isPrimaryKey;
    }

    public void setIsPrimaryKey(Byte isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
    }

    public Integer getFieldType() {
        return fieldType;
    }

    public void setFieldType(Integer fieldType) {
        this.fieldType = fieldType;
    }
}
