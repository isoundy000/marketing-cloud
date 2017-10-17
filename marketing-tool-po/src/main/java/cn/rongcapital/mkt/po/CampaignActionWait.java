package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

import java.util.Date;

public class CampaignActionWait extends BaseQuery {
    private Integer id;

    private Integer campaignHeadId;

    private String itemId;

    private String name;

    private Byte type;

    private Integer relativeValue;

    private Byte relativeType;

    private Date specificTime;

    private Byte status;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCampaignHeadId() {
        return campaignHeadId;
    }

    public void setCampaignHeadId(Integer campaignHeadId) {
        this.campaignHeadId = campaignHeadId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId == null ? null : itemId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getRelativeValue() {
        return relativeValue;
    }

    public void setRelativeValue(Integer relativeValue) {
        this.relativeValue = relativeValue;
    }

    public Byte getRelativeType() {
        return relativeType;
    }

    public void setRelativeType(Byte relativeType) {
        this.relativeType = relativeType;
    }

    public Date getSpecificTime() {
        return specificTime;
    }

    public void setSpecificTime(Date specificTime) {
        this.specificTime = specificTime;
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
