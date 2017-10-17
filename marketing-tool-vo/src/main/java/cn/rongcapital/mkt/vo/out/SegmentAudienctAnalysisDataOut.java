package cn.rongcapital.mkt.vo.out;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class SegmentAudienctAnalysisDataOut {

	//显示类型 1:饼图 2:地图
	private String showType;
	
	//中国人口数量
    private Integer chinaPopulationCount;
    
    //其他及未知区域
    private Integer foreignPopulationCount;
    
    private List<SegmentAnalysisCountData> populationCount = new ArrayList<SegmentAnalysisCountData>();

	@JsonProperty("show_type")
	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}

	@JsonProperty("china_population_count")
	public Integer getChinaPopulationCount() {
		return chinaPopulationCount;
	}

	public void setChinaPopulationCount(Integer chinaPopulationCount) {
		this.chinaPopulationCount = chinaPopulationCount;
	}

	@JsonProperty("foreign_population_count")
	public Integer getForeignPopulationCount() {
		return foreignPopulationCount;
	}

	public void setForeignPopulationCount(Integer foreignPopulationCount) {
		this.foreignPopulationCount = foreignPopulationCount;
	}

	@JsonProperty("population_count")
	public List<SegmentAnalysisCountData> getPopulationCount() {
		return populationCount;
	}

	public void setPopulationCount(List<SegmentAnalysisCountData> populationCount) {
		this.populationCount = populationCount;
	}
	
    
}
