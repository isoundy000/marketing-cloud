package cn.rongcapital.mkt.common.enums;

/**
 * Created by byf on 12/13/16.
 */
public enum SmsMaterialVariableEnum {

    VARIABLE_COUPON_EFFECTIVE_TIME(0, "优惠券", "有效期", "effective_time"),
    VARIABLE_COUPON_AMOUNT(0, "优惠券", "价值", "amount"),
    VARIABLE_COUPON_CHANNEL_CODE(0, "优惠券", "渠道", "channelCode"),
    VARIABLE_COUPON_COUPON_CODE(0, "优惠券", "优惠码", "code");

    private Integer variableType;
    private String variableTypeName;
    private String variableName;
    private String variableCode;

    SmsMaterialVariableEnum(Integer variableType, String variableTypeName, String variableName, String variableCode) {
        this.variableType = variableType;
        this.variableTypeName = variableTypeName;
        this.variableName = variableName;
        this.variableCode = variableCode;
    }

    public Integer getVariableType() {
        return variableType;
    }

    public void setVariableType(Integer variableType) {
        this.variableType = variableType;
    }

    public String getVariableTypeName() {
        return variableTypeName;
    }

    public void setVariableTypeName(String variableTypeName) {
        this.variableTypeName = variableTypeName;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public String getVariableCode() {
        return variableCode;
    }

    public void setVariableCode(String variableCode) {
        this.variableCode = variableCode;
    }

    public static SmsMaterialVariableEnum getSmsMaterialVariableEnum(Integer materielType,String value) {
        for (SmsMaterialVariableEnum smsMaterialVariableEnum : SmsMaterialVariableEnum.values()) {
            if (smsMaterialVariableEnum.getVariableName().equals(value) && smsMaterialVariableEnum.getVariableType().equals(materielType)) {
                return smsMaterialVariableEnum;
            }
        }
        return null;
    }
}
