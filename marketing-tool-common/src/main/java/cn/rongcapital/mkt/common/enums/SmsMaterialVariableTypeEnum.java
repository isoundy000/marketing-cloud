package cn.rongcapital.mkt.common.enums;

/**
 * Created by byf on 12/8/16.
 */
public enum SmsMaterialVariableTypeEnum {

    SMS_TARGET_SEGMENTATION(0,"优惠券")
    ;
    private Integer typeCode;
    private String typeValue;

    SmsMaterialVariableTypeEnum(Integer typeCode, String typeValue) {
        this.typeCode = typeCode;
        this.typeValue = typeValue;
    }

    public Integer getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(Integer typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }

    public static String getTypeValueByTypeCode(Integer typeCode) {
        SmsMaterialVariableTypeEnum[] smsMaterialVariableTypeEnums = SmsMaterialVariableTypeEnum.values();
        for (SmsMaterialVariableTypeEnum smsMaterialVariableTypeEnum : smsMaterialVariableTypeEnums) {
            if (smsMaterialVariableTypeEnum.getTypeCode().equals(typeCode)) {
                return smsMaterialVariableTypeEnum.getTypeValue();
            }
        }
        return "";
    }
}
