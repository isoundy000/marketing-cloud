package cn.rongcapital.mkt.common.enums;

public enum CampaignHeadStatusEnum {

    CAMPAIGN_PUBLISH_STATUS_NOT_PUBLISH((byte) 0, "未发布"), 
    CAMPAIGN_PUBLISH_STATUS_PUBLISH((byte) 1, "已发布"), 
    CAMPAIGN_PUBLISH_STATUS_IN_PROGRESS((byte) 2, "活动中"), 
    CAMPAIGN_PUBLISH_STATUS_FINISH((byte) 3, "已结束"), 
    CAMPAIGN_PUBLISH_STATUS_ALL((byte) 4, "全部活动"),

    ;

    private CampaignHeadStatusEnum(byte code, String status) {
        this.code = code;
        this.status = status;
    }

    private byte code;

    private String status;

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static String getStatusByCode(byte code) {
        CampaignHeadStatusEnum[] campaignHeadStatusEnums = CampaignHeadStatusEnum.values();
        for (CampaignHeadStatusEnum campaignHeadStatusEnum : campaignHeadStatusEnums) {
            if (campaignHeadStatusEnum.getCode() == code) {
                return campaignHeadStatusEnum.getStatus();
            }
        }

        return "";
    }
}
