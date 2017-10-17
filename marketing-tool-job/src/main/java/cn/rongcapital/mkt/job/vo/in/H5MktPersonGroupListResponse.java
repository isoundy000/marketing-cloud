package cn.rongcapital.mkt.job.vo.in;

/**
 * Created by Yunfeng on 2016-6-25.
 */
public class H5MktPersonGroupListResponse {
    private String message;
    private String total;
    private String uin;
    private H5PersonalGroups groups;

    public H5PersonalGroups getGroups() {
        return groups;
    }

    public void setGroups(H5PersonalGroups groups) {
        this.groups = groups;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getUin() {
        return uin;
    }

    public void setUin(String uin) {
        this.uin = uin;
    }
}
