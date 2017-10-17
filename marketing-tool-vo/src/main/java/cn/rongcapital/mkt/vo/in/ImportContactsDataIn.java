package cn.rongcapital.mkt.vo.in;

import cn.rongcapital.mkt.vo.BaseInput;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by Yunfeng on 2016-8-22.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class ImportContactsDataIn extends BaseInput{

    @NotEmpty
    private String userToken;
    @NotNull
    private Long contactId;

    @JsonProperty("user_token")
    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    @JsonProperty("contact_id")
    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }
}
