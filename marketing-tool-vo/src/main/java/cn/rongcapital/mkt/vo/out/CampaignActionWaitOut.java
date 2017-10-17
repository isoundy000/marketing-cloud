package cn.rongcapital.mkt.vo.out;


import org.codehaus.jackson.annotate.JsonProperty;

public class CampaignActionWaitOut {
	
    private String name;

    private Byte type;

    private Integer relativeValue;

    private Byte relativeType;

    private String specificTime;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    @JsonProperty("type")
    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    @JsonProperty("relative_value")
    public Integer getRelativeValue() {
        return relativeValue;
    }

    public void setRelativeValue(Integer relativeValue) {
        this.relativeValue = relativeValue;
    }

    @JsonProperty("relative_type")
    public Byte getRelativeType() {
        return relativeType;
    }

    public void setRelativeType(Byte relativeType) {
        this.relativeType = relativeType;
    }

    @JsonProperty("specific_time")
    public String getSpecificTime() {
        return specificTime;
    }

    public void setSpecificTime(String specificTime) {
        this.specificTime = specificTime;
    }
}
