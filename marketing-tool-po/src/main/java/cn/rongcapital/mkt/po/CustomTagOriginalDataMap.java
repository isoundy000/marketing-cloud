package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class CustomTagOriginalDataMap extends BaseQuery {
    private Integer id;

    private Integer tagId;

    private Integer originalDataType;

    private Integer originalDataId;

    private Byte status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public Integer getOriginalDataType() {
        return originalDataType;
    }

    public void setOriginalDataType(Integer originalDataType) {
        this.originalDataType = originalDataType;
    }

    public Integer getOriginalDataId() {
        return originalDataId;
    }

    public void setOriginalDataId(Integer originalDataId) {
        this.originalDataId = originalDataId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}
