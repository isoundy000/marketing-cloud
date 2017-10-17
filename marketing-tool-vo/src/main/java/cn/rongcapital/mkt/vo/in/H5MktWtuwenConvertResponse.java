package cn.rongcapital.mkt.vo.in;

/**
 * Created by Yunfeng on 2016-6-20.
 */
public class H5MktWtuwenConvertResponse {
    private String pcUrl;
    private String mobileUrl;
    private String screenshopUrl;
    private String title;
    private String createTime;
    private int channelType;
    private int materialId;

    public String getPcUrl() {
        return pcUrl;
    }

    public void setPcUrl(String pcUrl) {
        this.pcUrl = pcUrl;
    }

    public String getMobileUrl() {
        return mobileUrl;
    }

    public void setMobileUrl(String mobileUrl) {
        this.mobileUrl = mobileUrl;
    }

    public String getScreenshopUrl() {
        return screenshopUrl;
    }

    public void setScreenshopUrl(String screenshopUrl) {
        this.screenshopUrl = screenshopUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getChannelType() {
        return channelType;
    }

    public void setChannelType(int channelType) {
        this.channelType = channelType;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }
}
