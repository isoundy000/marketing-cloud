package cn.rongcapital.mkt.common.enums;

public enum BasUserEnum {

    MID("MID", "mid", "mobile"),
    NAME("姓名", "name", "name"),
    GENDER("性别", "gender", "gender"),
    BIRTHDAY("生日", "birthday", "birthday"),
    JOB("职业", "job", "job"),
    MONTHLY_INCOME("月收入", "monthlyIncome", "monthlyIncome"),
    MONTHLY_CONSUME("月消费", "monthlyConsume", "monthlyConsume"),
    PROVINCE("省", "province", "province"),
    CITY("市", "city", "city"),
    MARITAL_STATUS("婚否", "maritalStatus", "maritalStatus"),
    EDUCATION("教育程度", "education", "education"),
    EMPLOYMENT("就业情况", "employment", "employment"),
    NATIONNALITY("民族", "nationality", "nationality"),
    BLOOD_TYPE("血型", "bloodType", "bloodType"),
    CITIZENSHIP("国籍", "citizenship", "citizenship"),
    IQ("IQ", "iq", "iq"),
    IDENTIFY_NO("身份证号", "identifyNo", "identifyNo"),
    DRIVING_LICENSE("驾驶证号", "drivingLicense", "drivingLicense"),
    EMAIL("邮箱", "email", "email"),
    MOBILE("手机号", "mobile", "mobile"),
    TEL("固定电话", "tel", "tel"),
    QQ("QQ", "qq", "qq"),
    IDFA("IDFA", "idfa", "idfa"),
    IMEI("IMEI", "imei", "imei"),
    UUID("UUID", "uuid", "uuid"),
    MAC("MAC", "phoneMac", "phoneMac"),
    SOURCE("数据来源", "source", "source"),
    
    ;

    private BasUserEnum(String basCNName, String basENName, String mcName) {
        this.basCNName = basCNName;
        this.basENName = basENName;
        this.mcName = mcName;
    }

    /**
     * BAS中的中文列名
     */
    private String basCNName;

    /**
     * BAS中的英文字段名
     */
    private String basENName;

    /**
     * Marketing Cloud中对象中的字段名
     */
    private String mcName;

    public String getBasCNName() {
        return basCNName;
    }

    public void setBasCNName(String basCNName) {
        this.basCNName = basCNName;
    }

    public String getBasENName() {
        return basENName;
    }

    public void setBasENName(String basENName) {
        this.basENName = basENName;
    }

    public String getMcName() {
        return mcName;
    }

    public void setMcName(String mcName) {
        this.mcName = mcName;
    }
}
