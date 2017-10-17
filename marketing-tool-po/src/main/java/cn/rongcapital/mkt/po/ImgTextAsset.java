package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

import java.util.Date;

public class ImgTextAsset extends BaseQuery {
		
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String name;

    private Byte type;

    private String ownerName;

    private String pcPreviewUrl;

    private String mobilePreviewUrl;

    private String imgfileUrl;
    
    private String imgfileName;

    private String materialId;

    private Byte status;

    private Date createTime;

    private Date updateTime;

    private String pubId;

    private String pubName;

    private Byte wechatStatus;

    private Byte showCoverPic;

    private Byte thumbReady;

    private Byte wxType;

    private String digest;
    
    private Byte firstAsset;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName == null ? null : ownerName.trim();
    }

    public String getPcPreviewUrl() {
        return pcPreviewUrl;
    }

    public void setPcPreviewUrl(String pcPreviewUrl) {
        this.pcPreviewUrl = pcPreviewUrl == null ? null : pcPreviewUrl.trim();
    }

    public String getMobilePreviewUrl() {
        return mobilePreviewUrl;
    }

    public void setMobilePreviewUrl(String mobilePreviewUrl) {
        this.mobilePreviewUrl = mobilePreviewUrl == null ? null : mobilePreviewUrl.trim();
    }

    public String getImgfileUrl() {
        return imgfileUrl;
    }

    public void setImgfileUrl(String imgfileUrl) {
        this.imgfileUrl = imgfileUrl == null ? null : imgfileUrl.trim();
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId == null ? null : materialId.trim();
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

    public String getPubId() {
        return pubId;
    }

    public void setPubId(String pubId) {
        this.pubId = pubId == null ? null : pubId.trim();
    }

    public String getPubName() {
        return pubName;
    }

    public void setPubName(String pubName) {
        this.pubName = pubName == null ? null : pubName.trim();
    }

    public Byte getWechatStatus() {
        return wechatStatus;
    }

    public void setWechatStatus(Byte wechatStatus) {
        this.wechatStatus = wechatStatus;
    }

    public Byte getShowCoverPic() {
        return showCoverPic;
    }

    public void setShowCoverPic(Byte showCoverPic) {
        this.showCoverPic = showCoverPic;
    }

    public Byte getThumbReady() {
        return thumbReady;
    }

    public void setThumbReady(Byte thumbReady) {
        this.thumbReady = thumbReady;
    }

    public Byte getWxType() {
        return wxType;
    }

    public void setWxType(Byte wxType) {
        this.wxType = wxType;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest == null ? null : digest.trim();
    }

	public String getImgfileName() {
		return imgfileName;
	}

	public void setImgfileName(String imgfileName) {
		this.imgfileName = imgfileName;
	}
    
    public Byte getFirstAsset() {
        return firstAsset;
    }

    public void setFirstAsset(Byte firstAsset) {
        this.firstAsset = firstAsset;
    }

	
}