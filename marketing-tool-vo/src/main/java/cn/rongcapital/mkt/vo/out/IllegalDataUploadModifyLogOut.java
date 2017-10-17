package cn.rongcapital.mkt.vo.out;

import java.util.Date;

public class IllegalDataUploadModifyLogOut {

    private String handleTime;

    private String modifyFilename;

    private String modifyDownloadFilename;

    public String getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(String handleTime) {
        this.handleTime = handleTime;
    }

    public String getModifyFilename() {
        return modifyFilename;
    }

    public void setModifyFilename(String modifyFilename) {
        this.modifyFilename = modifyFilename;
    }

    public String getModifyDownloadFilename() {
        return modifyDownloadFilename;
    }

    public void setModifyDownloadFilename(String modifyDownloadFilename) {
        this.modifyDownloadFilename = modifyDownloadFilename;
    }
}
