package cn.rongcapital.mkt.vo.sms.in;


import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;


@JsonIgnoreProperties(ignoreUnknown=true)
public class SmsTempletIn {

	private Integer id;
	
	@NotNull
    private Byte channelType;

	@NotNull
    private Byte type;

    private String auditor;

    private String creator;

    private String updateUser;

    @NotEmpty
    private String content;
    
    @NotEmpty
    private String name;

    private List<SmstempletMaterialVo> materialList = new ArrayList<SmstempletMaterialVo>();
    
    @JsonProperty("id")
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@JsonProperty("channel_type")
	public Byte getChannelType() {
		return channelType;
	}

	public void setChannelType(Byte channelType) {
		this.channelType = channelType;
	}

	@JsonProperty("type")
	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	@JsonProperty("auditor")
	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	@JsonProperty("creator")
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@JsonProperty("update_user")
	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	@JsonProperty("content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	@JsonProperty("name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("material_list")
	public List<SmstempletMaterialVo> getMaterialList() {
		return materialList;
	}

	public void setMaterialList(List<SmstempletMaterialVo> materialList) {
		this.materialList = materialList;
	}
    
}
