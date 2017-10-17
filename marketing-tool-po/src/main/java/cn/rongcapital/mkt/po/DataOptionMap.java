package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class DataOptionMap extends BaseQuery {
    private Integer id;

    private Integer tableId;

    private String tableName;

    private Byte optionStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }

    public Byte getOptionStatus() {
        return optionStatus;
    }

    public void setOptionStatus(Byte optionStatus) {
        this.optionStatus = optionStatus;
    }
}
