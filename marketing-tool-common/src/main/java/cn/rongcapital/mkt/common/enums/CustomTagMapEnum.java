package cn.rongcapital.mkt.common.enums;

public enum CustomTagMapEnum {

    AUDIENCE(2, "联系人"),

    ;

    private CustomTagMapEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    private int code;

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

}
