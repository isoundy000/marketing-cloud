package cn.rongcapital.mkt.po;

import java.util.Date;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class ContactWayMap extends BaseQuery {
    private Integer id;

    private Integer contactWayId;

    private String contactWayName;

    private Byte status;

    private Date timeCondition;

    private String timeConditionAbbreviation;

    private Byte timeConditionStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getContactWayId() {
        return contactWayId;
    }

    public void setContactWayId(Integer contactWayId) {
        this.contactWayId = contactWayId;
    }

    public String getContactWayName() {
        return contactWayName;
    }

    public void setContactWayName(String contactWayName) {
        this.contactWayName = contactWayName == null ? null : contactWayName.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getTimeCondition() {
        return timeCondition;
    }

    public void setTimeCondition(Date timeCondition) {
        this.timeCondition = timeCondition;
    }

    public String getTimeConditionAbbreviation() {
        return timeConditionAbbreviation;
    }

    public void setTimeConditionAbbreviation(String timeConditionAbbreviation) {
        this.timeConditionAbbreviation = timeConditionAbbreviation == null ? null : timeConditionAbbreviation.trim();
    }

    public Byte getTimeConditionStatus() {
        return timeConditionStatus;
    }

    public void setTimeConditionStatus(Byte timeConditionStatus) {
        this.timeConditionStatus = timeConditionStatus;
    }
}
