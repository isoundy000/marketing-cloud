package cn.rongcapital.mkt.po;

import java.util.Date;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class SmsTemplet extends BaseQuery{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String code;

    private Byte channelType;

    private Byte type;

    private Byte auditStatus;

    private String auditor;

    private Byte status;

    private String creator;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    private String name;
    
    private String auditReason;

    private Date auditTime;

    private String content;
    
    /**
     * 临时属性
     */
    private String createTimeStr;
    
    private String auditStatusStr;
    
    private String typeStr;
    
    private Boolean editCheck;
    
    private Boolean deleteCheck;
    
    
    public SmsTemplet(){}


    public SmsTemplet(Byte channelType, Byte type, Byte auditStatus, String content) {
        super();
        this.channelType = channelType;
        this.type = type;
        this.auditStatus = auditStatus;
        this.content = content;
    }
    
    public SmsTemplet(Integer id, String code, Byte channelType, Byte type, Byte auditStatus,
                    String auditor, Byte status, String creator, Date createTime, String updateUser,
                    Date updateTime, String name, String auditReason, Date auditTime,
                    String content) {
        super();
        this.id = id;
        this.code = code;
        this.channelType = channelType;
        this.type = type;
        this.auditStatus = auditStatus;
        this.auditor = auditor;
        this.status = status;
        this.creator = creator;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.name = name;
        this.auditReason = auditReason;
        this.auditTime = auditTime;
        this.content = content;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Byte getChannelType() {
        return channelType;
    }

    public void setChannelType(Byte channelType) {
        this.channelType = channelType;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Byte auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor == null ? null : auditor.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }


    public String getAuditReason() {
        return auditReason;
    }

    public void setAuditReason(String auditReason) {
        this.auditReason = auditReason == null ? null : auditReason.trim();
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }
    
    
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}


	public String getAuditStatusStr() {
		return auditStatusStr;
	}


	public void setAuditStatusStr(String auditStatusStr) {
		this.auditStatusStr = auditStatusStr;
	}

	public String getTypeStr() {
		return typeStr;
	}

	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}

	public Boolean getEditCheck() {
		return editCheck;
	}

	public void setEditCheck(Boolean editCheck) {
		this.editCheck = editCheck;
	}

	public Boolean getDeleteCheck() {
		return deleteCheck;
	}

	public void setDeleteCheck(Boolean deleteCheck) {
		this.deleteCheck = deleteCheck;
	}
	    
}