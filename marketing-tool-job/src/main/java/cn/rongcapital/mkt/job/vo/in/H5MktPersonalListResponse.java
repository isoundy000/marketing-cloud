package cn.rongcapital.mkt.job.vo.in;

/**
 * Created by Yunfeng on 2016-6-17.
 */
public class H5MktPersonalListResponse {
    private String total;
    private H5Personals personals;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public H5Personals getPersonals() {
        return personals;
    }

    public void setPersonals(H5Personals personals) {
        this.personals = personals;
    }
}
