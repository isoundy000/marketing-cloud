package cn.rongcapital.mkt.job.vo.in;

/**
 * Created by Yunfeng on 2016-7-11.
 */
public class MaterialContent {
    private String mobileUrl;
    private Integer showCoverPic;
    private String screenshotUrl;
    private String title;
    private String pcUrl;
    private Integer thumbReady;

    public String getMobileUrl() {
        return mobileUrl;
    }

    public void setMobileUrl(String mobileUrl) {
        this.mobileUrl = mobileUrl;
    }

    public Integer getShowCoverPic() {
        return showCoverPic;
    }

    public void setShowCoverPic(Integer showCoverPic) {
        this.showCoverPic = showCoverPic;
    }

    public String getScreenshotUrl() {
        return screenshotUrl;
    }

    public void setScreenshotUrl(String screenshotUrl) {
        this.screenshotUrl = screenshotUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPcUrl() {
        return pcUrl;
    }

    public void setPcUrl(String pcUrl) {
        this.pcUrl = pcUrl;
    }

    public Integer getThumbReady() {
        return thumbReady;
    }

    public void setThumbReady(Integer thumbReady) {
        this.thumbReady = thumbReady;
    }
}
