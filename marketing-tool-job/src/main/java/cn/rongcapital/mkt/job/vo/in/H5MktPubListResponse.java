package cn.rongcapital.mkt.job.vo.in;

/**
 * Created by Yunfeng on 2016-6-17.
 */
public class H5MktPubListResponse {
    private int total;
    private H5PubList pubs;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public H5PubList getPubs() {
        return pubs;
    }

    public void setPubs(H5PubList pubs) {
        this.pubs = pubs;
    }
}
