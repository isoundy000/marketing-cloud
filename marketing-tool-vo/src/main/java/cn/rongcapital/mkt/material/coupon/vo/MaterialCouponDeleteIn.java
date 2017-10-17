 /*************************************************
 * @功能及特点的描述简述:  优惠券的删除VO
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC
 * @author: liuhaizhan
 * @version: 版本
 * @date(创建、开发日期)：2016年12月7日
 * 最后修改日期：2016年12月7日
 * @复审人:
 *************************************************/ 
package cn.rongcapital.mkt.material.coupon.vo;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import cn.rongcapital.mkt.vo.BaseInput;

public class MaterialCouponDeleteIn extends BaseInput {
  
    @NotEmpty
    private String userToken = null;
    
    @NotNull
    private Integer id ;

    @JsonProperty("user_token")
    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
