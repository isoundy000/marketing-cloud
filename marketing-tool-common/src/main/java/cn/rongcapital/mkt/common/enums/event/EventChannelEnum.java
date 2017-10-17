/*************************************************
 * @功能及特点的描述简述: 事件渠道枚举类
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017-01-09 
 * @date(最后修改日期)：2017-01-09 
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.common.enums.event;

public enum EventChannelEnum {
    CHANNEL1("channel1", "一方事件"), CHANNEL2("channel2", "二方事件"), CHANNEL3("channel3", "三方事件");

    private String code = "";
    private String description = "";

    private EventChannelEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * @功能描述: 通过Code获取相应的枚举值
     * @param code
     * @return boolean
     * @author xie.xiaoliang
     * @since 2017-01-09 
     */
    public static EventChannelEnum getByCode(String code) {
        if (EventChannelEnum.contains(code)) {
            for (EventChannelEnum item : EventChannelEnum.values()) {
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
     * @since 2017-01-09 
     */
    public static boolean contains(String code) {
        EventChannelEnum[] channelCodes = values();
        for (EventChannelEnum item : channelCodes) {
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