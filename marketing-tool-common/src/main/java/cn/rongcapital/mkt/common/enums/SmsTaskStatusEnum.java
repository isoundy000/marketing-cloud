package cn.rongcapital.mkt.common.enums;

/**
 * Created by byf on 10/20/16.
 */

//0:未启动\n1:已预约\n2:执行中\n3:暂停中\n4:已结束
public enum SmsTaskStatusEnum {
    TASK_UNSTART(0,"未启动"),
    TASK_RESERVATION(1,"已预约"),
    TASK_EXECUTING(2,"执行中"),
    TASK_PAUSE(3,"暂停中"),
    TASK_FINISH(4,"已结束")
    ;

    private Integer statusCode;
    private String statusName;

    SmsTaskStatusEnum(Integer statusCode, String statusName) {
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
    	SmsTaskStatusEnum[] smsTaskStatusEnums = SmsTaskStatusEnum.values();
        for (SmsTaskStatusEnum smsTaskStatusEnum : smsTaskStatusEnums) {
            if (smsTaskStatusEnum.getStatusCode().equals(status)) {
                return smsTaskStatusEnum.getStatusName();
            }
        }
        return "";
    }

}
