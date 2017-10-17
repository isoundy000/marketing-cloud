package cn.rongcapital.mkt.common.enums;

public enum ContactwayEnum {

    MOBILE(1, "mobile", "手机"), 
    EMAIL(2, "email", "电子邮件"), 
    WECHAT(3, "wechat", "微信号"),

    ;

    private ContactwayEnum(int id, String columnName, String description) {
        this.columnName = columnName;
        this.id = id;
        this.description = description;
    }

    private int id;

    private String columnName;

    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static String getColumnNameById(int id) {
        ContactwayEnum[] contactwayEnums = ContactwayEnum.values();
        for (ContactwayEnum contactwayEnum : contactwayEnums) {
            if (contactwayEnum.getId() == id) {
                return contactwayEnum.getColumnName();
            }
        }

        return null;
    }
}
