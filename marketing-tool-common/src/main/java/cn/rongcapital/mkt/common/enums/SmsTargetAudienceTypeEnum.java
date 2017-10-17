package cn.rongcapital.mkt.common.enums;

/**
 * Created by byf on 10/18/16.
 */
public enum SmsTargetAudienceTypeEnum {

    SMS_TARGET_SEGMENTATION(0,"受众细分"),
    SMS_TARGET_AUDIENCE(1,"固定人群")
    ;

    private Integer typeCode;
    private String  typeName;

    SmsTargetAudienceTypeEnum(Integer typeCode, String typeName) {
        this.typeCode = typeCode;
        this.typeName = typeName;
    }

    public Integer getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(Integer typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
