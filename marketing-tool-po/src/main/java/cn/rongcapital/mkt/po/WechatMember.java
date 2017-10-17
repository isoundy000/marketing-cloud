package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

import java.util.Date;

public class WechatMember extends BaseQuery {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String wxCode;

    private String wxName;

    private String wxGroupId;

    private String nickname;

    private Integer sex;

    private String country;

    private String province;

    private String city;

    private String county;

    private String birthday;

    private String signature;

    private String isFriend;

    private String pubId;

    private String uin;

    private String subscribeYn;

    private String subscribeTime;

    private String activeTime;

    private String activity48hYn;

    private String headImageUrl;

    private String remark;

    private Byte status;

    private Date createTime;

    private Date updateTime;

    private Byte selected;

    private String bitmap;

    private Integer keyid;

    private String fansJson;
    
    /**
     * 临时属性
     */
    private String sexC;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWxCode() {
        return wxCode;
    }

    public void setWxCode(String wxCode) {
        this.wxCode = wxCode == null ? null : wxCode.trim();
    }

    public String getWxName() {
        return wxName;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName == null ? null : wxName.trim();
    }

    public String getWxGroupId() {
        return wxGroupId;
    }

    public void setWxGroupId(String wxGroupId) {
        this.wxGroupId = wxGroupId == null ? null : wxGroupId.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county == null ? null : county.trim();
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature == null ? null : signature.trim();
    }

    public String getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(String isFriend) {
        this.isFriend = isFriend == null ? null : isFriend.trim();
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

    public String getSubscribeYn() {
        return subscribeYn;
    }

    public void setSubscribeYn(String subscribeYn) {
        this.subscribeYn = subscribeYn == null ? null : subscribeYn.trim();
    }

    public String getSubscribeTime() {
        return subscribeTime;
    }

    public void setSubscribeTime(String subscribeTime) {
        this.subscribeTime = subscribeTime == null ? null : subscribeTime.trim();
    }

    public String getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(String activeTime) {
        this.activeTime = activeTime == null ? null : activeTime.trim();
    }

    public String getActivity48hYn() {
        return activity48hYn;
    }

    public void setActivity48hYn(String activity48hYn) {
        this.activity48hYn = activity48hYn == null ? null : activity48hYn.trim();
    }

    public String getHeadImageUrl() {
        return headImageUrl;
    }

    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl == null ? null : headImageUrl.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public Byte getSelected() {
        return selected;
    }

    public void setSelected(Byte selected) {
        this.selected = selected;
    }

    public String getBitmap() {
        return bitmap;
    }

    public void setBitmap(String bitmap) {
        this.bitmap = bitmap == null ? null : bitmap.trim();
    }

    public Integer getKeyid() {
        return keyid;
    }

    public void setKeyid(Integer keyid) {
        this.keyid = keyid;
    }

	public String getFansJson() {
		return fansJson;
	}

	public void setFansJson(String fansJson) {
		this.fansJson = fansJson;
	}

	public String getSexC() {
		return sexC;
	}

	public void setSexC(String sexC) {
		this.sexC = sexC;
	}    
    
	
	
}
