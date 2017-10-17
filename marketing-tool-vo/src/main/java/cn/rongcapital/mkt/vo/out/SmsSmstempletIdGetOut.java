package cn.rongcapital.mkt.vo.out;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 接口：mkt.sms.smstemplet.id.get 返回输出
 * 
 * @param id
 * @return
 * @Date 2016-11-11
 * @author shuiyangyang
 */
public class SmsSmstempletIdGetOut {
    private Integer id;

    private Byte channelType;

    private Byte type;

    private Byte auditStatus;

    private String name;
    
    private String auditReason;

    private String auditTime;

    private String content;
    
    private Boolean editCheck;
    
    private Boolean deleteCheck;
    
    private List<SmsSmstempletMaterialData> materialList = new ArrayList<SmsSmstempletMaterialData>();
    
    public SmsSmstempletIdGetOut() {}

    public SmsSmstempletIdGetOut(Integer id, Byte channelType, Byte type, Byte auditStatus,
                    String name, String auditReason, String auditTime, String content, Boolean editCheck, Boolean deleteCheck) {
        super();
        this.id = id;
        this.channelType = channelType;
        this.type = type;
        this.auditStatus = auditStatus;
        this.name = name;
        this.auditReason = auditReason;
        this.auditTime = auditTime;
        this.content = content;
        this.editCheck = editCheck;
        this.deleteCheck = deleteCheck;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("channel_type")
    public Byte getChannelType() {
        return channelType;
    }

    public void setChannelType(Byte channelType) {
        this.channelType = channelType;
    }

    @JsonProperty("type")
    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    @JsonProperty("audit_status")
    public Byte getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Byte auditStatus) {
        this.auditStatus = auditStatus;
    }

    @JsonProperty("name")
    public String getName() {
        return name == null ?"":name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("audit_reason")
    public String getAuditReason() {
        return auditReason == null ? "" : auditReason;
    }

    public void setAuditReason(String auditReason) {
        this.auditReason = auditReason;
    }

    @JsonProperty("audit_time")
    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
    }

    @JsonProperty("content")
    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @JsonProperty("edit_check")
    public Boolean getEditCheck() {
        return editCheck;
    }

    public void setEditCheck(Boolean editCheck) {
        this.editCheck = editCheck;
    }

    @JsonProperty("delete_check")
    public Boolean getDeleteCheck() {
        return deleteCheck;
    }

    public void setDeleteCheck(Boolean deleteCheck) {
        this.deleteCheck = deleteCheck;
    }

    @JsonProperty("material_list")
    public List<SmsSmstempletMaterialData> getMaterialList() {
		return materialList;
	}

	public void setMaterialList(List<SmsSmstempletMaterialData> materialList) {
		this.materialList = materialList;
	}

	@Override
    public String toString() {
        return "SmsSmstempletIdGetOut [id=" + id + ", channelType=" + channelType + ", type=" + type
                        + ", auditStatus=" + auditStatus + ", name=" + name + ", auditReason="
                        + auditReason + ", auditTime=" + auditTime + ", content=" + content
                        + ", editCheck=" + editCheck + ", deleteCheck=" + deleteCheck + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((auditReason == null) ? 0 : auditReason.hashCode());
        result = prime * result + ((auditStatus == null) ? 0 : auditStatus.hashCode());
        result = prime * result + ((auditTime == null) ? 0 : auditTime.hashCode());
        result = prime * result + ((channelType == null) ? 0 : channelType.hashCode());
        result = prime * result + ((content == null) ? 0 : content.hashCode());
        result = prime * result + ((deleteCheck == null) ? 0 : deleteCheck.hashCode());
        result = prime * result + ((editCheck == null) ? 0 : editCheck.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SmsSmstempletIdGetOut other = (SmsSmstempletIdGetOut) obj;
        if (auditReason == null) {
            if (other.auditReason != null)
                return false;
        } else if (!auditReason.equals(other.auditReason))
            return false;
        if (auditStatus == null) {
            if (other.auditStatus != null)
                return false;
        } else if (!auditStatus.equals(other.auditStatus))
            return false;
        if (auditTime == null) {
            if (other.auditTime != null)
                return false;
        } else if (!auditTime.equals(other.auditTime))
            return false;
        if (channelType == null) {
            if (other.channelType != null)
                return false;
        } else if (!channelType.equals(other.channelType))
            return false;
        if (content == null) {
            if (other.content != null)
                return false;
        } else if (!content.equals(other.content))
            return false;
        if (deleteCheck == null) {
            if (other.deleteCheck != null)
                return false;
        } else if (!deleteCheck.equals(other.deleteCheck))
            return false;
        if (editCheck == null) {
            if (other.editCheck != null)
                return false;
        } else if (!editCheck.equals(other.editCheck))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }

}
