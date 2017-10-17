package cn.rongcapital.mkt.vo.out;

import java.util.LinkedHashMap;
import java.util.List;

import cn.rongcapital.mkt.vo.BaseOutput;

public class DataGetQualityListOut extends BaseOutput {

    public DataGetQualityListOut() {}

    public DataGetQualityListOut(int code, String msg, int total, List<Object> data) {
        super(code, msg, total, data);
    }

    private LinkedHashMap<String, Object> column;

    public LinkedHashMap<String, Object> getColumn() {
        return column;
    }

    public void setColumn(LinkedHashMap<String, Object> column) {
        this.column = column;
    }
}
