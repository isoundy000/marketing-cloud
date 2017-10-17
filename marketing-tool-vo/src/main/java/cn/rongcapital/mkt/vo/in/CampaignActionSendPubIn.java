package cn.rongcapital.mkt.vo.in;


import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class CampaignActionSendPubIn {
	
    private String name;

    
    private Integer assetId;
    
    
    private Integer imgTextAssetId;
    
    @JsonProperty("name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("asset_id")
	public Integer getAssetId() {
		return assetId;
	}

	public void setAssetId(Integer assetId) {
		this.assetId = assetId;
	}

	@JsonProperty("img_text_asset_id")
	public Integer getImgTextAssetId() {
		return imgTextAssetId;
	}

	public void setImgTextAssetId(Integer imgTextAssetId) {
		this.imgTextAssetId = imgTextAssetId;
	}

}
