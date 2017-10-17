package cn.rongcapital.mkt.common.enums;

public enum GenderEnum {

    MALE((byte)1, "男"),
    FEMALE((byte)2, "女"),
    OTHER((byte)3, "其他"),
    UNSURE((byte)4, "未知")
    ;

    GenderEnum(Byte statusCode, String description) {
        this.statusCode = statusCode;
        this.description = description;
    }

    private Byte statusCode;

    private String description;

    public Byte getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Byte statusCode) {
        this.statusCode = statusCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
