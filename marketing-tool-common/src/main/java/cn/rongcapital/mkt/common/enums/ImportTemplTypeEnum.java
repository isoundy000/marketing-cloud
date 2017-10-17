package cn.rongcapital.mkt.common.enums;

public enum ImportTemplTypeEnum {
    PARTY("data_party_rows", "主数据"), 
    POPULATION("data_population_rows", "人口属性"), 
    CUSTOMER_TAGS("data_customer_tags_rows", "客户标签"), 
    ARCH_POINT("data_arch_point_rows", "埋点统计"), 
    MEMBER("data_member_rows", "会员卡记录"), 
    LOGIN("data_login_rows", "登录行为"), 
    PAYMENT("data_payment_rows", "支付记录"), 
    SHOPPING("data_shopping_rows", "购物记录"),

    ;
    private ImportTemplTypeEnum(String countName, String name) {
        this.name = name;
        this.countName = countName;
    }

    private String name;

    private String countName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountName() {
        return countName;
    }

    public void setCountName(String countName) {
        this.countName = countName;
    }

    public static String getNameByCountName(String countName) {
        ImportTemplTypeEnum[] importTemplTypeEnumArray = ImportTemplTypeEnum.values();
        for (ImportTemplTypeEnum importTemplTypeEnum : importTemplTypeEnumArray) {
            if (importTemplTypeEnum.getCountName().equalsIgnoreCase(countName)) {
                return importTemplTypeEnum.getName();
            }
        }

        return "";
    }

    public static String getCountNameByName(String name) {
        ImportTemplTypeEnum[] importTemplTypeEnumArray = ImportTemplTypeEnum.values();
        for (ImportTemplTypeEnum importTemplTypeEnum : importTemplTypeEnumArray) {
            if (importTemplTypeEnum.getName().equalsIgnoreCase(name)) {
                return importTemplTypeEnum.getCountName();
            }
        }

        return "";
    }
}
