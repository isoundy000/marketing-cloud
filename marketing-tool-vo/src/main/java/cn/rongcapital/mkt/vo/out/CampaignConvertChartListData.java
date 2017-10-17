package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Yunfeng on 2016-7-25.
 */
public class CampaignConvertChartListData {

    private Integer peopleTotalCount;
    private Integer peopleCoverCount;

    @JsonProperty("people_total_count")
    public Integer getPeopleTotalCount() {
        return peopleTotalCount;
    }

    public void setPeopleTotalCount(Integer peopleTotalCount) {
        this.peopleTotalCount = peopleTotalCount;
    }

    @JsonProperty("people_cover_count")
    public Integer getPeopleCoverCount() {
        return peopleCoverCount;
    }

    public void setPeopleCoverCount(Integer peopleCoverCount) {
        this.peopleCoverCount = peopleCoverCount;
    }

}
