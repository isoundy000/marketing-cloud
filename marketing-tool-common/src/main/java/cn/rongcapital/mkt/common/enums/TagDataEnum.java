package cn.rongcapital.mkt.common.enums;

public enum TagDataEnum {

    IN_THIS_MONTH(1, "本月内"),
    IN_THREE_MONTH(2, "三个月内"),
    IN_HALF_YEAR(3, "半年内"),
    ONE_TIME(4, "1次"),
    TWO_TIME(5, "2次"),
    THREE_TIME(6, "3次"),
    MORE_THAN_FOUR_TIME(7, "4次以上"),
    ONE_TO_TWO_TIME(8, "1-2次"),
    MORE_THAN_THREE_TIME(9, "3次以上"),
    LESS_THAN_FIFTY(10, "50元以下"),
    FIFTY_ONE_TO_100(11, "51-100元"),
    ONE_HUNDRED_TO_150(12, "101-150元"),
    ONE_HUNDRED_FIFTY_TO_200(13, "151-200元"),
    TWO_HUNDRED_TO_250(14, "201-250元"),
    TWO_HUNDRED_FIFTY_TO_300(15, "251-300元"),
    MORE_THAN_300(16, "301元以上"),

    ;

    private TagDataEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    private int code;

    private String name;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
