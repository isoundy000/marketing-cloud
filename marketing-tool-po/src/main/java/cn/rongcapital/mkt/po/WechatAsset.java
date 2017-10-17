package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

import java.util.Date;

public class WechatAsset extends BaseQuery {
    private Integer id;

    private Integer assetId;

    private Integer assetType;

    private String assetName;

    private String nickname;

    private String wxAcct;

    private Date consignationTime;

    private Integer totalCount;

    private String groupIds;

    private String imgfileUrl;

    private Byte status;

    private Date createTime;

    private Date updateTime;

    private String appId;

    private String wechatQrcode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public Integer getAssetType() {
        return assetType;
    }

    public void setAssetType(Integer assetType) {
        this.assetType = assetType;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName == null ? null : assetName.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getWxAcct() {
        return wxAcct;
    }

    public void setWxAcct(String wxAcct) {
        this.wxAcct = wxAcct == null ? null : wxAcct.trim();
    }

    public Date getConsignationTime() {
        return consignationTime;
    }

    public void setConsignationTime(Date consignationTime) {
        this.consignationTime = consignationTime;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public String getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(String groupIds) {
        this.groupIds = groupIds == null ? null : groupIds.trim();
    }

    public String getImgfileUrl() {
        return imgfileUrl;
    }

    public void setImgfileUrl(String imgfileUrl) {
        this.imgfileUrl = imgfileUrl == null ? null : imgfileUrl.trim();
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

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public String getWechatQrcode() {
        return wechatQrcode;
    }

    public void setWechatQrcode(String wechatQrcode) {
        this.wechatQrcode = wechatQrcode == null ? null : wechatQrcode.trim();
    }
}
