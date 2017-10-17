package cn.rongcapital.mkt.common.enums;

public enum SmsTempletTypeEnum {

	FIXED(0,"固定模板"),
	VARIABLE(1,"变量模板")
	;

    private Integer statusCode;
    private String statusName;

    SmsTempletTypeEnum(Integer statusCode, String statusName) {
        this.statusCode = statusCode;
        this.statusName = statusName;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
    
    public static String getDescriptionByStatus(Integer status) {
    	SmsTempletTypeEnum[] smsTempletTypeEnums = SmsTempletTypeEnum.values();
        for (SmsTempletTypeEnum smsTempletTypeEnum : smsTempletTypeEnums) {
            if (smsTempletTypeEnum.getStatusCode().equals(status)) {
                return smsTempletTypeEnum.getStatusName();
            }
        }
        return "";
    }

}
