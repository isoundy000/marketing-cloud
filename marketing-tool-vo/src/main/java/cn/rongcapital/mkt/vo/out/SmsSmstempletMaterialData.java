package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

public class SmsSmstempletMaterialData {

	private String smsVariableValue;
	
	private Integer materialId;
	
	private String materialType;
	
	private String materialName;
	
	private Integer materialPropertyId;
	
	private String materialPropertyCode;
	
	private String materialPropertyName;

	@JsonProperty("sms_variable_value")
	public String getSmsVariableValue() {
		return smsVariableValue;
	}

	public void setSmsVariableValue(String smsVariableValue) {
		this.smsVariableValue = smsVariableValue;
	}

	@JsonProperty("material_id")
	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	@JsonProperty("material_type")
	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}

	@JsonProperty("material_name")
	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	@JsonProperty("material_property_id")
	public Integer getMaterialPropertyId() {
		return materialPropertyId;
	}

	public void setMaterialPropertyId(Integer materialPropertyId) {
		this.materialPropertyId = materialPropertyId;
	}

	@JsonProperty("material_property_code")
	public String getMaterialPropertyCode() {
		return materialPropertyCode;
	}

	public void setMaterialPropertyCode(String materialPropertyCode) {
		this.materialPropertyCode = materialPropertyCode;
	}

	@JsonProperty("material_property_name")
	public String getMaterialPropertyName() {
		return materialPropertyName;
	}

	public void setMaterialPropertyName(String materialPropertyName) {
		this.materialPropertyName = materialPropertyName;
	}
	
}
