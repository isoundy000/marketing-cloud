package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

import java.util.Date;

public class WechatMemberCountCache extends BaseQuery {
    private Long id;

    private Integer wechatAssetType;

    private Integer wechatAssetTypeMemberCount;

    private String countTime;

    private Date createTime;

    private Date updateTime;

    private Byte status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWechatAssetType() {
        return wechatAssetType;
    }

    public void setWechatAssetType(Integer wechatAssetType) {
        this.wechatAssetType = wechatAssetType;
    }

    public Integer getWechatAssetTypeMemberCount() {
        return wechatAssetTypeMemberCount;
    }

    public void setWechatAssetTypeMemberCount(Integer wechatAssetTypeMemberCount) {
        this.wechatAssetTypeMemberCount = wechatAssetTypeMemberCount;
    }

    public String getCountTime() {
        return countTime;
    }

    public void setCountTime(String countTime) {
        this.countTime = countTime == null ? null : countTime.trim();
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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}
