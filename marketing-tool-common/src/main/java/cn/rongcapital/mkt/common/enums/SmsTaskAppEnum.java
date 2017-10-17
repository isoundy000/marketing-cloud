package cn.rongcapital.mkt.common.enums;

public enum SmsTaskAppEnum {

	ADVERT_SMS((byte) 0, "营销短信"), 
	SERVICE_SMS((byte) 1, "服务通知"),
	IDENTIFY_CODE_SMS((byte) 2, "验证码通知短信"),
	//0:营销短信模板,1:服务通知模板,2：短信验证码模板
   ;
	
   private SmsTaskAppEnum(Byte status, String description) {
       this.status = status;
       this.description = description;
   }
	
   private Byte status;

   private String description;

   public Byte getStatus() {
       return status;
   }

   public void setStatus(Byte status) {
       this.status = status;
   }

   public String getDescription() {
       return description;
   }

   public void setDescription(String description) {
       this.description = description;
   }

   public static String getDescriptionByStatus(Byte status) {
	   SmsTaskAppEnum[] smsTaskAppEnums = SmsTaskAppEnum.values();
       for (SmsTaskAppEnum smsTaskAppEnum : smsTaskAppEnums) {
           if (smsTaskAppEnum.getStatus().equals(status)) {
               return smsTaskAppEnum.getDescription();
           }
       }

       return "";
   }
}
