package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

import java.math.BigDecimal;
import java.util.Date;

public class OriginalDataShopping extends BaseQuery {
    private Integer id;

    private String channelType;

    private String channelId;

    private String channelName;

    private String payType;

    private String transSerial;

    private String orderNo;

    private Date transTime;

    private String productId;

    private String specification;

    private String color;

    private String discountType;

    private BigDecimal discountAmt;

    private BigDecimal price;

    private Integer amount;

    private Integer inventory;

    private String brandId;

    private String brandName;

    private String class1Id;

    private String class1Name;

    private String class2Id;

    private String class2Name;

    private String class3Id;

    private String class3Name;

    private String class4Id;

    private String class4Name;

    private String saleAssistId;

    private String saleAssistance;

    private String settlementClerkId;

    private String settlementClerk;

    private String identifyNo;

    private String drivingLicense;

    private String email;

    private String mobile;

    private String tel;

    private String qq;

    private String acctType;

    private String acctNo;

    private String idfa;

    private String imei;

    private String udid;

    private String phoneMac;

    private Integer status;

    private Date updateTime;

    private String source;

    private String batchId;

    private String fileUnique;

    private String orderStatus;

    private String deliveryWay;

    private String logisticsStatus;

    private BigDecimal shippingFee;

    private String shippingWay;

    private String expressCompany;

    private String expressOrder;

    private String consignee;

    private String consigneeTel;

    private String consigneeAddr;

    private String buyerComment;

    private String wxmpId;

    private String wxCode;

    private String productName;

