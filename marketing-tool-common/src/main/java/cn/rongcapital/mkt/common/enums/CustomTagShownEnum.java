package cn.rongcapital.mkt.common.enums;

/**
 * Created by Yunfeng on 2016-9-28.
 */
public enum CustomTagShownEnum {
    COLUMN_TAG_NAME(1,"tag_name","标签名称"),
    COLUMN_CREATE_TIEM(2,"create_time","添加时间"),
    COLUMN_TAG_SOURCE(3,"tag_source","标签来源"),
    COLUMN_COVER_AUDIENCE(4,"cover_audience_count","覆盖人群")
    ;

    private Integer columnId;
    private String columnCode;
    private String columnName;

    CustomTagShownEnum(Integer columnId, String columnCode, String columnName) {
        this.columnId = columnId;
        this.columnCode = columnCode;
        this.columnName = columnName;
    }

    public Integer getColumnId() {
        return columnId;
    }

    public void setColumnId(Integer columnId) {
        this.columnId = columnId;
    }

    public String getColumnCode() {
        return columnCode;
    }

    public void setColumnCode(String columnCode) {
        this.columnCode = columnCode;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
}
