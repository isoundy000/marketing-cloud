package cn.rongcapital.mkt.vo.out;

import java.math.BigDecimal;
import java.util.Date;

/*************************************************
 * @功能及特点的描述简述: BAS的event所需的VO
 * @author : nianjun.sun
 * @date : 2016-07-13
 *************************************************/
public class BasEventOut {

    /**
     * 事件类型
     */
    private String event;

    /**
     * 时间发生的时间
     */
    private Date dateTime;

    /**
     * MID
     */
    private String mid;

    /**
     * 支付渠道
     */
    private String payChannel;

    /**
     * 支付宝账号
     */
    private String alipayNumber;

    /**
     * 账务流水号
     */
    private String accSerialNumber;

    /**
     * 业务流水号
     */
    private String serviceSerialNumber;

    /**
     * 商户订单号
     */
    private String busOrderId;

    /**
     * 商品名称
     */
    private String BusName;

    /**
     * 发生时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 支付状态
     */
    private String payStatus;

    /**
     * 对方账号
     */
    private String otherAccount;

    /**
     * 收入金额
     */
    private BigDecimal takeMoney;

    /**
     * 支出金额
     */
    private BigDecimal payMoney;

    /**
     * 账户余额
     */
    private BigDecimal accBalance;

    /**
     * 备注
     */
    private String remark;

    /**
     * 数据来源
     */
    private String dataSources;

    /**
     * 渠道分类
     */
    private String channelClassify;

    /**
     * 购物渠道ID
     */
    private String shopChannelId;

    /**
     * 购物渠道
     */
    private String shopChannel;

    /**
     * 支付方式
     */
    private String payWay;

    /**
     * 消费时间
     */
    private Date shopTime;

    /**
     * 商品ID
     */
    private String businessId;

    /**
     * 规格
     */
    private String format;

    /**
     * 颜色
     */
    private String color;

    /**
     * 折扣类型
     */
    private String discountType;

    /**
     * 折扣金额
     */
    private BigDecimal discountMoney;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 数量
     */
    private Integer number;

    /**
     * 库存量
     */
    private Integer stockNumber;

    /**
     * 品牌
     */
    private String brandName;

    /**
     * 品牌Id
     */
    private String brandId;

    /**
     * 一级品类名称
     */
    private String onelevelName;

    /**
     * 一级品类名称Id
     */
    private String onelevelId;

    /**
     * 二级品类名称
     */
    private String twolevelName;

    /**
     * 二级品类名称Id
     */
    private String twolevelId;

    /**
     * 三级品类名称
     */
    private String threelevelName;

    /**
     * 三级品类名称Id
     */
    private String threelevelId;

    /**
     * 四级品类名称
     */
    private String fourlevelName;

    /**
     * 四级品类名称Id
     */
    private String fourlevelId;

    /**
     * 导购人员
     */
    private String shopPeople;

    /**
     * 导购人员Id
     */
    private String shopPeopleId;

    /**
     * 结算人员
     */
    private String balancePeople;

    /**
     * 结算人员Id
     */
    private String balancePeopleId;

    /**
     * 登录方式
     */
    private String loginType;

    /**
     * 登录时间
     */
    private Date loginTime;

    /**
     * 退出时间
     */
    private Date outTime;

    /**
     * 登录IP
     */
    private String loginIp;

    /**
     * 登录设备
     */
    private String loginDevice;

    /**
     * 分辨率
     */
    private String resolutionRatio;

    /**
     * 登录页面
     */
    private String loginPage;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 点击时间
     */
    private Date clickTime;

    /**
     * 会员卡号
     */
    private Integer vipCardNum;

    /**
     * 会员积分
     */
    private String vipIntegral;

    /**
     * 会员等级
     */
    private String vipLevel;

    /**
     * 开卡时间
     */
    private Date openCardTime;

    /**
     * 卡内余额
     */
    private BigDecimal cardBalance;

    /**
     * 有效期
     */
    private Date validDay;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public String getAlipayNumber() {
        return alipayNumber;
    }

    public void setAlipayNumber(String alipayNumber) {
        this.alipayNumber = alipayNumber;
    }

    public String getAccSerialNumber() {
        return accSerialNumber;
    }

    public void setAccSerialNumber(String accSerialNumber) {
        this.accSerialNumber = accSerialNumber;
    }

    public String getServiceSerialNumber() {
        return serviceSerialNumber;
    }

    public void setServiceSerialNumber(String serviceSerialNumber) {
        this.serviceSerialNumber = serviceSerialNumber;
    }

    public String getBusOrderId() {
        return busOrderId;
    }

    public void setBusOrderId(String busOrderId) {
        this.busOrderId = busOrderId;
    }

    public String getBusName() {
        return BusName;
    }

