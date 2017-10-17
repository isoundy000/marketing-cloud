package cn.rongcapital.mkt.common.enums;

public enum FileNameEnum {

    PARTY(0, "data_party"), 
    POPULATION(1, "data_population"), 
    CUSTOMER_TAGS(2, "data_customer_tags"), 
    ARCH_POINT(3, "data_arch_point"), 
    MEMBER(4, "data_member"), 
    LOGIN(5, "data_login"), 
    PAYMENT(6, "data_payment"), 
    SHOPPING(7, "data_shopping"), 
    IMPORT_DATA_HISTORY_LOG(8, "import_data_history_log"),
    CUSTOM_AUDIENCE(9,"custom_audience"),
    ILLEGAL_DATA(10,"illegal_data"),
    BAS_EVENT(11,"bas_event"),
    BAS_USER(12,"bas_user"),
    CONTACT_LIST(13,"contact_list"),
    CONTACT_USER_DOWNLOAD(14,"contact_user_download")

    ;

    private FileNameEnum(int code, String detailName) {
        this.code = code;
        this.detailName = detailName;
    }

    private String detailName;

    private int code;

    public String getDetailName() {
        return detailName;
    }

    public void setDetailName(String detailName) {
        this.detailName = detailName;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static String getNameByCode(int code) {
        FileNameEnum[] fileNameEnums = FileNameEnum.values();
        for (FileNameEnum fileNameEnum : fileNameEnums) {
            if (fileNameEnum.getCode() == code) {
                return fileNameEnum.getDetailName();
            }
        }

        return "";
    }

}
