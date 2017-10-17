package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class IllegalDataSummary extends BaseQuery {
    private Integer id;

    private String emailCount;

    private String monileCount;

    private String needMergeCount;

    private String inspectTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmailCount() {
        return emailCount;
    }

    public void setEmailCount(String emailCount) {
        this.emailCount = emailCount == null ? null : emailCount.trim();
    }

    public String getMonileCount() {
        return monileCount;
    }

    public void setMonileCount(String monileCount) {
        this.monileCount = monileCount == null ? null : monileCount.trim();
    }

    public String getNeedMergeCount() {
        return needMergeCount;
    }

    public void setNeedMergeCount(String needMergeCount) {
        this.needMergeCount = needMergeCount == null ? null : needMergeCount.trim();
    }

    public String getInspectTime() {
        return inspectTime;
    }

    public void setInspectTime(String inspectTime) {
        this.inspectTime = inspectTime == null ? null : inspectTime.trim();
    }
}
