package cn.rongcapital.mkt.po;

/**
 * Created by Yunfeng on 2016-7-29.
 */
public class OrderCount {
    private String mobile;
    private Integer orderCount;
    private Integer dataPartyId;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public Integer getDataPartyId() {
        return dataPartyId;
    }

    public void setDataPartyId(Integer dataPartyId) {
        this.dataPartyId = dataPartyId;
    }
}
