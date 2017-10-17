package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class PartyBehavior extends BaseQuery {
	private Integer id;
	
    private String contactId;

    private String behaviorType;

    private String behaviorName;

    private String wechatType;

    private String wechatName;

    private String message;

    private String createtime;

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId == null ? null : contactId.trim();
    }

    public String getBehaviorType() {
        return behaviorType;
    }

    public void setBehaviorType(String behaviorType) {
        this.behaviorType = behaviorType == null ? null : behaviorType.trim();
    }

    public String getBehaviorName() {
        return behaviorName;
    }

    public void setBehaviorName(String behaviorName) {
        this.behaviorName = behaviorName == null ? null : behaviorName.trim();
    }

    public String getWechatType() {
        return wechatType;
    }

    public void setWechatType(String wechatType) {
        this.wechatType = wechatType == null ? null : wechatType.trim();
    }

    public String getWechatName() {
        return wechatName;
    }

    public void setWechatName(String wechatName) {
        this.wechatName = wechatName == null ? null : wechatName.trim();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }
}
