package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

import java.util.Date;

public class SegmentationBody extends BaseQuery {
    private Integer id;

    private Integer headId;

    private String groupId;

    private String groupName;

    private Integer groupIndex;

    private String tagId;

    private String tagName;

    private Integer tagExclude;

    private String tagValueId;

    private String tagValueName;

    private Byte status;

    private Date createTime;

    private Date updateTime;

    private Integer tagSeq;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHeadId() {
        return headId;
    }

    public void setHeadId(Integer headId) {
        this.headId = headId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public Integer getGroupIndex() {
        return groupIndex;
    }

    public void setGroupIndex(Integer groupIndex) {
        this.groupIndex = groupIndex;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId == null ? null : tagId.trim();
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName == null ? null : tagName.trim();
    }

    public Integer getTagExclude() {
        return tagExclude;
    }

    public void setTagExclude(Integer tagExclude) {
        this.tagExclude = tagExclude;
    }

    public String getTagValueId() {
        return tagValueId;
    }

    public void setTagValueId(String tagValueId) {
        this.tagValueId = tagValueId == null ? null : tagValueId.trim();
    }

    public String getTagValueName() {
        return tagValueName;
    }

    public void setTagValueName(String tagValueName) {
        this.tagValueName = tagValueName == null ? null : tagValueName.trim();
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

    public Integer getTagSeq() {
        return tagSeq;
    }

    public void setTagSeq(Integer tagSeq) {
        this.tagSeq = tagSeq;
    }
}