    private String bitmap;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType == null ? null : channelType.trim();
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId == null ? null : channelId.trim();
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName == null ? null : channelName.trim();
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }

    public String getTransSerial() {
        return transSerial;
    }

    public void setTransSerial(String transSerial) {
        this.transSerial = transSerial == null ? null : transSerial.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification == null ? null : specification.trim();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color == null ? null : color.trim();
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType == null ? null : discountType.trim();
    }

    public BigDecimal getDiscountAmt() {
        return discountAmt;
    }

    public void setDiscountAmt(BigDecimal discountAmt) {
        this.discountAmt = discountAmt;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId == null ? null : brandId.trim();
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName == null ? null : brandName.trim();
    }

    public String getClass1Id() {
        return class1Id;
    }

    public void setClass1Id(String class1Id) {
        this.class1Id = class1Id == null ? null : class1Id.trim();
    }

    public String getClass1Name() {
        return class1Name;
    }

    public void setClass1Name(String class1Name) {
        this.class1Name = class1Name == null ? null : class1Name.trim();
    }

    public String getClass2Id() {
        return class2Id;
    }

    public void setClass2Id(String class2Id) {
        this.class2Id = class2Id == null ? null : class2Id.trim();
    }

    public String getClass2Name() {
        return class2Name;
    }

    public void setClass2Name(String class2Name) {
        this.class2Name = class2Name == null ? null : class2Name.trim();
    }

    public String getClass3Id() {
        return class3Id;
    }

    public void setClass3Id(String class3Id) {
        this.class3Id = class3Id == null ? null : class3Id.trim();
    }

    public String getClass3Name() {
        return class3Name;
    }

    public void setClass3Name(String class3Name) {
        this.class3Name = class3Name == null ? null : class3Name.trim();
    }

    public String getClass4Id() {
        return class4Id;
    }

    public void setClass4Id(String class4Id) {
        this.class4Id = class4Id == null ? null : class4Id.trim();
    }

    public String getClass4Name() {
        return class4Name;
    }

    public void setClass4Name(String class4Name) {
        this.class4Name = class4Name == null ? null : class4Name.trim();
    }

    public String getSaleAssistId() {
        return saleAssistId;
    }

    public void setSaleAssistId(String saleAssistId) {
        this.saleAssistId = saleAssistId == null ? null : saleAssistId.trim();
    }

    public String getSaleAssistance() {
        return saleAssistance;
    }

    public void setSaleAssistance(String saleAssistance) {
        this.saleAssistance = saleAssistance == null ? null : saleAssistance.trim();
    }

    public String getSettlementClerkId() {
        return settlementClerkId;
    }

    public void setSettlementClerkId(String settlementClerkId) {
        this.settlementClerkId = settlementClerkId == null ? null : settlementClerkId.trim();
    }

    public String getSettlementClerk() {
        return settlementClerk;
    }

    public void setSettlementClerk(String settlementClerk) {
        this.settlementClerk = settlementClerk == null ? null : settlementClerk.trim();
    }

    public String getIdentifyNo() {
        return identifyNo;
    }

    public void setIdentifyNo(String identifyNo) {
        this.identifyNo = identifyNo == null ? null : identifyNo.trim();
    }

    public String getDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(String drivingLicense) {
        this.drivingLicense = drivingLicense == null ? null : drivingLicense.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType == null ? null : acctType.trim();
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo == null ? null : acctNo.trim();
    }

    public String getIdfa() {
        return idfa;
    }

    public void setIdfa(String idfa) {
        this.idfa = idfa == null ? null : idfa.trim();
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei == null ? null : imei.trim();
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid == null ? null : udid.trim();
    }

    public String getPhoneMac() {
        return phoneMac;
    }

    public void setPhoneMac(String phoneMac) {
        this.phoneMac = phoneMac == null ? null : phoneMac.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId == null ? null : batchId.trim();
    }

    public String getFileUnique() {
        return fileUnique;
    }

    public void setFileUnique(String fileUnique) {
        this.fileUnique = fileUnique == null ? null : fileUnique.trim();
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus == null ? null : orderStatus.trim();
    }

    public String getDeliveryWay() {
        return deliveryWay;
    }

    public void setDeliveryWay(String deliveryWay) {
        this.deliveryWay = deliveryWay == null ? null : deliveryWay.trim();
    }

    public String getLogisticsStatus() {
        return logisticsStatus;
    }

    public void setLogisticsStatus(String logisticsStatus) {
        this.logisticsStatus = logisticsStatus == null ? null : logisticsStatus.trim();
    }

    public BigDecimal getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(BigDecimal shippingFee) {
        this.shippingFee = shippingFee;
    }

    public String getShippingWay() {
        return shippingWay;
    }

    public void setShippingWay(String shippingWay) {
        this.shippingWay = shippingWay == null ? null : shippingWay.trim();
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany == null ? null : expressCompany.trim();
    }

    public String getExpressOrder() {
        return expressOrder;
    }

    public void setExpressOrder(String expressOrder) {
        this.expressOrder = expressOrder == null ? null : expressOrder.trim();
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee == null ? null : consignee.trim();
    }

    public String getConsigneeTel() {
        return consigneeTel;
    }

    public void setConsigneeTel(String consigneeTel) {
        this.consigneeTel = consigneeTel == null ? null : consigneeTel.trim();
    }

    public String getConsigneeAddr() {
        return consigneeAddr;
    }

    public void setConsigneeAddr(String consigneeAddr) {
        this.consigneeAddr = consigneeAddr == null ? null : consigneeAddr.trim();
    }

    public String getBuyerComment() {
        return buyerComment;
    }

    public void setBuyerComment(String buyerComment) {
        this.buyerComment = buyerComment == null ? null : buyerComment.trim();
    }

    public String getWxmpId() {
        return wxmpId;
    }

    public void setWxmpId(String wxmpId) {
        this.wxmpId = wxmpId == null ? null : wxmpId.trim();
    }

    public String getWxCode() {
        return wxCode;
    }

    public void setWxCode(String wxCode) {
        this.wxCode = wxCode == null ? null : wxCode.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getBitmap() {
        return bitmap;
    }

    public void setBitmap(String bitmap) {
        this.bitmap = bitmap == null ? null : bitmap.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
