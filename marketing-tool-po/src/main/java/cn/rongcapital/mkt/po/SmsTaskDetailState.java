package cn.rongcapital.mkt.po;

import java.util.Date;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class SmsTaskDetailState extends BaseQuery {
    private Long id;

    private Long smsTaskDetailId;

    private Integer smsTaskSendStatus;

    private Byte status;

    private Date createTime;

    private Date updateTime;
    
    public SmsTaskDetailState() {}
    
    

    /**
     * @param smsTaskDetailId
     * @param smsTaskSendStatus
     */
    public SmsTaskDetailState(Long smsTaskDetailId, Integer smsTaskSendStatus, Byte status) {
        super();
        this.smsTaskDetailId = smsTaskDetailId;
        this.smsTaskSendStatus = smsTaskSendStatus;
        this.status = status;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSmsTaskDetailId() {
        return smsTaskDetailId;
    }

    public void setSmsTaskDetailId(Long smsTaskDetailId) {
        this.smsTaskDetailId = smsTaskDetailId;
    }

    public Integer getSmsTaskSendStatus() {
        return smsTaskSendStatus;
    }

    public void setSmsTaskSendStatus(Integer smsTaskSendStatus) {
        this.smsTaskSendStatus = smsTaskSendStatus;
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
}