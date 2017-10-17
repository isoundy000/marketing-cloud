package cn.rongcapital.mkt.common.enums;

public enum SmsTempleteAuditStatusEnum {

	AUDIT_STATUS_NO_CHECK(0,"未审核"),
	AUDIT_STATUS_PASS(1,"审核通过"),
	AUDIT_STATUS_NO_PASS(2,"审核不通过")
	;

    private Integer statusCode;
    private String statusName;

    SmsTempleteAuditStatusEnum(Integer statusCode, String statusName) {
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
    	SmsTempleteAuditStatusEnum[] smsTempleteAuditStatusEnums = SmsTempleteAuditStatusEnum.values();
        for (SmsTempleteAuditStatusEnum smsTempleteAuditStatusEnum : smsTempleteAuditStatusEnums) {
            if (smsTempleteAuditStatusEnum.getStatusCode().equals(status)) {
                return smsTempleteAuditStatusEnum.getStatusName();
            }
        }
        return "";
    }

	
}
