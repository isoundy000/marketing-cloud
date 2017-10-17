/*************************************************
 * @功能简述: VO:MaterialCouponGeneralGetOut
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2016/12/8
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.material.coupon.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;


public class MaterialCouponGeneralGetOut {

    private Long id;

    private String title;

    @JsonProperty("source_code")
    private String sourceCode;

    private String type;

    @JsonProperty("coupon_status")
    private String couponStatus;

    @JsonProperty("channel_code")
    private String channelCode;

    @JsonProperty("task_id")
    private Long taskId;

    @JsonProperty("task_name")
    private String taskName;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCouponStatus() {
        return couponStatus;
    }

    public void setCouponStatus(String couponStatus) {
        this.couponStatus = couponStatus;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public String toString() {
        return "MaterialCouponGeneralGetOut [id=" + id + ", title=" + title + ", sourceCode=" + sourceCode + ", type="
                        + type + ", couponStatus=" + couponStatus + ", channelCode=" + channelCode + ", taskId="
                        + taskId + ", taskName=" + taskName + "]";
    }

}
