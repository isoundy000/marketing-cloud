package cn.rongcapital.mkt.vo.out;

import java.util.List;

/**
 * Created by ethan on 16/7/26.
 */
public class CampaignAnalysisDimensionSpanOut {

    private String dimension;

    private List<CampaignAnalysisTimeSpanOut> dimensionList;

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public List<CampaignAnalysisTimeSpanOut> getDimensionList() {
        return dimensionList;
    }

    public void setDimensionList(List<CampaignAnalysisTimeSpanOut> dimensionList) {
        this.dimensionList = dimensionList;
    }
}
