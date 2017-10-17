package cn.rongcapital.mkt.po;

import java.util.Date;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class ImportDataHistory extends BaseQuery {
    
    public ImportDataHistory(){}

    public ImportDataHistory(Integer index , Integer size){
        super(index, size);
    }
    
    private Long id;

    private String name;

    private Date importStartTime;

    private Date importEndTime;

    private Integer totalRows;

    private Integer legalRows;

    private Byte success;

    private String source;

    private String sourceFilename;

    private String downloadFilename;

    private Integer emailRows;

    private Integer mobileRows;

    private Integer duplicateRows;

    private Integer illegalRows;

    private String summary;

    private String noRecognizeProperty;

    private String fileUnique;

    private Byte status;

    private Date createTime;

    private Date updateTime;

    private Integer fileType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getImportStartTime() {
        return importStartTime;
    }

    public void setImportStartTime(Date importStartTime) {
        this.importStartTime = importStartTime;
    }

    public Date getImportEndTime() {
        return importEndTime;
    }

    public void setImportEndTime(Date importEndTime) {
        this.importEndTime = importEndTime;
    }

    public Integer getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
    }

    public Integer getLegalRows() {
        return legalRows;
    }

    public void setLegalRows(Integer legalRows) {
        this.legalRows = legalRows;
    }

    public Byte getSuccess() {
        return success;
    }

    public void setSuccess(Byte success) {
        this.success = success;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getSourceFilename() {
        return sourceFilename;
    }

    public void setSourceFilename(String sourceFilename) {
        this.sourceFilename = sourceFilename == null ? null : sourceFilename.trim();
    }

    public String getDownloadFilename() {
        return downloadFilename;
    }

    public void setDownloadFilename(String downloadFilename) {
        this.downloadFilename = downloadFilename == null ? null : downloadFilename.trim();
    }

    public Integer getEmailRows() {
        return emailRows;
    }

    public void setEmailRows(Integer emailRows) {
        this.emailRows = emailRows;
    }

    public Integer getMobileRows() {
        return mobileRows;
    }

    public void setMobileRows(Integer mobileRows) {
        this.mobileRows = mobileRows;
    }

    public Integer getDuplicateRows() {
        return duplicateRows;
    }

    public void setDuplicateRows(Integer duplicateRows) {
        this.duplicateRows = duplicateRows;
    }

    public Integer getIllegalRows() {
        return illegalRows;
    }

    public void setIllegalRows(Integer illegalRows) {
        this.illegalRows = illegalRows;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public String getNoRecognizeProperty() {
        return noRecognizeProperty;
    }

    public void setNoRecognizeProperty(String noRecognizeProperty) {
        this.noRecognizeProperty = noRecognizeProperty == null ? null : noRecognizeProperty.trim();
    }

    public String getFileUnique() {
        return fileUnique;
    }

    public void setFileUnique(String fileUnique) {
        this.fileUnique = fileUnique == null ? null : fileUnique.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }
}
