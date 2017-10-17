package cn.rongcapital.mkt.po;

import java.util.Date;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class ImportTemplate extends BaseQuery implements Comparable<ImportTemplate> {
    private Integer id;

    private Integer templType;

    private String templName;

    private String fieldName;

    private String fieldCode;

    private Boolean selected;

    private Byte status;

    private Date createTime;

    private Date updateTime;

    public ImportTemplate() {}

    public ImportTemplate(Integer startIndex, Integer pageSize) {
        super(startIndex, pageSize);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTemplType() {
        return templType;
    }

    public void setTemplType(Integer templType) {
        this.templType = templType;
    }

    public String getTemplName() {
        return templName;
    }

    public void setTemplName(String templName) {
        this.templName = templName == null ? null : templName.trim();
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

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
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

    @Override
    public int compareTo(ImportTemplate o) {
        if (this.templType > o.templType) {
            return 1;
        } else if (this.templType < o.templType) {
            return -1;
        }
        return 0;
    }
}
