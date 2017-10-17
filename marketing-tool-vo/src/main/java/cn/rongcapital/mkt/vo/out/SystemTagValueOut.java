package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by byf on 11/8/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SystemTagValueOut {

    @NotEmpty
    private String tagValueId;

    @NotEmpty
    private String tagValue;

    @JsonProperty("tag_value_id")
    public String getTagValueId() {
        return tagValueId;
    }

    public void setTagValueId(String tagValueId) {
        this.tagValueId = tagValueId;
    }

    @JsonProperty("tag_value")
    public String getTagValue() {
        return tagValue;
    }

    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }

}
