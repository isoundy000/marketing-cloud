package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

import java.util.Date;

public class TagRecommend extends BaseQuery {
    private Integer id;

    private Integer tagGroupId;

    private String tagGroupName;

    private Byte status;

    private Date createTime;

    private Date updateTime;

    private String tagDesc;

    private Boolean flag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTagGroupId() {
        return tagGroupId;
    }

    public void setTagGroupId(Integer tagGroupId) {
        this.tagGroupId = tagGroupId;
    }

    public String getTagGroupName() {
        return tagGroupName;
    }

    public void setTagGroupName(String tagGroupName) {
        this.tagGroupName = tagGroupName == null ? null : tagGroupName.trim();
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

    public String getTagDesc() {
        return tagDesc;
    }

    public void setTagDesc(String tagDesc) {
        this.tagDesc = tagDesc == null ? null : tagDesc.trim();
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
}