    public void setBusName(String busName) {
        BusName = busName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getOtherAccount() {
        return otherAccount;
    }

    public void setOtherAccount(String otherAccount) {
        this.otherAccount = otherAccount;
    }

    public BigDecimal getTakeMoney() {
        return takeMoney;
    }

    public void setTakeMoney(BigDecimal takeMoney) {
        this.takeMoney = takeMoney;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public BigDecimal getAccBalance() {
        return accBalance;
    }

    public void setAccBalance(BigDecimal accBalance) {
        this.accBalance = accBalance;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDataSources() {
        return dataSources;
    }

    public void setDataSources(String dataSources) {
        this.dataSources = dataSources;
    }

    public String getChannelClassify() {
        return channelClassify;
    }

    public void setChannelClassify(String channelClassify) {
        this.channelClassify = channelClassify;
    }

    public String getShopChannelId() {
        return shopChannelId;
    }

    public void setShopChannelId(String shopChannelId) {
        this.shopChannelId = shopChannelId;
    }

    public String getShopChannel() {
        return shopChannel;
    }

    public void setShopChannel(String shopChannel) {
        this.shopChannel = shopChannel;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public Date getShopTime() {
        return shopTime;
    }

    public void setShopTime(Date shopTime) {
        this.shopTime = shopTime;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public BigDecimal getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(BigDecimal discountMoney) {
        this.discountMoney = discountMoney;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getStockNumber() {
        return stockNumber;
    }

    public void setStockNumber(Integer stockNumber) {
        this.stockNumber = stockNumber;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getOnelevelName() {
        return onelevelName;
    }

    public void setOnelevelName(String onelevelName) {
        this.onelevelName = onelevelName;
    }

    public String getOnelevelId() {
        return onelevelId;
    }

    public void setOnelevelId(String onelevelId) {
        this.onelevelId = onelevelId;
    }

    public String getTwolevelName() {
        return twolevelName;
    }

    public void setTwolevelName(String twolevelName) {
        this.twolevelName = twolevelName;
    }

    public String getTwolevelId() {
        return twolevelId;
    }

    public void setTwolevelId(String twolevelId) {
        this.twolevelId = twolevelId;
    }

    public String getThreelevelName() {
        return threelevelName;
    }

    public void setThreelevelName(String threelevelName) {
        this.threelevelName = threelevelName;
    }

    public String getThreelevelId() {
        return threelevelId;
    }

    public void setThreelevelId(String threelevelId) {
        this.threelevelId = threelevelId;
    }

    public String getFourlevelName() {
        return fourlevelName;
    }

    public void setFourlevelName(String fourlevelName) {
        this.fourlevelName = fourlevelName;
    }

    public String getFourlevelId() {
        return fourlevelId;
    }

    public void setFourlevelId(String fourlevelId) {
        this.fourlevelId = fourlevelId;
    }

    public String getShopPeople() {
        return shopPeople;
    }

    public void setShopPeople(String shopPeople) {
        this.shopPeople = shopPeople;
    }

    public String getShopPeopleId() {
        return shopPeopleId;
    }

    public void setShopPeopleId(String shopPeopleId) {
        this.shopPeopleId = shopPeopleId;
    }

    public String getBalancePeople() {
        return balancePeople;
    }

    public void setBalancePeople(String balancePeople) {
        this.balancePeople = balancePeople;
    }

    public String getBalancePeopleId() {
        return balancePeopleId;
    }

    public void setBalancePeopleId(String balancePeopleId) {
        this.balancePeopleId = balancePeopleId;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getLoginDevice() {
        return loginDevice;
    }

    public void setLoginDevice(String loginDevice) {
        this.loginDevice = loginDevice;
    }

    public String getResolutionRatio() {
        return resolutionRatio;
    }

    public void setResolutionRatio(String resolutionRatio) {
        this.resolutionRatio = resolutionRatio;
    }

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getClickTime() {
        return clickTime;
    }

    public void setClickTime(Date clickTime) {
        this.clickTime = clickTime;
    }

    public Integer getVipCardNum() {
        return vipCardNum;
    }

    public void setVipCardNum(Integer vipCardNum) {
        this.vipCardNum = vipCardNum;
    }

    public String getVipIntegral() {
        return vipIntegral;
    }

    public void setVipIntegral(String vipIntegral) {
        this.vipIntegral = vipIntegral;
    }

    public String getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(String vipLevel) {
        this.vipLevel = vipLevel;
    }

    public Date getOpenCardTime() {
        return openCardTime;
    }

    public void setOpenCardTime(Date openCardTime) {
        this.openCardTime = openCardTime;
    }

    public BigDecimal getCardBalance() {
        return cardBalance;
    }

    public void setCardBalance(BigDecimal cardBalance) {
        this.cardBalance = cardBalance;
    }

    public Date getValidDay() {
        return validDay;
    }

    public void setValidDay(Date validDay) {
        this.validDay = validDay;
    }

}
