package cn.rongcapital.mkt.vo.out;

import java.util.List;
import java.util.Map;

public class Field_List {
	private String field_name;
	private String field_code;
	private String selected;
	private String index;
	private Integer field_type;
	private Integer required;
	private Integer ischecked;
	private List<Map<String, Object>> select_data;


	public List<Map<String, Object>> getSelect_data() {
		return select_data;
	}

	public void setSelect_data(List<Map<String, Object>> select_data) {
		this.select_data = select_data;
	}

	public String getField_name() {
		return field_name;
	}

	public void setField_name(String field_name) {
		this.field_name = field_name;
	}

	public String getField_code() {
		return field_code;
	}

	public void setField_code(String field_code) {
		this.field_code = field_code;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public Integer getRequired() {
		return required;
	}

	public void setRequired(Integer required) {
		this.required = required;
	}

	public Integer getIschecked() {
		return ischecked;
	}

	public void setIschecked(Integer ischecked) {
		this.ischecked = ischecked;
	}

	public Integer getField_type() {
		return field_type;
	}

	public void setField_type(Integer field_type) {
		this.field_type = field_type;
	}

}
