package cn.rongcapital.mkt.vo.out;


import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class CampaignActionSetTagOut {
	
    private String name;

    private List<String> tagNames;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    @JsonProperty("tag_names")
	public List<String> getTagNames() {
		return tagNames;
	}

	public void setTagNames(List<String> tagNames) {
		this.tagNames = tagNames;
	}

}
