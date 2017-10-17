package cn.rongcapital.mkt.common.enums;

public enum CampaignAnalyseTypeEnum {

    CAMPAIGN_READ(0, "阅读人数"),
    CAMPAIGN_ORIGIN_READ(1, "原文阅读人数"),
    CAMPAIGN_SHARE(2, "分享人数"),
    CAMPAIGN_FAVOR(3, "收藏人数");


    CampaignAnalyseTypeEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    private Integer code;

    private String description;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
