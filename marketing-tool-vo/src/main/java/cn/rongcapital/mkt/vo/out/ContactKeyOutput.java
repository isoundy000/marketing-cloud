package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Yunfeng on 2016-8-15.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class ContactKeyOutput {

    private String fieldName;
    private String fieldCode;
    private Integer selected;
    private Integer index;
    private Integer fixedIndex;
    private Integer required;
    private Integer isChecked;
    private Integer fieldType;

    @JsonProperty("field_name")
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @JsonProperty("field_code")
    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    @JsonProperty("selected")
    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }

    @JsonProperty("index")
    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    @JsonProperty("fixed_index")
    public Integer getFixedIndex() {
        return fixedIndex;
    }

    public void setFixedIndex(Integer fixedIndex) {
        this.fixedIndex = fixedIndex;
    }

    @JsonProperty("required")
    public Integer getRequired() {
        return required;
    }

    public void setRequired(Integer required) {
        this.required = required;
    }

    @JsonProperty("ischecked")
    public Integer getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Integer isChecked) {
        this.isChecked = isChecked;
    }

    @JsonProperty("field_type")
    public Integer getFieldType() {
        return fieldType;
    }

    public void setFieldType(Integer fieldType) {
        this.fieldType = fieldType;
    }
}
