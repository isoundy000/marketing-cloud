package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

public class CampaignDecisionWechatReadOut {
	
    private String name;

    private Integer assetId;

    private String assetName;

    private Integer refreshInterval;

    private Byte refreshIntervalType;

    private Integer imgTextAssetId;

    private String imgTextAssetName;

    private Byte readTime;

    private Byte readPercent;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    @JsonProperty("refresh_interval")
    public Integer getRefreshInterval() {
        return refreshInterval;
    }

    public void setRefreshInterval(Integer refreshInterval) {
        this.refreshInterval = refreshInterval;
    }

    @JsonProperty("refresh_interval_type")
    public Byte getRefreshIntervalType() {
        return refreshIntervalType;
    }

    public void setRefreshIntervalType(Byte refreshIntervalType) {
        this.refreshIntervalType = refreshIntervalType;
    }

    @JsonProperty("read_time")
    public Byte getReadTime() {
        return readTime;
    }

    public void setReadTime(Byte readTime) {
        this.readTime = readTime;
    }

    @JsonProperty("read_percent")
    public Byte getReadPercent() {
        return readPercent;
    }

    public void setReadPercent(Byte readPercent) {
        this.readPercent = readPercent;
    }

    @JsonProperty("asset_id")
	public Integer getAssetId() {
		return assetId;
	}

	public void setAssetId(Integer assetId) {
		this.assetId = assetId;
	}

	@JsonProperty("asset_name")
	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	@JsonProperty("img_text_asset_id")
	public Integer getImgTextAssetId() {
		return imgTextAssetId;
	}

	public void setImgTextAssetId(Integer imgTextAssetId) {
		this.imgTextAssetId = imgTextAssetId;
	}

	@JsonProperty("img_text_asset_name")
	public String getImgTextAssetName() {
		return imgTextAssetName;
	}

	public void setImgTextAssetName(String imgTextAssetName) {
		this.imgTextAssetName = imgTextAssetName;
	}

}
