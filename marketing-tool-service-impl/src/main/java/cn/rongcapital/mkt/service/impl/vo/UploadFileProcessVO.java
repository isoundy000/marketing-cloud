package cn.rongcapital.mkt.service.impl.vo;

import java.util.List;

public class UploadFileProcessVO {

    private String dataTopic;
    private Integer totalRows;
    private Integer legalRows;
    private Integer illegalRows;
    private Integer emailRows;
    private Integer mobileRows;
    private Integer duplicateRows;
    private String unrecognizeFields;
    private String fileType;

    private String fileHeader;
    private List<String> illegalRawData;

    public String getDataTopic() {
        return dataTopic;
    }

    public void setDataTopic(String dataTopic) {
        this.dataTopic = dataTopic;
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

    public Integer getIllegalRows() {
        return illegalRows;
    }

    public void setIllegalRows(Integer illegalRows) {
        this.illegalRows = illegalRows;
    }

    public String getUnrecognizeFields() {
        return unrecognizeFields;
    }

    public void setUnrecognizeFields(String unrecognizeFields) {
        this.unrecognizeFields = unrecognizeFields;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileHeader() {
        return fileHeader;
    }

    public void setFileHeader(String fileHeader) {
        this.fileHeader = fileHeader;
    }

    public List<String> getIllegalRawData() {
        return illegalRawData;
    }

    public void setIllegalRawData(List<String> illegalRawData) {
        this.illegalRawData = illegalRawData;
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
}
