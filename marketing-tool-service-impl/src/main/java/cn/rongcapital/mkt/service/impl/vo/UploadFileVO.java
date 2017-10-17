package cn.rongcapital.mkt.service.impl.vo;

import cn.rongcapital.mkt.po.ImportDataHistory;
import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * Created by ethan on 16/7/5.
 */
public class UploadFileVO {

    private BaseOutput output;

    private UploadFileProcessVO processVO;

    private ImportDataHistory importDataHistory;

    private String fileName;

    private String downloadFileName;

    public BaseOutput getOutput() {
        return output;
    }

    public void setOutput(BaseOutput output) {
        this.output = output;
    }

    public UploadFileProcessVO getProcessVO() {
        return processVO;
    }

    public void setProcessVO(UploadFileProcessVO processVO) {
        this.processVO = processVO;
    }

    public ImportDataHistory getImportDataHistory() {
        return importDataHistory;
    }

    public void setImportDataHistory(ImportDataHistory importDataHistory) {
        this.importDataHistory = importDataHistory;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDownloadFileName() {
        return downloadFileName;
    }

    public void setDownloadFileName(String downloadFileName) {
        this.downloadFileName = downloadFileName;
    }
}
