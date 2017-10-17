package cn.rongcapital.mkt.vo.out;

import cn.rongcapital.mkt.vo.BaseOutput;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by byf on 10/18/16.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class SmsSignatureListOut extends BaseOutput {
    List<SmsSignatureOut> smsSignatureOutList = new ArrayList<>();

    public SmsSignatureListOut(int code, String msg, int total) {
        super(code, msg, total, null);
    }

    @JsonProperty("data")
    public List<SmsSignatureOut> getSmsSignatureOutList() {
        return smsSignatureOutList;
    }

    public void setSmsSignatureOutList(List<SmsSignatureOut> smsSignatureOutList) {
        this.smsSignatureOutList = smsSignatureOutList;
    }
}
