package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Yunfeng on 2016-9-28.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class CustomTagColumnShown {
    private Integer colId;
    private String colCode;
    private String colName;

    @JsonProperty("col_id")
    public Integer getColId() {
        return colId;
    }

    public void setColId(Integer colId) {
        this.colId = colId;
    }

    @JsonProperty("col_code")
    public String getColCode() {
        return colCode;
    }

    public void setColCode(String colCode) {
        this.colCode = colCode;
    }

    @JsonProperty("col_name")
    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }
}
