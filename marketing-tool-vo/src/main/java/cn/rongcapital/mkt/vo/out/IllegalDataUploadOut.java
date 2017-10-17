package cn.rongcapital.mkt.vo.out;

import cn.rongcapital.mkt.vo.BaseOutput;

import java.util.List;

public class IllegalDataUploadOut extends BaseOutput {

    private List<IllegalDataUploadModifyLogOut> modifyLog;

    public IllegalDataUploadOut() {
    }

    public IllegalDataUploadOut(int code, String msg, int total, List<Object> data) {
        super(code, msg, total, data);
    }

    public List<IllegalDataUploadModifyLogOut> getModifyLog() {
        return modifyLog;
    }

    public void setModifyLog(List<IllegalDataUploadModifyLogOut> modifyLog) {
        this.modifyLog = modifyLog;
    }
}
