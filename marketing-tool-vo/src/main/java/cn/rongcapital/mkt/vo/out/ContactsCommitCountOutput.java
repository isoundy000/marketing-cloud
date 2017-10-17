package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Yunfeng on 2016-8-16.
 */
public class ContactsCommitCountOutput {

    private Integer commitCount;
    private Integer todayCount;
    private Integer pageViews;
    private Integer mdCount;
    private Integer nonmdCount;

    @JsonProperty("commit_count")
    public Integer getCommitCount() {
        return commitCount;
    }

    public void setCommitCount(Integer commitCount) {
        this.commitCount = commitCount;
    }

    @JsonProperty("today_count")
    public Integer getTodayCount() {
        return todayCount;
    }

    public void setTodayCount(Integer todayCount) {
        this.todayCount = todayCount;
    }

    @JsonProperty("page_views")
    public Integer getPageViews() {
        return pageViews;
    }

    public void setPageViews(Integer pageViews) {
        this.pageViews = pageViews;
    }

    @JsonProperty("md_count")
    public Integer getMdCount() {
        return mdCount;
    }

    public void setMdCount(Integer mdCount) {
        this.mdCount = mdCount;
    }

    @JsonProperty("nonmd_count")
    public Integer getNonmdCount() {
        return nonmdCount;
    }

    public void setNonmdCount(Integer nonmdCount) {
        this.nonmdCount = nonmdCount;
    }
}
