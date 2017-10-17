/*************************************************
* @功能及特点的描述简述: 优惠券编辑回显
* 该类被编译测试过
* @see （与该类关联的类）：
* @对应项目名称：MC
* @author: liuhaizhan
* @version: 版本
* @date(创建、开发日期)：2016年12月9日
* 最后修改日期：2016年12月9日
* @复审人:
*************************************************/
package cn.rongcapital.mkt.material.coupon.vo.out;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CouPonEditInfoOut {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("source_code")
    private String sourceCode;
    @JsonProperty("rule")
    private Object rlObject;
    @JsonProperty("stock_total")
    private Integer stockTotal;
    @JsonProperty("amount")
    private double amount;
    @JsonProperty("channel_code")
    private String channelCode;
    @JsonProperty("start_time")
    private Long startTime;
    @JsonProperty("end_time")
    private Long endTime;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getSourceCode() {
        return sourceCode;
    }
    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }
    
    public Object getRlObject() {
        return rlObject;
    }
    public void setRlObject(Object rlObject) {
        this.rlObject = rlObject;
    }
    public Integer getStockTotal() {
        return stockTotal;
    }
    public void setStockTotal(Integer stockTotal) {
        this.stockTotal = stockTotal;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public String getChannelCode() {
        return channelCode;
    }
    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }
    public Long getStartTime() {
        return startTime;
    }
    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }
    public Long getEndTime() {
        return endTime;
    }
    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

   
}
