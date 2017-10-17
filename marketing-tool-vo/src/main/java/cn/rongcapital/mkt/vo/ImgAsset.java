package cn.rongcapital.mkt.vo;


import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by Yunfeng on 2016-5-27.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class ImgAsset extends BaseInput{
    private int imgtextId;
    private int assetType;
    private String ownerName;
    private String wxType;
    private String searchKey;
    private String pubId;
    
    @JsonProperty("imgtext_id")
    public int getImgtextId() {
        return imgtextId;
    }

    public void setImgtextId(int imgtextId) {
        this.imgtextId = imgtextId;
    }

    @JsonProperty("owner_name")
    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    @JsonProperty("type")
    public int getAssetType() {
        return assetType;
    }

    public void setAssetType(int assetType) {
        this.assetType = assetType;
    }
    @JsonProperty("wx_type")
	public String getWxType() {
		return wxType;
	}

	public void setWxType(String wxType) {
		this.wxType = wxType;
	}
    @JsonProperty("search_key")
	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
    @JsonProperty("pub_id")
	public String getPubId() {
		return pubId;
	}

	public void setPubId(String pubId) {
		this.pubId = pubId;
	}
    
    
}
