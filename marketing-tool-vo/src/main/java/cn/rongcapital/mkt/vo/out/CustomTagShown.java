package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Yunfeng on 2016-9-28.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class CustomTagShown {
    private String tagId;
    private String tagName;
    private String createTime;
    private Integer coverAudienceCount;
    private String tagSource;

    @JsonProperty("tag_id")
    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    @JsonProperty("tag_name")
    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @JsonProperty("create_time")
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @JsonProperty("cover_audience_count")
    public Integer getCoverAudienceCount() {
        return coverAudienceCount;
    }

    public void setCoverAudienceCount(Integer coverAudienceCount) {
        this.coverAudienceCount = coverAudienceCount;
    }

    @JsonProperty("tag_source")
    public String getTagSource() {
        return tagSource;
    }

    public void setTagSource(String tagSource) {
        this.tagSource = tagSource;
    }
}
