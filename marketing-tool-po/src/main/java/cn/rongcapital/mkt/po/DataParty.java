package cn.rongcapital.mkt.po;

import java.math.BigDecimal;
import java.util.Date;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class DataParty extends BaseQuery {
    
    public DataParty(){}
    
    public DataParty(Integer index , Integer size){
        super(index, size);
    }
    
    private Integer id;

    private String mobile;

    private String name;

    private Byte gender;

    private Date birthday;

    private String citizenship;

    private String provice;

    private String city;

    private String job;

    private BigDecimal monthlyIncome;

    private String memberLevel;

    private String memberPoints;

    private String source;

    private BigDecimal monthlyConsume;

    private Date lastLogin;

    private Byte status;

    private Date createTime;

    private Date updateTime;

    private String batchId;

    private Integer mdType;

    private String mappingKeyid;

    private String tel;

    private String email;

    private String qq;

    private String identifyNo;

    private String drivingLicense;

    private String wxmpId;

    private String wxCode;

    private String wxUin;

    private String wxpersonId;

    private String idfa;

    private String imei;

    private String unionid;

    private String acctNo;

    private String acctType;

    private String flag2;

    private String bitmap;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship == null ? null : citizenship.trim();
    }

    public String getProvice() {
        return provice;
    }

    public void setProvice(String provice) {
        this.provice = provice == null ? null : provice.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    public BigDecimal getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(BigDecimal monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public String getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(String memberLevel) {
        this.memberLevel = memberLevel == null ? null : memberLevel.trim();
    }

    public String getMemberPoints() {
        return memberPoints;
    }

    public void setMemberPoints(String memberPoints) {
        this.memberPoints = memberPoints == null ? null : memberPoints.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public BigDecimal getMonthlyConsume() {
        return monthlyConsume;
    }

    public void setMonthlyConsume(BigDecimal monthlyConsume) {
        this.monthlyConsume = monthlyConsume;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
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

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId == null ? null : batchId.trim();
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
        this.mappingKeyid = mappingKeyid == null ? null : mappingKeyid.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    public String getIdentifyNo() {
        return identifyNo;
    }

    public void setIdentifyNo(String identifyNo) {
        this.identifyNo = identifyNo == null ? null : identifyNo.trim();
    }

    public String getDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(String drivingLicense) {
        this.drivingLicense = drivingLicense == null ? null : drivingLicense.trim();
    }

    public String getWxmpId() {
        return wxmpId;
    }

    public void setWxmpId(String wxmpId) {
        this.wxmpId = wxmpId == null ? null : wxmpId.trim();
    }

    public String getWxCode() {
        return wxCode;
    }

    public void setWxCode(String wxCode) {
        this.wxCode = wxCode == null ? null : wxCode.trim();
    }

    public String getWxUin() {
        return wxUin;
    }

    public void setWxUin(String wxUin) {
        this.wxUin = wxUin == null ? null : wxUin.trim();
    }

    public String getWxpersonId() {
        return wxpersonId;
    }

    public void setWxpersonId(String wxpersonId) {
        this.wxpersonId = wxpersonId == null ? null : wxpersonId.trim();
    }

    public String getIdfa() {
        return idfa;
    }

    public void setIdfa(String idfa) {
        this.idfa = idfa == null ? null : idfa.trim();
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei == null ? null : imei.trim();
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid == null ? null : unionid.trim();
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo == null ? null : acctNo.trim();
    }

    public String getAcctType() {
		return acctType;
	}

	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}

	public String getFlag2() {
        return flag2;
    }

    public void setFlag2(String flag2) {
        this.flag2 = flag2 == null ? null : flag2.trim();
    }

    public String getBitmap() {
        return bitmap;
    }

    public void setBitmap(String bitmap) {
        this.bitmap = bitmap == null ? null : bitmap.trim();
    }
}
