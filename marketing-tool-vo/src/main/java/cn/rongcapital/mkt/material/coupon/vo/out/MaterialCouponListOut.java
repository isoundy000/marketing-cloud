/*************************************************
 * @功能及特点的描述简述: 分页查询获取优惠券列表(支持模糊查询)结果类
 * 该类被编译测试过
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.6
 * @date(创建、开发日期)：2016-12月-07 
 * 最后修改日期：2016-12月-07 
 * @复审人：
 *************************************************/ 
package cn.rongcapital.mkt.material.coupon.vo.out;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import cn.rongcapital.mkt.vo.BaseOutput;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MaterialCouponListOut extends BaseOutput{
    
    private List<MaterialCouponListItemOut> listItems = new ArrayList<>();

    public MaterialCouponListOut() {
        super();
    }

    /**
     * @param code
     * @param msg
     * @param total
     */
    public MaterialCouponListOut(int code, String msg, int total) {
        super(code, msg, total, null);
    }
    
    @JsonProperty("data")
    public List<MaterialCouponListItemOut> getListItems() {
        return listItems;
    }

    public void setListItems(List<MaterialCouponListItemOut> listItems) {
        this.listItems = listItems;
    }
}
