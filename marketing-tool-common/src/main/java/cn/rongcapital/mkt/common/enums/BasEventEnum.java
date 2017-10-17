package cn.rongcapital.mkt.common.enums;

public enum BasEventEnum {

    EVENT("事件类型", "event", ""), 
    DATE_TIME("事件发生的时间", "dateTime", "createTime"), 
    MID("MID", "mid", "mobile"), 
    PAY_CHANNEL("支付渠道", "payChannel", "payChannel"), 
    ALI_PAY_NUMBER("支付宝账号", "alipayNumber", ""), 
    ACC_SERIAL_NUMBER("账务流水号", "accSerialNumber", ""), 
    SERVICE_SERIAL_NUMBER("业务流水号", "serviceSerialNumber", ""), 
    BUS_ORDER_ID("商户订单号", "busOrderId", ""),
    BUS_NAME("商品名称", "busName", ""),
    START_TIME("发生时间", "startTime", ""),
    END_TIME("结束时间", "endTime", "completeTime"),
    PAY_STATUS("支付状态", "payStatus", "payStatus"),
    OTHER_ACCOUNT("对方账号", "otherAccount", "counterAcct"),
    TAKE_MONEY("收入金额", "takeMoney", "incomeAmt"),
    PAY_MONEY("支出金额", "payMoney", "paidAmt"),
    ACC_BALANCE("账户余额", "accBalance", "acctAmt"),
    REMARK("备注", "Remark", "comments"),
    DATA_SOURCES("数据来源", "dataSources", "source"),
    CHANNEL_CLASSIFY("渠道分类", "channelClassify", "channelType"),
    SHOP_CHANNEL_ID("购物渠道ID", "shopChannelId", "channelId"),
    SHOP_CHANNEL("购物渠道", "shopChannel", "channelName"),
    PAY_WAY("支付方式", "payWay", "payType"),
    SHOP_TIME("消费时间", "shopTime", "transTime"),
    BUSINESS_ID("商品ID", "businessID", "productId"),
    FORMAT("规格", "format", "specification"),
    COLOR("颜色", "color", "color"),
    DISCOUNT_TYPE("折扣类型", "discountType", "discountType"),
    DISCOUNT_MONEY("折扣金额", "discountMoney", "discountAmt"),
    PRICE("单价", "price", "price"),
    NUMBER("数量", "number", "amount"),
    STOCK_NUMBER("库存量", "stockNumber", "inventory"),
    BRAND_NAME("品牌", "brandName", "brandName"),
    BRAND_ID("品牌ID", "brandId", "brandId"),
    ONE_LEVEL_NAME("一级品类名称", "onelevelName", "class1Name"),
    ONE_LEVEL_ID("一级品类名称ID", "onelevelId", "class1Id"),
    TWO_LEVEL_NAME("二级品类名称", "twolevelName", "class2Name"),
    TWO_LEVEL_ID("二级品类名称ID", "twolevelId", "class2Id"),
    THREE_LEVEL_NAME("三级品类名称", "threelevelName", "class3Name"),
    THREE_LEVEL_ID("三级品类名称ID", "threelevelId", "class3Id"),
    FOUR_LEVEL_NAME("四级品类名称", "fourlevelName", "class4Name"),
    FOUR_LEVEL_ID("四级品类名称ID", "fourlevelId", "class4Id"),
    SHOP_PEOPLE("导购人员", "shopPeople", "saleAssistance"),
    SHOP_PEOPLE_ID("导购人员ID", "shopPeopleId", "saleAssistId"),
    BALANCE_PEOPLE("结算人员", "balancePeople", "settlementClerk"),
    BALANCE_PEOPLE_ID("结算人员ID", "balancePeopleId", "settlementClerkId"),
    LOGIN_TYPE("登录方式", "loginType", "loginType"),
    LOGIN_TIME("登录时间", "loginTime", "loginTime"),
    OUT_TIME("退出时间", "outTime", "logoutTime"),
    LOGIN_IP("登录IP", "loginIP", "loginIp"),
    LOGIN_DEVICE("登录设备", "loginDevice", "loginDevice"),
    RESOLUTION_RATIO("分辨率", "resolutionRatio", "resolutionRatio"),
    LOGIN_PAGE("登录页面", "loginPage", "loginUrl"),
    CREATE_TIME("创建时间", "createTime", "createTime"),
    CLICK_TIME("点击时间", "clickTime", "clickTime"),
    VIP_CARD_NUM("会员卡号", "vipCardNum", "memberId"),
    VIP_INTEGRAL("会员积分", "vipIntegral", "memberPoints"),
    VIP_LEVEL("会员等级", "vipLevel", "memberLevel"),
    OPEN_CARD_TIME("开卡时间", "openCardTime", "registTime"),
    CARD_BALANCE("卡内余额", "cardBalance", "cardAmt"),
    VALID_DAY("有效期（日）", "validDay", "expire"),

    ;

    private BasEventEnum(String basCNName, String basENName, String mcName) {
        this.basCNName = basCNName;
        this.basENName = basENName;
        this.mcName = mcName;
    }

    /**
     * BAS中的中文列名
     */
    private String basCNName;

    /**
     * BAS中的英文字段名
     */
    private String basENName;

    /**
     * Marketing Cloud中对象中的字段名
     */
    private String mcName;

    public String getBasCNName() {
        return basCNName;
    }

    public void setBasCNName(String basCNName) {
        this.basCNName = basCNName;
    }

    public String getBasENName() {
        return basENName;
    }

    public void setBasENName(String basENName) {
        this.basENName = basENName;
    }

    public String getMcName() {
        return mcName;
    }

    public void setMcName(String mcName) {
        this.mcName = mcName;
    }
}
