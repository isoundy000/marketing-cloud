//package cn.rongcapital.mkt.vo.in;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.validation.constraints.NotNull;
//
//import org.codehaus.jackson.annotate.JsonIgnoreProperties;
//import org.codehaus.jackson.annotate.JsonProperty;
//import org.hibernate.validator.constraints.NotEmpty;
//
//@JsonIgnoreProperties(ignoreUnknown=true)
//public class CampaignNodeChain {
//	
//	@NotNull
//	private Integer campaignHeadId;
//	@NotEmpty
//	private String intemId;
//	private byte itemType;
//	private byte nodeType;
//	@NotEmpty
//	private String nextItemId;
//	private byte nextItemType;
//	private byte nextNodeType;
//	@NotEmpty
//	private String posX;//x坐标
//	@NotEmpty
//	private String poxY;
//	
//	private List<CampaignSwitchIn> campaignSwitchList = new ArrayList<CampaignSwitchIn>();
//	
//	private Object info;//节点配置的属性
//
//	@JsonProperty("campaign_head_id")
//	public Integer getCampaignHeadId() {
//		return campaignHeadId;
//	}
//
//	public void setCampaignHeadId(Integer campaignHeadId) {
//		this.campaignHeadId = campaignHeadId;
//	}
//
//	@JsonProperty("item_id")
//	public String getIntemId() {
//		return intemId;
//	}
//
//	public void setIntemId(String intemId) {
//		this.intemId = intemId;
//	}
//
//	@JsonProperty("item_type")
//	public byte getItemType() {
//		return itemType;
//	}
//
//	public void setItemType(byte itemType) {
//		this.itemType = itemType;
//	}
//
//	@JsonProperty("node_type")
//	public byte getNodeType() {
//		return nodeType;
//	}
//
//	public void setNodeType(byte nodeType) {
//		this.nodeType = nodeType;
//	}
//
//	@JsonProperty("x")
//	public String getPosX() {
//		return posX;
//	}
//
//	public void setPosX(String posX) {
//		this.posX = posX;
//	}
//
//	@JsonProperty("y")
//	public String getPoxY() {
//		return poxY;
//	}
//
//	public void setPoxY(String poxY) {
//		this.poxY = poxY;
//	}
//
//	@JsonProperty("switch")
//	public List<CampaignSwitchIn> getCampaignSwitchList() {
//		return campaignSwitchList;
//	}
//	
//	public void setCampaignSwitchList(List<CampaignSwitchIn> campaignSwitchList) {
//		this.campaignSwitchList = campaignSwitchList;
//	}
//
//	@JsonProperty("info")
//	public Object getInfo() {
//		return info;
//	}
//
//	public void setInfo(Object info) {
//		this.info = info;
//	}
//
//	@JsonProperty("next_item_id")
//	public String getNextItemId() {
//		return nextItemId;
//	}
//
//	public void setNextItemId(String nextItemId) {
//		this.nextItemId = nextItemId;
//	}
//
//	@JsonProperty("next_item_type")
//	public byte getNextItemType() {
//		return nextItemType;
//	}
//
//	public void setNextItemType(byte nextItemType) {
//		this.nextItemType = nextItemType;
//	}
//
//	@JsonProperty("next_node_type")
//	public byte getNextNodeType() {
//		return nextNodeType;
//	}
//
//	public void setNextNodeType(byte nextNodeType) {
//		this.nextNodeType = nextNodeType;
//	}
//
//	
//}
