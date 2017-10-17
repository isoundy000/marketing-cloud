package cn.rongcapital.mkt.vo.in;

import cn.rongcapital.mkt.vo.BaseInput;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Yunfeng on 2016-6-23.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class ReauthWechatAccountIn extends BaseInput{

    private String assetId;

    @JsonProperty("asset_id")
    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }
}
