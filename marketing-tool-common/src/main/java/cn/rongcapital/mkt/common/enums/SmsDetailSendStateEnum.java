package cn.rongcapital.mkt.common.enums;

/**
 * Created by byf on 10/19/16.
 */
public enum SmsDetailSendStateEnum {
    SMS_DETAIL_WAITING(0,"等待处理"),
    SMS_DETAIL_SEND_SUCCESS(1,"发送成功"),
    SMS_DETAIL_SEND_FAILURE(2,"发送失败")
    ;

    private Integer stateCode;
    private String stateName;

    SmsDetailSendStateEnum(Integer stateCode, String stateName) {
        this.stateCode = stateCode;
        this.stateName = stateName;
    }

    public Integer getStateCode() {
        return stateCode;
    }

    public void setStateCode(Integer stateCode) {
        this.stateCode = stateCode;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}
