package cn.rongcapital.mkt.vo.out;

import cn.rongcapital.mkt.vo.BaseOutput;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by byf on 10/18/16.
 */
public class SmsTargetAudienceListOut extends BaseOutput{

    List<SmsTargetAudienceOut> smsTargetAudienceOutList = new ArrayList<>();

    public SmsTargetAudienceListOut(int code, String msg, int total) {
        super(code, msg, total, null);
    }

    @JsonProperty("data")
    public List<SmsTargetAudienceOut> getSmsTargetAudienceOutList() {
        return smsTargetAudienceOutList;
    }

    public void setSmsTargetAudienceOutList(List<SmsTargetAudienceOut> smsTargetAudienceOutList) {
        this.smsTargetAudienceOutList = smsTargetAudienceOutList;
    }
}
