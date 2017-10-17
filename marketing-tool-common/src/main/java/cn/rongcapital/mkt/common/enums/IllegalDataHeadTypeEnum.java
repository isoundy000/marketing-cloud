package cn.rongcapital.mkt.common.enums;

public enum IllegalDataHeadTypeEnum {

    DATA("0", "数据"),
    HEADER("1", "数据头");

    IllegalDataHeadTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 数据类型代码
     */
    private String code;

    /**
     * 数据类型描述
     */
    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
