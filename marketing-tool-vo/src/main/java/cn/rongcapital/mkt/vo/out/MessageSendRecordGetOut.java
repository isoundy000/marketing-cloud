/**
 * 
 */
package cn.rongcapital.mkt.vo.out;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;
import org.jboss.resteasy.util.DateUtil;

/**
 * @author shuiyangyang
 *
 */
public class MessageSendRecordGetOut {
    
    private Long id;
    
    private Long smsTaskHeadId;

    private String receiveMobile;

    private String sendMessage;

    private String sendMobile;

    private String sendTime;
    
    private Integer smsTaskSendStatus;
    
    public MessageSendRecordGetOut(){}
    
    

    /**
     * @param id
     * @param smsTaskHeadId
     * @param receiveMobile
     * @param sendMessage
     * @param sendMobile
     * @param sendTime
     * @param smsTaskSendStatus
     */
    public MessageSendRecordGetOut(Long id, Long smsTaskHeadId, String receiveMobile, String sendMessage,
            String sendMobile, Date sendTime, Integer smsTaskSendStatus) {
        super();
        this.id = id;
        this.smsTaskHeadId = smsTaskHeadId;
        this.receiveMobile = receiveMobile == null ? "" : receiveMobile.trim();
        this.sendMessage = sendMessage == null ? "" : sendMessage.trim();
        this.sendMobile = sendMobile == null ? "" : sendMobile.trim();
        this.sendTime = sendTime == null ? "" : DateUtil.formatDate(sendTime, "yyyy-MM-dd HH:mm:ss");
        this.smsTaskSendStatus = smsTaskSendStatus;
    }



    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("sms_task_head_id")
    public Long getSmsTaskHeadId() {
        return smsTaskHeadId;
    }

    public void setSmsTaskHeadId(Long smsTaskHeadId) {
        this.smsTaskHeadId = smsTaskHeadId;
    }

    @JsonProperty("receive_mobile")
    public String getReceiveMobile() {
        return receiveMobile == null ? "" : receiveMobile.trim();
    }

    public void setReceiveMobile(String receiveMobile) {
        this.receiveMobile = receiveMobile == null ? "" : receiveMobile.trim();
    }

    @JsonProperty("send_message")
    public String getSendMessage() {
        return sendMessage == null ? "" : sendMessage.trim();
    }

    public void setSendMessage(String sendMessage) {
        this.sendMessage = sendMessage == null ? "" : sendMessage.trim();
    }

    @JsonProperty("send_mobile")
    public String getSendMobile() {
        return sendMobile == null ? "" : sendMobile.trim();
    }

    public void setSendMobile(String sendMobile) {
        this.sendMobile = sendMobile == null ? "" : sendMobile.trim();
    }

    @JsonProperty("send_time")
    public String getSendTime() {
        return sendTime == null ? "" : sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime == null ? "" : DateUtil.formatDate(sendTime, "yyyy-MM-dd HH:mm:ss");
    }

    @JsonProperty("sms_task_send_status")
    public Integer getSmsTaskSendStatus() {
        return smsTaskSendStatus == null ? 0 : smsTaskSendStatus;
    }

    public void setSmsTaskSendStatus(Integer smsTaskSendStatus) {
        this.smsTaskSendStatus = smsTaskSendStatus;
    }



    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((receiveMobile == null) ? 0 : receiveMobile.hashCode());
        result = prime * result + ((sendMessage == null) ? 0 : sendMessage.hashCode());
        result = prime * result + ((sendMobile == null) ? 0 : sendMobile.hashCode());
        result = prime * result + ((sendTime == null) ? 0 : sendTime.hashCode());
        result = prime * result + ((smsTaskHeadId == null) ? 0 : smsTaskHeadId.hashCode());
        result = prime * result + ((smsTaskSendStatus == null) ? 0 : smsTaskSendStatus.hashCode());
        return result;
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        MessageSendRecordGetOut other = (MessageSendRecordGetOut) obj;
        if (id == null) {
            if (other.id != null) return false;
        } else if (!id.equals(other.id)) return false;
        if (receiveMobile == null) {
            if (other.receiveMobile != null) return false;
        } else if (!receiveMobile.equals(other.receiveMobile)) return false;
        if (sendMessage == null) {
            if (other.sendMessage != null) return false;
        } else if (!sendMessage.equals(other.sendMessage)) return false;
        if (sendMobile == null) {
            if (other.sendMobile != null) return false;
        } else if (!sendMobile.equals(other.sendMobile)) return false;
        if (sendTime == null) {
            if (other.sendTime != null) return false;
        } else if (!sendTime.equals(other.sendTime)) return false;
        if (smsTaskHeadId == null) {
            if (other.smsTaskHeadId != null) return false;
        } else if (!smsTaskHeadId.equals(other.smsTaskHeadId)) return false;
        if (smsTaskSendStatus == null) {
            if (other.smsTaskSendStatus != null) return false;
        } else if (!smsTaskSendStatus.equals(other.smsTaskSendStatus)) return false;
        return true;
    }



    @Override
    public String toString() {
        return "MessageSendRecordGetOut [id=" + id + ", smsTaskHeadId=" + smsTaskHeadId + ", receiveMobile="
                + receiveMobile + ", sendMessage=" + sendMessage + ", sendMobile=" + sendMobile + ", sendTime="
                + sendTime + ", smsTaskSendStatus=" + smsTaskSendStatus + "]";
    }
    
    

}
