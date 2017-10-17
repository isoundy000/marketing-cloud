/*************************************************
 * @功能简述: 回写优惠码状态Vo类
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 1.0
 * @date: 2016/12/7
 *************************************************/
package cn.rongcapital.mkt.material.coupon.vo;


public class MaterialCouponCodeStatusUpdateVO {

    /**
     * 优惠码主键
     */
    private Long id;

    /**
     * 用户
     */
    private String user;

    /**
     * 优惠码状态值
     */
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MaterialCouponCodeStatusUpdateVO [id=" + id + ", user=" + user + ", status=" + status + "]";
    }


}
