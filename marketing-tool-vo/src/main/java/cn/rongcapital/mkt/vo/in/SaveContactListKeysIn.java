package cn.rongcapital.mkt.vo.in;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

/**
 * Created by Yunfeng on 2016-8-19.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class SaveContactListKeysIn {

    @NotNull
    private Long contactId;
    @NotNull
    private Integer saveFlag;
    @NotEmpty
    private ArrayList<ContactListKeyAttribute> fieldNameList;

    @JsonProperty("contact_id")
    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    @JsonProperty("save_flag")
    public Integer getSaveFlag() {
        return saveFlag;
    }

    public void setSaveFlag(Integer saveFlag) {
        this.saveFlag = saveFlag;
    }

    @JsonProperty("field_list")
    public ArrayList<ContactListKeyAttribute> getFieldNameList() {
        return fieldNameList;
    }

    public void setFieldNameList(ArrayList<ContactListKeyAttribute> fieldNameList) {
        this.fieldNameList = fieldNameList;
    }
}
