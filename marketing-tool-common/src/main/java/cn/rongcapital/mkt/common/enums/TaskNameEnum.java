package cn.rongcapital.mkt.common.enums;

public enum TaskNameEnum {
    
    POPULATION(1, "人口属性同步任务"), 
    CUSTOMER_TAGS(2, "客户标签同步任务"), 
    ARCH_POINT(3, "埋点统计同步任务"), 
    MEMBER(4, "会员卡记录同步任务"), 
    LOGIN(5, "登录行为同步任务"), 
    PAYMENT(6, "支付记录同步任务"), 
    SHOPPING(7, "购物记录同步任务"),
    
    ;
    
    private TaskNameEnum(int code , String description){
        this.code = code;
        this.description = description;
    }
    
    /**
     * 数据类型代码
     */
    private int code;

    /**
     * 数据类型描述
     */
    private String description;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
