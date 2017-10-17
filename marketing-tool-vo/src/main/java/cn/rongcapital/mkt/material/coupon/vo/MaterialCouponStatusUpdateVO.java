/*************************************************
 * @功能简述: 回写优惠券状态Vo类
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 1.0
 * @date: 2016/12/7
 *************************************************/
package cn.rongcapital.mkt.material.coupon.vo;


public class MaterialCouponStatusUpdateVO {

    private Long id;

    private String status;

    private Long taskId;

    private String taskName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        return "MaterialCouponStatusUpdateVO [id=" + id + ", status=" + status + ", taskId=" + taskId + ", taskName="
                        + taskName + "]";
    }

}
