package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by ethan on 16/7/26.
 */
public class CampaignContentOut {

    @JsonProperty("content_type")
    private String contentType;

    @JsonProperty("content_name")
    private String contentName;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }
}
