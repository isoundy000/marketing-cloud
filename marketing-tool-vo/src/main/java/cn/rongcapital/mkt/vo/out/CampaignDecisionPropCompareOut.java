package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

public class CampaignDecisionPropCompareOut {
	
    private String name;

    private Byte propType;

    private Byte rule;
    
    private String ruleValue;
    
    private Byte exclude;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    @JsonProperty("prop_type")
    public Byte getPropType() {
        return propType;
    }

    public void setPropType(Byte propType) {
        this.propType = propType;
    }

    @JsonProperty("rule")
    public Byte getRule() {
        return rule;
    }

    public void setRule(Byte rule) {
        this.rule = rule;
    }

    @JsonProperty("rule_value")
	public String getRuleValue() {
		return ruleValue;
	}

	public void setRuleValue(String ruleValue) {
		this.ruleValue = ruleValue;
	}

	@JsonProperty("exclude")
	public Byte getExclude() {
		return exclude;
	}

	public void setExclude(Byte exclude) {
		this.exclude = exclude;
	}

}
