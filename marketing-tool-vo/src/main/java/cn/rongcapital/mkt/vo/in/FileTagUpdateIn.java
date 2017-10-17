package cn.rongcapital.mkt.vo.in;

import cn.rongcapital.mkt.vo.BaseInput;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;

/**
 * Created by Yunfeng on 2016-6-25.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class FileTagUpdateIn extends BaseInput{

    private String fileUnique;
    private String fileType;
    private ArrayList<String> tagNames;

    @JsonProperty("file_unique")
    public String getFileUnique() {
        return fileUnique;
    }

    public void setFileUnique(String fileUnique) {
        this.fileUnique = fileUnique;
    }

    @JsonProperty("file_type")
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @JsonProperty("tag_names")
    public ArrayList<String> getTagNames() {
        return tagNames;
    }

    public void setTagNames(ArrayList<String> tagNames) {
        this.tagNames = tagNames;
    }
}
