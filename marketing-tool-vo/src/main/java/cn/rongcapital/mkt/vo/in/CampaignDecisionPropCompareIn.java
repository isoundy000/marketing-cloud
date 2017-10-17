package cn.rongcapital.mkt.vo.in;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class CampaignDecisionPropCompareIn {
	
    private Integer campaignHeadId;

    private String itemId;

    private String name;

    private Byte propType;

    private Byte rule;
    
    private String ruleValue;
    
    private Byte exlude;

    @JsonProperty("rule_value")
    public String getRuleValue() {
		return ruleValue;
	}

	public void setRuleValue(String ruleValue) {
		this.ruleValue = ruleValue;
	}

	@JsonProperty("exclude")
	public Byte getExlude() {
		return exlude;
	}

	public void setExlude(Byte exlude) {
		this.exlude = exlude;
	}

    @JsonProperty("campaign_head_id")
    public Integer getCampaignHeadId() {
        return campaignHeadId;
    }

    public void setCampaignHeadId(Integer campaignHeadId) {
        this.campaignHeadId = campaignHeadId;
    }

    @JsonProperty("item_id")
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId == null ? null : itemId.trim();
    }

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

}
