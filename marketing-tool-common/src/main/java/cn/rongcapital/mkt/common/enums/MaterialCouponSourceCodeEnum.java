/*************************************************
 * @功能及特点的描述简述: 优惠券中来源类型ENUM 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.6 
 * @date(创建、开发日期)：2016-12月-07 
 * @最后修改日期：2016-12月-07 
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.common.enums;

public enum MaterialCouponSourceCodeEnum {
    COMMON("common", "通用码"), GENERATE("generate", "平台生成码"), OWN("own", "自有码");

    private String code = "";
    private String description = "";

    private MaterialCouponSourceCodeEnum(String code, String description) {
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
    public static MaterialCouponSourceCodeEnum getByCode(String code) {
        if (MaterialCouponSourceCodeEnum.contains(code)) {
            for (MaterialCouponSourceCodeEnum item : MaterialCouponSourceCodeEnum.values()) {
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
        MaterialCouponSourceCodeEnum[] items = values();
        for (MaterialCouponSourceCodeEnum item : items) {
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
