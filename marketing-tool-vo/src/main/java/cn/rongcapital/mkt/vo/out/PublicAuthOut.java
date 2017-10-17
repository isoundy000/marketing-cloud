package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Yunfeng on 2016-6-17.
 */
public class PublicAuthOut {
    private String url;

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
