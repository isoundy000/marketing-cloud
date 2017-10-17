/*************************************************
 * @功能及特点的描述简述: 优惠码核销操作参数类
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.6
 * @date(创建、开发日期)：2016-12-20 
 * @date(最后修改日期)：2016-12-20 
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.material.coupon.vo.in;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import cn.rongcapital.mkt.vo.BaseInput;

public class MaterialCouponCodeVerifyIn extends BaseInput {

    @NotEmpty
    private String userToken = null;
    @NotNull
    private Long id;
    @NotEmpty
    private String coupon_code;
    @NotEmpty
    private String user;


    public String getUserToken() {
        return userToken;
    }

    @JsonProperty("user_token")
    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public Long getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
    }

    public String getCoupon_code() {
        return coupon_code;
    }

    @JsonProperty("coupon_code")
    public void setCoupon_code(String coupon_code) {
        this.coupon_code = coupon_code;
    }

    public String getUser() {
        return user;
    }

    @JsonProperty("user")
    public void setUser(String user) {
        this.user = user;
    }



}
