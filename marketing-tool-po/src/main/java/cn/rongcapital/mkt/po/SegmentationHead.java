package cn.rongcapital.mkt.po;

import java.util.Date;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class SegmentationHead extends BaseQuery {
	
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String name;

    private Byte publishStatus;

    private Integer referCampaignCount;

    private Date createTime;

    private Date updateTime;

    private Byte status;

    private String oper;

    private String tagIds;
    
    public SegmentationHead() {
    }
    
    public SegmentationHead(Integer startIndex, Integer pageSize) {
    	super(startIndex, pageSize);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Byte getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(Byte publishStatus) {
        this.publishStatus = publishStatus;
    }

    public Integer getReferCampaignCount() {
        return referCampaignCount;
    }

    public void setReferCampaignCount(Integer referCampaignCount) {
        this.referCampaignCount = referCampaignCount;
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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getOper() {
        return oper;
    }

    public void setOper(String oper) {
        this.oper = oper == null ? null : oper.trim();
    }

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds == null ? null : tagIds.trim();
    }
}
