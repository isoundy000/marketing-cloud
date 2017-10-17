package cn.rongcapital.mkt.vo.out;

import java.util.List;

import cn.rongcapital.mkt.vo.BaseOutput;
import org.codehaus.jackson.annotate.JsonProperty;

public class DataGetMainCountOut extends BaseOutput {

    private Integer dataSourceCount;

    public DataGetMainCountOut() {
    }

    public DataGetMainCountOut(int code, String msg, int total, List<Object> data) {
        super(code, msg, total, data);
    }

    @JsonProperty("data_source_count")
    public Integer getDataSourceCount() {
        return dataSourceCount;
    }

    public void setDataSourceCount(Integer dataSourceCount) {
        this.dataSourceCount = dataSourceCount;
    }
}
