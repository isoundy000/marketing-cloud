package cn.rongcapital.mkt.vo.out;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonProperty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*************************************************
 * @功能及特点的描述简述: 展示联系人档案信息类
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：DMP系统
 * @author: congshulin
 * @version: 版本
 * @date(创建、开发日期)：2017-07-11
 * 最后修改日期：2017-07-11
 * @复审人：
 *************************************************/
@JsonIgnoreProperties(ignoreUnknown=true)
public class BasicContactInfoOut {
		@NotNull
		private Integer contactId;

		private String name;

        private String gender;
        
        private String birthday;

        private String bloodType;
        
        private Integer iq;

		private Long identifyNo;

        private Long drivingLicense;
        
        private String mobile;

        private String tel;

        private String email;

        private String qq;

		private String wechat;

        private String acctType;
        
        private String acctNo;

		private String citizenship;

        private String provice;
        
        private String city;
        
        private String nationality;
        
        private String job;
        
        private String monthlyIncome;
        
        private String monthlyConsume;
        
        private String maritalStatus;
        
        private String education;
        
        private String employment;
        
        private String photo;

        /**
         * 微信别名
         */
        private String wx_nickname;
        /**
         * 微信性别
         */
        private String wx_gender;
        /**
         * 微信_国家
         */
        private String wx_country;
        /**
         * 微信_省份
         */
        private String wx_provice;
        /**
         * 微信_市区
         */
        private String wx_city;

        @JsonProperty("contact_id")
		public Integer getContactId() {
			return contactId;
		}

		public void setContactId(Integer contactId) {
			this.contactId = contactId;
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

		public String getBirthday() {
			return birthday;
		}

		public void setBirthday(String birthday) {
			this.birthday = birthday;
		}
        @JsonProperty("blood_type")
		public String getBloodType() {
			return bloodType;
		}

		public void setBloodType(String bloodType) {
			this.bloodType = bloodType;
		}

		public Integer getIq() {
			return iq;
		}

		public void setIq(Integer iq) {
			this.iq = iq;
		}
        @JsonProperty("identify_no")
		public Long getIdentifyNo() {
			return identifyNo;
		}

		public void setIdentifyNo(Long identifyNo) {
			this.identifyNo = identifyNo;
		}
        @JsonProperty("driving_license")
		public Long getDrivingLicense() {
			return drivingLicense;
		}

		public void setDrivingLicense(Long drivingLicense) {
			this.drivingLicense = drivingLicense;
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

		public String getWechat() {
			return wechat;
		}

		public void setWechat(String wechat) {
			this.wechat = wechat;
		}
        @JsonProperty("acct_type")
		public String getAcctType() {
			return acctType;
		}

		public void setAcctType(String acctType) {
			this.acctType = acctType;
		}
        @JsonProperty("acct_no")
		public String getAcctNo() {
			return acctNo;
		}

		public void setAcctNo(String acctNo) {
			this.acctNo = acctNo;
		}

		public String getCitizenship() {
			return citizenship;
		}

		public void setCitizenship(String citizenship) {
			this.citizenship = citizenship;
		}

		public String getProvice() {
			return provice;
		}

		public void setProvice(String provice) {
			this.provice = provice;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getNationality() {
			return nationality;
		}

		public void setNationality(String nationality) {
			this.nationality = nationality;
		}

		public String getJob() {
			return job;
		}

		public void setJob(String job) {
			this.job = job;
		}

        @JsonProperty("monthly_income")
		public String getMonthlyIncome() {
			return monthlyIncome;
		}

		public void setMonthlyIncome(String monthlyIncome) {
			this.monthlyIncome = monthlyIncome;
		}
        @JsonProperty("monthly_consume")
		public String getMonthlyConsume() {
			return monthlyConsume;
		}

		public void setMonthlyConsume(String monthlyConsume) {
			this.monthlyConsume = monthlyConsume;
		}
		@JsonProperty("marital_status")
		public String getMaritalStatus() {
			return maritalStatus;
		}

		public void setMaritalStatus(String maritalStatus) {
			this.maritalStatus = maritalStatus;
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

		public String getPhoto() {
			return photo;
		}

		public void setPhoto(String photo) {
			this.photo = photo;
		}

		public String getWx_nickname() {
			return wx_nickname;
		}

		public void setWx_nickname(String wx_nickname) {
			this.wx_nickname = wx_nickname;
		}

		public String getWx_gender() {
			return wx_gender;
		}

		public void setWx_gender(String wx_gender) {
			this.wx_gender = wx_gender;
		}

		public String getWx_country() {
			return wx_country;
		}

		public void setWx_country(String wx_country) {
			this.wx_country = wx_country;
		}

		public String getWx_provice() {
			return wx_provice;
		}

		public void setWx_provice(String wx_provice) {
			this.wx_provice = wx_provice;
		}

		public String getWx_city() {
			return wx_city;
		}

		public void setWx_city(String wx_city) {
			this.wx_city = wx_city;
		}
		
		
		
}
