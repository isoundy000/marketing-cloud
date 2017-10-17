package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;
import java.util.Date;

public class SmsTaskDetail extends BaseQuery {
    private Long id;

    private Long smsTaskHeadId;

    private String receiveMobile;

    private String sendMessage;

    private Long materielCouponCodeId;

    private String materielCouponCodeCode;

    private Byte status;

    private Date createTime;

    private Date updateTime;

    private String sendMobile;

    private Date sendTime;

    public SmsTaskDetail(){}



    /**
     * @param id
     * @param smsTaskHeadId
     * @param receiveMobile
     * @param sendMessage
     * @param status
     * @param createTime
     * @param updateTime
     * @param sendMobile
     * @param sendTime
     */
    public SmsTaskDetail(Long id, Long smsTaskHeadId, String receiveMobile, String sendMessage,Byte status,
             String sendMobile, Date sendTime) {
        super();
        this.id = id;
        this.smsTaskHeadId = smsTaskHeadId;
        this.receiveMobile = receiveMobile;
        this.sendMessage = sendMessage;
        this.status = status;
        this.sendMobile = sendMobile;
        this.sendTime = sendTime;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSmsTaskHeadId() {
        return smsTaskHeadId;
    }

    public void setSmsTaskHeadId(Long smsTaskHeadId) {
        this.smsTaskHeadId = smsTaskHeadId;
    }

    public String getReceiveMobile() {
        return receiveMobile;
    }

    public void setReceiveMobile(String receiveMobile) {
        this.receiveMobile = receiveMobile == null ? null : receiveMobile.trim();
    }

    public String getSendMessage() {
        return sendMessage;
    }

    public void setSendMessage(String sendMessage) {
        this.sendMessage = sendMessage == null ? null : sendMessage.trim();
    }

    public Long getMaterielCouponCodeId() {
        return materielCouponCodeId;
    }

    public void setMaterielCouponCodeId(Long materielCouponCodeId) {
        this.materielCouponCodeId = materielCouponCodeId;
    }

    public String getMaterielCouponCodeCode() {
        return materielCouponCodeCode;
    }

    public void setMaterielCouponCodeCode(String materielCouponCodeCode) {
        this.materielCouponCodeCode = materielCouponCodeCode == null ? null : materielCouponCodeCode.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getSendMobile() {
        return sendMobile;
    }

    public void setSendMobile(String sendMobile) {
        this.sendMobile = sendMobile == null ? null : sendMobile.trim();
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }
}