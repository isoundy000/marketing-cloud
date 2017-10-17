package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Yunfeng on 2016-6-14.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class UploadFileAccordTemplateOut {
    private String dataTopic;
    private Integer legalRows;
    private String unrecognizeFields;
    private String fileType;

    @JsonProperty("data_topic")
    public String getDataTopic() {
        return dataTopic;
    }

    public void setDataTopic(String dataTopic) {
        this.dataTopic = dataTopic;
    }

    @JsonProperty("data_rows")
    public Integer getLegalRows() {
        return legalRows;
    }

    public void setLegalRows(Integer legalRows) {
        this.legalRows = legalRows;
    }

    @JsonProperty("unrecognize_fields")
    public String getUnrecognizeFields() {
        return unrecognizeFields;
    }

    public void setUnrecognizeFields(String unrecognizeFields) {
        this.unrecognizeFields = unrecognizeFields;
    }

    @JsonProperty("file_type")
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
