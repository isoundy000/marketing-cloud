package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

import java.math.BigDecimal;
import java.util.Date;

public class PartyRadar extends BaseQuery {
	
    private String contactId;

    private String contactName;

    private Date recentBuyTime;

    private BigDecimal buyRate;

    private Integer goodsTypes;

    private BigDecimal averageTransAmt;

    private BigDecimal topTransAmt;

	public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId == null ? null : contactId.trim();
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName == null ? null : contactName.trim();
    }

    public Date getRecentBuyTime() {
        return recentBuyTime;
    }

    public void setRecentBuyTime(Date recentBuyTime) {
        this.recentBuyTime = recentBuyTime;
    }

    public BigDecimal getBuyRate() {
        return buyRate;
    }

    public void setBuyRate(BigDecimal buyRate) {
        this.buyRate = buyRate;
    }

    public Integer getGoodsTypes() {
        return goodsTypes;
    }

    public void setGoodsTypes(Integer goodsTypes) {
        this.goodsTypes = goodsTypes;
    }

    public BigDecimal getAverageTransAmt() {
        return averageTransAmt;
    }

    public void setAverageTransAmt(BigDecimal averageTransAmt) {
        this.averageTransAmt = averageTransAmt;
    }

    public BigDecimal getTopTransAmt() {
        return topTransAmt;
    }

    public void setTopTransAmt(BigDecimal topTransAmt) {
        this.topTransAmt = topTransAmt;
    }
}
