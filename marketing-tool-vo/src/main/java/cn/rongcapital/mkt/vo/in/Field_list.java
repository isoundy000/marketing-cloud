package cn.rongcapital.mkt.vo.in;

import javax.validation.constraints.NotNull;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;


@JsonIgnoreProperties(ignoreUnknown=true)
public class Field_list {

	@NotEmpty
	@JsonProperty("field_name")
	private String field_name;

	@NotEmpty
	@JsonProperty("field_code")
	private String field_code;

	@NotEmpty
	@JsonProperty("selected")
	private String selected;
	
	@NotNull
	@JsonProperty("index")
	private int index;

	@NotNull
	@JsonProperty("required")
	private int required;

	@NotNull
	@JsonProperty("ischecked")
	private int ischecked;
	
	@NotNull
	@JsonProperty("field_type")
	private int field_type;

	public void setField_name(String field_name){
	this.field_name = field_name;
	}
	public String getField_name(){
	return this.field_name;
	}
	public void setField_code(String field_code){
	this.field_code = field_code;
	}
	public String getField_code(){
	return this.field_code;
	}
	public void setSelected(String selected){
	this.selected = selected;
	}
	public String getSelected(){
	return this.selected;
	}
	public void setIndex(int index){
	this.index = index;
	}
	public int getIndex(){
	return this.index;
	}
	public void setRequired(int required){
	this.required = required;
	}
	public int getRequired(){
	return this.required;
	}
	public void setIschecked(int ischecked){
	this.ischecked = ischecked;
	}
	public int getIschecked(){
	return this.ischecked;
	}
	public int getField_type() {
		return field_type;
	}
	public void setField_type(int field_type) {
		this.field_type = field_type;
	}
	
	

}
