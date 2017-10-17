package cn.rongcapital.mkt.po.mongodb;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "segment")
public class Segment implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	@Field(value = "segmentation_head_id")
	private Integer segmentationHeadId;
	
	@Field(value = "data_id")
	private Integer dataId;
	
	@Field(value = "md_type")
	private Integer mdType;
	
	@Field(value = "name")
	private String name;
	
	@Field(value = "mapping_keyid")
	private String mappingKeyid;
	
	//调用大连发送公众号图文接口中，发送方的公众号pubId
//	private transient String pubId;
	
	//发送的微信h5手机图文地址
	private transient String h5MobileUrl;
	
	//粉丝/好友的微信open_id
	private transient String fansFriendsOpenId;
	
	//微信图文的id
//	private transient Integer materialId;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getSegmentationHeadId() {
		return segmentationHeadId;
	}

	public void setSegmentationHeadId(Integer segmentationHeadId) {
		this.segmentationHeadId = segmentationHeadId;
	}

	public Integer getDataId() {
		return dataId;
	}

	public void setDataId(Integer dataId) {
		this.dataId = dataId;
	}

	public String getH5MobileUrl() {
		return h5MobileUrl;
	}

	public void setH5MobileUrl(String h5MobileUrl) {
		this.h5MobileUrl = h5MobileUrl;
	}

	public String getFansFriendsOpenId() {
		return fansFriendsOpenId;
	}

	public void setFansFriendsOpenId(String fansFriendsOpenId) {
		this.fansFriendsOpenId = fansFriendsOpenId;
	}

	public Integer getMdType() {
		return mdType;
	}

	public void setMdType(Integer mdType) {
		this.mdType = mdType;
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
	
}
