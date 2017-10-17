package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by byf on 11/10/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchTagValueOut {

    @NotEmpty
    private String tagValue;

    @NotEmpty
    private String tagValueSeq;
    private Integer valueCount;

    @JsonProperty("tag_value")
    public String getTagValue() {
        return tagValue;
    }

    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }

    @JsonProperty("tag_value_seq")
    public String getTagValueSeq() {
        return tagValueSeq;
    }

    public void setTagValueSeq(String tagValueSeq) {
        this.tagValueSeq = tagValueSeq;
    }

    @JsonProperty("value_count")
    public Integer getValueCount() {
        return valueCount;
    }

    public void setValueCount(Integer valueCount) {
        this.valueCount = valueCount;
    }
}
