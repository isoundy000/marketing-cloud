package cn.rongcapital.mkt.vo.out;

import java.util.List;

import cn.rongcapital.mkt.vo.BaseOutput;

public class DataGetFilterRecentTaskOut extends BaseOutput {

    public DataGetFilterRecentTaskOut() {}

    public DataGetFilterRecentTaskOut(int code, String msg, int total, List<Object> data) {
        super(code, msg, total, data);
    }
}
