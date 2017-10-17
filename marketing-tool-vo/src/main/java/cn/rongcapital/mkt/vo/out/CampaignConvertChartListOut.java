package cn.rongcapital.mkt.vo.out;

import cn.rongcapital.mkt.vo.BaseOutput;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yunfeng on 2016-7-25.
 */
public class CampaignConvertChartListOut extends BaseOutput{

    private List<CampaignConvertChartListData> dataCustom = new ArrayList<CampaignConvertChartListData>();

    public CampaignConvertChartListOut(int code, String msg, int total) {
        super(code, msg, total, null);
    }

    @JsonProperty("data")
    public List<CampaignConvertChartListData> getDataCustom() {
        return dataCustom;
    }

    public void setDataCustom(List<CampaignConvertChartListData> dataCustom) {
        this.dataCustom = dataCustom;
    }

}
