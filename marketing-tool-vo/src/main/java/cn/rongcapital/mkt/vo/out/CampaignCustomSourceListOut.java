package cn.rongcapital.mkt.vo.out;

import cn.rongcapital.mkt.vo.BaseOutput;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yunfeng on 2016-7-25.
 */
public class CampaignCustomSourceListOut extends BaseOutput{

    private List<CampaignCustomSourceData> dataCustom = new ArrayList<CampaignCustomSourceData>();

    public CampaignCustomSourceListOut(int code, String msg, int total) {
        super(code, msg, total, null);
    }

    @JsonProperty("data")
    public List<CampaignCustomSourceData> getDataCustom() {
        return dataCustom;
    }

    public void setDataCustom(List<CampaignCustomSourceData> dataCustom) {
        this.dataCustom = dataCustom;
    }
}
