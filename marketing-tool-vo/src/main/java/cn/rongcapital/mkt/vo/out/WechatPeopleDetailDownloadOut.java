package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Yunfeng on 2016-6-29.
 */
public class WechatPeopleDetailDownloadOut {

    private String downloadFileName;
    private Long   totalCount;

    @JsonProperty("download_file_name")
    public String getDownloadFileName() {
        return downloadFileName;
    }

    public void setDownloadFileName(String downloadFileName) {
        this.downloadFileName = downloadFileName;
    }

    @JsonProperty("total_count")
    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }
}
