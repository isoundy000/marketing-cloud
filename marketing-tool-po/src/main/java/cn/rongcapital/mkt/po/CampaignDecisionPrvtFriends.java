package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

import java.util.Date;

public class CampaignDecisionPrvtFriends extends BaseQuery {
    private Integer id;

    private Integer campaignHeadId;

    private String itemId;

    private String name;

    private String uin;

    private Integer assetId;

    private String ucode;

    private Integer groupId;

    private Integer refreshInterval;

    private Byte refreshIntervalType;

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

    public String getUin() {
        return uin;
    }

    public void setUin(String uin) {
        this.uin = uin == null ? null : uin.trim();
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public String getUcode() {
        return ucode;
    }

    public void setUcode(String ucode) {
        this.ucode = ucode == null ? null : ucode.trim();
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getRefreshInterval() {
        return refreshInterval;
    }

    public void setRefreshInterval(Integer refreshInterval) {
        this.refreshInterval = refreshInterval;
    }

    public Byte getRefreshIntervalType() {
        return refreshIntervalType;
    }

    public void setRefreshIntervalType(Byte refreshIntervalType) {
        this.refreshIntervalType = refreshIntervalType;
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
