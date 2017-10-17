package cn.rongcapital.mkt.common.enums;

/**
 * Created by Yunfeng on 2016-7-18.
 */
public enum FileTypeEnum {
    POPULATION(1,"人口属性"),
    CUSTOM_TAG(2,"客户标签"),
    ARC_POINT(3,"埋点统计"),
    MEMBER_RECORD(4,"会员卡记录"),
    LOGIN(5,"登录行为"),
    PAYMENT(6,"支付行为"),
    SHOPPING(7,"购物记录")
    ;

    private Integer fileType;
    private String typeValue;

    FileTypeEnum(Integer fileType, String typeValue) {
        this.fileType = fileType;
        this.typeValue = typeValue;
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }

    public static String getTypeValueByType(int fileType){
        FileTypeEnum[] fileTypeEnums = FileTypeEnum.values();
        for (FileTypeEnum fileTypeEnum : fileTypeEnums) {
            if (fileTypeEnum.getFileType() == fileType ) {
                return fileTypeEnum.getTypeValue();
            }
        }
        return "";
    }
}
