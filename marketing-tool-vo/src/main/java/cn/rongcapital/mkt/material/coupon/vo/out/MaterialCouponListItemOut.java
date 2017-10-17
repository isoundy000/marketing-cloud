/*************************************************
 * @功能及特点的描述简述: 优惠券列表查询结果Item类
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.6
 * @date(创建、开发日期)：2016-12-13 
 * @date(最后修改日期)：2016-12-13 
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.material.coupon.vo.out;

import java.math.BigDecimal;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MaterialCouponListItemOut {
    private Long id;
    private String couponStatus;
    private String title;
    private String createTime;
    private Integer stockTotal;
    private Integer stockRest;
    private BigDecimal amount;
    private String channelCode;
    private String readyStatus;
    
    /**
     * @return the id
     */
    @JsonProperty("id")
    public Long getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * @return the couponStatus
     */
    @JsonProperty("coupono_status")
    public String getCouponStatus() {
        return couponStatus;
    }
    /**
     * @param couponStatus the couponStatus to set
     */
    public void setCouponStatus(String couponStatus) {
        this.couponStatus = couponStatus;
    }
    /**
     * @return the title
     */
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }
    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * @return the createTime
     */
    @JsonProperty("create_time")
    public String getCreateTime() {
        return createTime;
    }
    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    
    /**
     * @return the stockTotal
     */
    @JsonProperty("stock_total")
    public Integer getStockTotal() {
        return stockTotal;
    }
    /**
     * @param stockTotal the stockTotal to set
     */
    public void setStockTotal(Integer stockTotal) {
        this.stockTotal = stockTotal;
    }
    /**
     * @return the stockRest
     */
    @JsonProperty("stock_rest")
    public Integer getStockRest() {
        return stockRest;
    }
    /**
     * @param stockRest the stockRest to set
     */
    public void setStockRest(Integer stockRest) {
        this.stockRest = stockRest;
    }
    
    /**
     * @return the amount
     */
    @JsonProperty("amount")
    public BigDecimal getAmount() {
        return amount;
    }
   
    /**
     * @param amount the amount to set
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    /**
     * @return the chanelCode
     */
    @JsonProperty("channel_code")
    public String getChannelCode() {
        return channelCode;
    }
    /**
     * @param channelCode the chanelCode to set
     */
    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }
	public String getReadyStatus() {
		return readyStatus;
	}
	@JsonProperty("ready_status")
	public void setReadyStatus(String readyStatus) {
		this.readyStatus = readyStatus;
	}
}
