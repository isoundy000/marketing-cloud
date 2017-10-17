package cn.rongcapital.mkt.vo.out;

import cn.rongcapital.mkt.vo.BaseOutput;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yunfeng on 2016-8-15.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class GetContactKeyListOutput extends BaseOutput {

    private String keyList;
    private String contactName;
    private String contactTitle;
    private String contactDescription;
    private Integer status;
    private List<ContactKeyOutput> dataKeyList = new ArrayList<ContactKeyOutput>();

    public GetContactKeyListOutput(int code, String msg, int total) {
        super(code, msg, total, null);
    }

    @JsonProperty("key_list")
    public String getKeyList() {
        return keyList;
    }

    public void setKeyList(String keyList) {
        this.keyList = keyList;
    }

    @JsonProperty("contact_name")
    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    @JsonProperty("contact_title")
    public String getContactTitle() {
        return contactTitle;
    }

    public void setContactTitle(String contactTitle) {
        this.contactTitle = contactTitle;
    }

    @JsonProperty("contact_descript")
    public String getContactDescription() {
        return contactDescription;
    }

    public void setContactDescription(String contactDescription) {
        this.contactDescription = contactDescription;
    }

    @JsonProperty("contact_status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @JsonProperty("data")
    public List<ContactKeyOutput> getDataKeyList() {
        return dataKeyList;
    }

    public void setDataKeyList(List<ContactKeyOutput> dataKeyList) {
        this.dataKeyList = dataKeyList;
    }

}
