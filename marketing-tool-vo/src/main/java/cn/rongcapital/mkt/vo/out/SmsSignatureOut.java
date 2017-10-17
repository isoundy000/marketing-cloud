package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by byf on 10/18/16.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class SmsSignatureOut{

    private Long id;
    private String signatrueName;

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("signature_name")
    public String getSignatrueName() {
        return signatrueName;
    }

    public void setSignatrueName(String signatrueName) {
        this.signatrueName = signatrueName;
    }
}
