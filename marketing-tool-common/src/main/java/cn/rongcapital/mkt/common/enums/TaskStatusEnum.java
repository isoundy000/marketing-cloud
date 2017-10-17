package cn.rongcapital.mkt.common.enums;

public enum TaskStatusEnum {

    PROCESSING((byte) 0, "进行中"), 
    FINISHED((byte) 1, "完成"), 
    FAILED((byte) 2, "失败"),
    DELETED((byte) 3, "已删除"),
    PHYSICAL_DELETED((byte) 4, "物理删除"),
    IN_MEMORY_DELETED((byte) 5, "内存删除"),

    ;

    private TaskStatusEnum(Byte status, String description) {
        this.status = status;
        this.description = description;
    }

    private Byte status;

    private String description;

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static String getDescriptionByStatus(Byte status) {
        TaskStatusEnum[] taskStatusEnums = TaskStatusEnum.values();
        for (TaskStatusEnum taskStatusEnum : taskStatusEnums) {
            if (taskStatusEnum.getStatus().equals(status)) {
                return taskStatusEnum.getDescription();
            }
        }

        return "";
    }

}
