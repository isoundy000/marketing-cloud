package cn.rongcapital.mkt.po;

import java.util.Date;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class CustomTag extends BaseQuery {
    private Integer id;

    private String name;

    private Integer coverAudienceCount;

    private String oper;

    private Byte status;

    private Date updateTime;

    private Date createTime;

    public CustomTag() {}

    public CustomTag(Integer index, Integer size) {
        super(index, size);
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

    public Integer getCoverAudienceCount() {
        return coverAudienceCount;
    }

    public void setCoverAudienceCount(Integer coverAudienceCount) {
        this.coverAudienceCount = coverAudienceCount;
    }

    public String getOper() {
        return oper;
    }

    public void setOper(String oper) {
        this.oper = oper == null ? null : oper.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
