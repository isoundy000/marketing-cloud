package cn.rongcapital.mkt.common.enums;

public enum DataTypeEnum {

    PARTY(0, "主数据"), 
    POPULATION(1, "人口属性"), 
    CUSTOMER_TAGS(2, "客户标签"), 
    ARCH_POINT(3, "埋点统计"), 
    MEMBER(4, "会员卡记录"), 
    LOGIN(5, "登录行为"), 
    PAYMENT(6, "支付记录"), 
    SHOPPING(7, "购物记录"),
    WECHAT(8, "微信会员记录"),
    WECHAT1(9, "微信粉丝"),

    ;

    private DataTypeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 数据类型代码
     */
    private int code;

    /**
     * 数据类型描述
     */
    private String description;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static boolean isContainCode(int code) {
        boolean result = false;
        DataTypeEnum[] dataTypeEnums = DataTypeEnum.values();
        for (DataTypeEnum dataTypeEnum : dataTypeEnums) {
            if (dataTypeEnum.getCode() == code) {
                return true;
            }
        }
        return result;
    }

}
