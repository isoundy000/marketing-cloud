package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

import java.util.Date;

public class WechatQrcode extends BaseQuery {
    private Integer id;

    private String wxName;
    
    private String wxAcct;

    private Integer chCode;

    private Byte isAudience;

    private String audienceName;

    private String relatedTags;

    private String comments;

    private Date createTime;

    private Date expirationTime;

    private Byte status;

    private String qrcodePic;

    private String qrcodeUrl;

    private String batchId;

    private String qrcodeName;

    private Byte authorization;
    
    private String ticket;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWxName() {
        return wxName;
    }
    
    public String getWxAcct() {
        return wxAcct;
    }

    public void setWxAcct(String wxAcct) {
        this.wxAcct = wxAcct == null ? null : wxAcct.trim();
    }

    public void setWxName(String wxName) {
        this.wxName = wxName == null ? null : wxName.trim();
    }

    public Integer getChCode() {
        return chCode;
    }

    public void setChCode(Integer chCode) {
        this.chCode = chCode;
    }

    public Byte getIsAudience() {
        return isAudience;
    }

    public void setIsAudience(Byte isAudience) {
        this.isAudience = isAudience;
    }

    public String getAudienceName() {
        return audienceName;
    }

    public void setAudienceName(String audienceName) {
        this.audienceName = audienceName == null ? null : audienceName.trim();
    }

    public String getRelatedTags() {
        return relatedTags;
    }

    public void setRelatedTags(String relatedTags) {
        this.relatedTags = relatedTags == null ? null : relatedTags.trim();
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getQrcodePic() {
        return qrcodePic;
    }

    public void setQrcodePic(String qrcodePic) {
        this.qrcodePic = qrcodePic == null ? null : qrcodePic.trim();
    }

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl == null ? null : qrcodeUrl.trim();
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId == null ? null : batchId.trim();
    }

    public String getQrcodeName() {
        return qrcodeName;
    }

    public void setQrcodeName(String qrcodeName) {
        this.qrcodeName = qrcodeName == null ? null : qrcodeName.trim();
    }

    public Byte getAuthorization() {
        return authorization;
    }

    public void setAuthorization(Byte authorization) {
        this.authorization = authorization;
    }
    
    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket == null ? null : ticket.trim();
    }
}
