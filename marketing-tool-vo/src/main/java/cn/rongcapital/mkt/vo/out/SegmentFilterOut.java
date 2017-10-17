package cn.rongcapital.mkt.vo.out;

import cn.rongcapital.mkt.vo.BaseOutput;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by byf on 11/14/16.
 */
public class SegmentFilterOut extends BaseOutput{

    private Long segmentTotal;

    private List<TagGroupChartOut> tagGroupChartOutList = new ArrayList<TagGroupChartOut>();

    public SegmentFilterOut(int code, String msg, int total){
        super(code, msg, total, null);
    }

    @JsonProperty("data")
    public List<TagGroupChartOut> getTagGroupChartOutList() {
        return tagGroupChartOutList;
    }

    public void setTagGroupChartOutList(List<TagGroupChartOut> tagGroupChartOutList) {
        this.tagGroupChartOutList = tagGroupChartOutList;
    }

    @JsonProperty("segment_total")
    public Long getSegmentTotal() {
        return segmentTotal;
    }

    public void setSegmentTotal(Long segmentTotal) {
        this.segmentTotal = segmentTotal;
    }
}
