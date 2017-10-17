package cn.rongcapital.mkt.job.vo.in;

/**
 * Created by Yunfeng on 2016-6-17.
 */
public class H5MktPubFansListResponse {
    private String message;
    private long total;
    private H5PubFanList fans;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public H5PubFanList getFans() {
        return fans;
    }

    public void setFans(H5PubFanList fans) {
        this.fans = fans;
    }
}
