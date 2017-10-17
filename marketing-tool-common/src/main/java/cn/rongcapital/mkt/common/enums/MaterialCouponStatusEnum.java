/*************************************************
 * @功能及特点的描述简述: 优惠券状态枚举类 该类被编译测试过
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.6 
 * @date(创建、开发日期)：2016-12月-07 
 * @最后修改日期：2016-12月-07
 *************************************************/
package cn.rongcapital.mkt.common.enums;

public enum MaterialCouponStatusEnum {
    UNUSED("unused", "未投放"), USED("used", "已占用"), RELEASED("released", "已投放"), RELEASING("releasing", "投放中");


    private String code = "";
    private String description = "";

    private MaterialCouponStatusEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }


    /**
     * @功能描述: 通过Code获取相应的枚举值
     * @param code
     * @return boolean
     * @author xie.xiaoliang
     * @since 2016年12月7日
     */
    public static MaterialCouponStatusEnum getByCode(String code) {
        if (MaterialCouponStatusEnum.contains(code)) {
            for (MaterialCouponStatusEnum item : MaterialCouponStatusEnum.values()) {
                if (item.code.equals(code)) {
                    return item;
                }
            }
        }
        return null;
    }

    /**
     * @功能描述: 验证是否包含此Code
     * @param code
     * @return boolean
     * @author xie.xiaoliang
     * @since 2016年12月7日
     */
    public static boolean contains(String code) {
        MaterialCouponStatusEnum[] statusList = values();
        for (MaterialCouponStatusEnum item : statusList) {
            if (item.getCode().equals(code)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }


}
