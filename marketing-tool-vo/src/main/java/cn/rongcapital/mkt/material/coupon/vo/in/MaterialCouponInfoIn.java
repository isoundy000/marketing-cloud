package cn.rongcapital.mkt.material.coupon.vo.in;

import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import cn.rongcapital.mkt.vo.BaseInput;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class MaterialCouponInfoIn extends BaseInput{

    @JsonProperty("id")
    private Long id;
    @NotBlank
    @JsonProperty("title")
    private String title;
    @NotBlank
    @JsonProperty("source_code")
    private String source_code;
    @JsonProperty("rule")
    private String rule;
    @NotNull
    @JsonProperty("stock_total")
    @Min(1) @Max(1000000)
    private Integer stock_total;
    @NotNull
    @JsonProperty("amount")
    @Min(0) @Max(10000)
    private BigDecimal amount;
    @NotBlank
    @JsonProperty("channel_code")
    private String channel_code;
    @NotNull
    @JsonProperty("start_time")
    private Date start_time;
    @NotNull
    @JsonProperty("end_time")
    private Date end_time;
    @NotEmpty
    @JsonProperty("user_id")
    private String userId;
    
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

    public String getSource_code() {
        return source_code;
    }

    public void setSource_code(String source_code) {
        this.source_code = source_code;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Integer getStock_total() {
        return stock_total;
    }

    public void setStock_total(Integer stock_total) {
        this.stock_total = stock_total;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getChannel_code() {
        return channel_code;
    }

    public void setChannel_code(String channel_code) {
        this.channel_code = channel_code;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
