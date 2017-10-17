package cn.rongcapital.mkt.vo.out;


import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class CampaignDecisionTagOut {
	
    private String name;

    private Byte rule;

    private List<TagOut> tags; 
    
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    @JsonProperty("rule")
    public Byte getRule() {
        return rule;
    }

    public void setRule(Byte rule) {
        this.rule = rule;
    }

    @JsonProperty("tags")
	public List<TagOut> getTags() {
		return tags;
	}

	public void setTags(List<TagOut> tags) {
		this.tags = tags;
	}
    
}
