package cn.rongcapital.mkt.po.mongodb;

import cn.rongcapital.mkt.common.util.GenderUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Yunfeng on 2016-7-7.
 */
public class WechatMember implements Serializable{

    private static final long serialVersionUID = -7640978737144901901L;

    @Id
    private String id;

    private String wxCode;

    private String wxName;

    private String wxGroupId;

    private String nickname;

    private String sex;

    private Byte gender;

    private String country;

    @Field(value = "provice") // TODO 需要把从original到mongo整个数据链及DB修正拼写错误
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

    private Integer mid;

    private Integer mdType;

    private String mappingKeyid;

    private String source;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
        this.sex = GenderUtils.byteToChar(gender);
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
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

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Integer getMdType() {
        return mdType;
    }

    public void setMdType(Integer mdType) {
        this.mdType = mdType;
    }

    public String getMappingKeyid() {
        return mappingKeyid;
    }

    public void setMappingKeyid(String mappingKeyid) {
        this.mappingKeyid = mappingKeyid;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
