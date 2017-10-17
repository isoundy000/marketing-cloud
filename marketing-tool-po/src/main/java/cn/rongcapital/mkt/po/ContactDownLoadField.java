package cn.rongcapital.mkt.po;

import java.math.BigDecimal;
import java.util.Date;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class ContactDownLoadField extends BaseQuery {

    private static final long serialVersionUID = 2186798162059177043L;

    private Integer id;
    
    private String name; // 姓名
    
    private String gender; // 性别
    
    private Date birthday; // 生日
    
    private String mobile; // 手机号码
    
    private String tel; // 固话号码
    
    private String email; // 邮箱地址
    
    private String qq; // QQ号
    
    private String bloodType; // 血型
    
    private String nationality; // 民族
    
    private String citizenship; // 国籍
    
    private String city; // 地区
    
    private BigDecimal monthlyIncome; // 月收入
    
    private BigDecimal monthlyConsume;// 月消费
    
    private String job; // 职业
    
    private String education; // 教育程度
    
    private String employment; // 就业情况
    
    private Integer iq; // IQ
    
    private String identifyNo; // 身份证号
    
    private String drivingLicense; // 驾驶证号
    
    private String maritalStatus; // 婚否
    
    public ContactDownLoadField(){}

    public ContactDownLoadField(Integer id, String name, String gender, Date birthday,
                    String mobile, String tel, String email, String qq, String bloodType,
                    String nationality, String citizenship, String city, BigDecimal monthlyIncome,
                    BigDecimal monthlyConsume, String job, String education, String employment,
                    Integer iq, String identifyNo, String drivingLicense, String maritalStatus) {
        super();
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.mobile = mobile;
        this.tel = tel;
        this.email = email;
        this.qq = qq;
        this.bloodType = bloodType;
        this.nationality = nationality;
        this.citizenship = citizenship;
        this.city = city;
        this.monthlyIncome = monthlyIncome;
        this.monthlyConsume = monthlyConsume;
        this.job = job;
        this.education = education;
        this.employment = employment;
        this.iq = iq;
        this.identifyNo = identifyNo;
        this.drivingLicense = drivingLicense;
        this.maritalStatus = maritalStatus;
    }

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
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public BigDecimal getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(BigDecimal monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public BigDecimal getMonthlyConsume() {
        return monthlyConsume;
    }

    public void setMonthlyConsume(BigDecimal monthlyConsume) {
        this.monthlyConsume = monthlyConsume;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getEmployment() {
        return employment;
    }

    public void setEmployment(String employment) {
        this.employment = employment;
    }

    public Integer getIq() {
        return iq;
    }

    public void setIq(Integer iq) {
        this.iq = iq;
    }

    public String getIdentifyNo() {
        return identifyNo;
    }

    public void setIdentifyNo(String identifyNo) {
        this.identifyNo = identifyNo;
    }

    public String getDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(String drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

}
