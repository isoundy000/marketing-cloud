package cn.rongcapital.mkt.job.vo.in;

/**
 * Created by Yunfeng on 2016-6-17.
 */
public class H5Pub {
    private String pubId;
    private String pubName;
    private String headUrl;
    private int pubType;

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

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public int getPubType() {
        return pubType;
    }

    public void setPubType(int pubType) {
        this.pubType = pubType;
    }
}
