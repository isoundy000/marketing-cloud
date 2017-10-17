package cn.rongcapital.mkt.po.mongodb;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "node_audience")
public class NodeAudience implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	@Field(value = "campaign_head_id")
	private Integer campaignHeadId;
	
	@Field(value = "item_id")
	private String itemId;
	
	@Field(value = "data_id")
	private Integer dataId;
	
	private Integer taskId;

	@Field(value = "name")
	private String name;
	
	@Field(value = "mapping_keyid")
	private String mappingKeyid;
	
	//逻辑删除标记,0:未删除,1:已删除
	@Field(value = "status")
	private Integer status;
	
	//发送状态:0:未发送,1:公众号图文已发送
	@Field(value = "sent_status")
	private Integer sentStatus;
	
	@Field(value = "wx_code")
	private String wxCode;
	
	public Integer getSentStatus() {
		return sentStatus;
	}

	public void setSentStatus(Integer sentStatus) {
		this.sentStatus = sentStatus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getCampaignHeadId() {
		return campaignHeadId;
	}

	public void setCampaignHeadId(Integer campaignHeadId) {
		this.campaignHeadId = campaignHeadId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public Integer getDataId() {
		return dataId;
	}

	public void setDataId(Integer dataId) {
		this.dataId = dataId;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMappingKeyid() {
		return mappingKeyid;
	}

	public void setMappingKeyid(String mappingKeyid) {
		this.mappingKeyid = mappingKeyid;
	}

	public String getWxCode() {
		return wxCode;
	}

	public void setWxCode(String wxCode) {
		this.wxCode = wxCode;
	}
	
}
