package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

import java.util.Date;

public class ImportDataModifyLog extends BaseQuery {
    private Long id;

    private Long importDataId;

    private Date handleTime;

    private Integer totalRows;

    private Integer modifyRows;

    private Integer illegalRows;

    private String modifyFilename;

    private Byte success;

    private String modifyDownloadFilename;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getImportDataId() {
        return importDataId;
    }

    public void setImportDataId(Long importDataId) {
        this.importDataId = importDataId;
    }

    public Date getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(Date handleTime) {
        this.handleTime = handleTime;
    }

    public Integer getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
    }

    public Integer getModifyRows() {
        return modifyRows;
    }

    public void setModifyRows(Integer modifyRows) {
        this.modifyRows = modifyRows;
    }

    public Integer getIllegalRows() {
        return illegalRows;
    }

    public void setIllegalRows(Integer illegalRows) {
        this.illegalRows = illegalRows;
    }

    public String getModifyFilename() {
        return modifyFilename;
    }

    public void setModifyFilename(String modifyFilename) {
        this.modifyFilename = modifyFilename == null ? null : modifyFilename.trim();
    }

    public Byte getSuccess() {
        return success;
    }

    public void setSuccess(Byte success) {
        this.success = success;
    }

    public String getModifyDownloadFilename() {
        return modifyDownloadFilename;
    }

    public void setModifyDownloadFilename(String modifyDownloadFilename) {
        this.modifyDownloadFilename = modifyDownloadFilename == null ? null : modifyDownloadFilename.trim();
    }
}
