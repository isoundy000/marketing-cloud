package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Yunfeng on 2016-6-14.
 */
public class UploadRepairFileOut {
    private String dataTopic;
    private Integer totalRows;
    private Integer legalRows;
    private Integer illegalRows;
    private String unrecognizeFields;
    private String fileType;

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
}
