package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

import java.util.Date;

public class SmsMaterial extends BaseQuery {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String code;

    private String name;

    private Byte channelType;

    private Byte smsType;

    private Integer smsTempletId;

    private Integer smsSignId;

    private String smsSignName;

    private Byte status;

    private String creator;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    private String smsTempletContent;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Byte getChannelType() {
        return channelType;
    }

    public void setChannelType(Byte channelType) {
        this.channelType = channelType;
    }

    public Byte getSmsType() {
        return smsType;
    }

    public void setSmsType(Byte smsType) {
        this.smsType = smsType;
    }

    public Integer getSmsTempletId() {
        return smsTempletId;
    }

    public void setSmsTempletId(Integer smsTempletId) {
        this.smsTempletId = smsTempletId;
    }

    public Integer getSmsSignId() {
        return smsSignId;
    }

    public void setSmsSignId(Integer smsSignId) {
        this.smsSignId = smsSignId;
    }

    public String getSmsSignName() {
        return smsSignName;
    }

    public void setSmsSignName(String smsSignName) {
        this.smsSignName = smsSignName == null ? null : smsSignName.trim();
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

    public String getSmsTempletContent() {
        return smsTempletContent;
    }

    public void setSmsTempletContent(String smsTempletContent) {
        this.smsTempletContent = smsTempletContent == null ? null : smsTempletContent.trim();
    }
}
