package cn.rongcapital.mkt.vo.in;

import java.util.List;

import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class CampaignNodeChainIn {
	
	@NotEmpty
	private String itemId;
	private byte itemType;
	private byte nodeType;
	@NotEmpty
	private String posX;//x坐标
	@NotEmpty
	private String posY;//y坐标
	@NotEmpty
	private String posZ;//z坐标
	
	@NotEmpty
	private String icon;//icon
	
	@NotEmpty
	private String code;//code
	
	private String desc;//节点描述,对应campaign_body表里的description
	
	private List<CampaignSwitchIn> campaignSwitchList;//判断分支:switch
	
	private List<CampaignSwitchIn> campaignEndsList;//多分支:ends
	
	private Map<String,Object> info;//节点配置的属性

	@JsonProperty("item_id")
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	@JsonProperty("item_type")
	public byte getItemType() {
		return itemType;
	}

	public void setItemType(byte itemType) {
		this.itemType = itemType;
	}

	@JsonProperty("node_type")
	public byte getNodeType() {
		return nodeType;
	}

	public void setNodeType(byte nodeType) {
		this.nodeType = nodeType;
	}

	@JsonProperty("x")
	public String getPosX() {
		return posX;
	}

	public void setPosX(String posX) {
		this.posX = posX;
	}

	@JsonProperty("y")
	public String getPosY() {
		return posY;
	}

	public void setPosY(String posY) {
		this.posY = posY;
	}

	@JsonProperty("z")
	public String getPosZ() {
		return posZ;
	}

	public void setPosZ(String posZ) {
		this.posZ = posZ;
	}

	@JsonProperty("ends")
	public List<CampaignSwitchIn> getCampaignEndsList() {
		return campaignEndsList;
	}

	public void setCampaignEndsList(List<CampaignSwitchIn> campaignEndsList) {
		this.campaignEndsList = campaignEndsList;
	}

	@JsonProperty("switch")
	public List<CampaignSwitchIn> getCampaignSwitchList() {
		return campaignSwitchList;
	}
	
	public void setCampaignSwitchList(List<CampaignSwitchIn> campaignSwitchList) {
		this.campaignSwitchList = campaignSwitchList;
	}

	@JsonProperty("info")
	public Map<String,Object> getInfo() {
		return info;
	}

	public void setInfo(Map<String,Object> info) {
		this.info = info;
	}

	@JsonProperty("icon")
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@JsonProperty("code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@JsonProperty("desc")
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
