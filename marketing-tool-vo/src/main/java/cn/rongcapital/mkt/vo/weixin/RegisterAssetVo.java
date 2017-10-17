package cn.rongcapital.mkt.vo.weixin;

public class RegisterAssetVo {

	private Integer asset_id;
	
	private String asset_name;
	
	private String wx_acct;
	
	private String headerImage;

	public Integer getAsset_id() {
		return asset_id;
	}

	public void setAsset_id(Integer asset_id) {
		this.asset_id = asset_id;
	}

	public String getAsset_name() {
		return asset_name;
	}

	public void setAsset_name(String asset_name) {
		this.asset_name = asset_name;
	}

	public String getWx_acct() {
		return wx_acct;
	}

	public void setWx_acct(String wx_acct) {
		this.wx_acct = wx_acct;
	}

	public String getHeaderImage() {
		return headerImage;
	}

	public void setHeaderImage(String headerImage) {
		this.headerImage = headerImage;
	}
		
}
