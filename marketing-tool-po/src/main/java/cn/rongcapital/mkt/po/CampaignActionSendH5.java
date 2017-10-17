package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

import java.util.Date;

public class CampaignActionSendH5 extends BaseQuery {
    private Integer id;

    private Integer campaignHeadId;

    private String itemId;

    private String name;

    private Integer pubAssetId;

    private Integer prvAssetId;

    private Integer groupId;

    private Integer imgTextAssetId;

    private String pubId;

    private String uin;

    private String ucode;

    private Integer materialId;

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

    public Integer getPubAssetId() {
        return pubAssetId;
    }

    public void setPubAssetId(Integer pubAssetId) {
        this.pubAssetId = pubAssetId;
    }

    public Integer getPrvAssetId() {
        return prvAssetId;
    }

    public void setPrvAssetId(Integer prvAssetId) {
        this.prvAssetId = prvAssetId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getImgTextAssetId() {
        return imgTextAssetId;
    }

    public void setImgTextAssetId(Integer imgTextAssetId) {
        this.imgTextAssetId = imgTextAssetId;
    }

    public String getPubId() {
        return pubId;
    }

    public void setPubId(String pubId) {
        this.pubId = pubId == null ? null : pubId.trim();
    }

    public String getUin() {
        return uin;
    }

    public void setUin(String uin) {
        this.uin = uin == null ? null : uin.trim();
    }

    public String getUcode() {
        return ucode;
    }

    public void setUcode(String ucode) {
        this.ucode = ucode == null ? null : ucode.trim();
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
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
