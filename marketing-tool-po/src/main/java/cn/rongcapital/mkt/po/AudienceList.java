package cn.rongcapital.mkt.po;

import java.util.Date;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class AudienceList extends BaseQuery {
    private Integer id;

    private String audienceName;

    private Integer audienceRows;

    private String source;

    private Byte status;

    private Date createTime;

    private Date updateTime;

    public AudienceList() {}

    public AudienceList(Integer index, Integer size) {
        super(index, size);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAudienceName() {
        return audienceName;
    }

    public void setAudienceName(String audienceName) {
        this.audienceName = audienceName == null ? null : audienceName.trim();
    }

    public Integer getAudienceRows() {
        return audienceRows;
    }

    public void setAudienceRows(Integer audienceRows) {
        this.audienceRows = audienceRows;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
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
