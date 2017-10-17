package cn.rongcapital.mkt.common.enums;

public enum StatusEnum {

    ACTIVE(0, "未删除"), 
    DELETED(1, "已删除"),
    PROCESSED(2, "主数据已处理"),
    MONGO_PROCESSED(3, "芒果已处理");

    private StatusEnum(Integer statusCode, String description) {
        this.statusCode = statusCode;
        this.description = description;
    }

    private Integer statusCode;

    private String description;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
