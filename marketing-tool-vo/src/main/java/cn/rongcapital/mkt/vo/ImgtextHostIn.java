package cn.rongcapital.mkt.vo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Yunfeng on 2016-5-30.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class ImgtextHostIn extends BaseInput{

    private String assetUrl;

    @JsonProperty("asset_url")
    public String getAssetUrl() {
        return assetUrl;
    }

    public void setAssetUrl(String assetUrl) {
        this.assetUrl = assetUrl;
    }
}
