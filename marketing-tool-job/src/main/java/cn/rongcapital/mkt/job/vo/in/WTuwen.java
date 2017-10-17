package cn.rongcapital.mkt.job.vo.in;

/**
 * Created by Yunfeng on 2016-6-21.
 */
public class WTuwen {
    private Integer materialId;
    private String pubId;
    private String pubName;
    private String createTime;
    private Integer channelType;
    private Integer status;
    private MaterialContents materialContents;

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public String getPubId() {
        return pubId;
    }

    public void setPubId(String pubId) {
        this.pubId = pubId;
    }

    public String getPubName() {
        return pubName;
    }

    public void setPubName(String pubName) {
        this.pubName = pubName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getChannelType() {
        return channelType;
    }

    public void setChannelType(Integer channelType) {
        this.channelType = channelType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public MaterialContents getMaterialContents() {
        return materialContents;
    }

    public void setMaterialContents(MaterialContents materialContents) {
        this.materialContents = materialContents;
    }
}
