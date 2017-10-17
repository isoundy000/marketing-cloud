package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by byf on 10/28/16.
 */
public class WechatAnalysisDateOut {

    private String wxAcct;
    private String wxmpName;
    private String chName;
    private String chCode;
    private TodayOut todayOut;
    private YestodayOut yesDdayOut;
    private Day7Out day7Out;
    private Day30Out day30Out;
    private RecordScopeOut recordScopeOut;

    @JsonProperty("wx_acct")
    public String getWxAcct() {
        return wxAcct;
    }

    public void setWxAcct(String wxAcct) {
        this.wxAcct = wxAcct;
    }

    @JsonProperty("wxmp_name")
    public String getWxmpName() {
        return wxmpName;
    }

    public void setWxmpName(String wxmpName) {
        this.wxmpName = wxmpName;
    }

    @JsonProperty("ch_name")
    public String getChName() {
        return chName;
    }

    public void setChName(String chName) {
        this.chName = chName;
    }

    @JsonProperty("ch_code")
    public String getChCode() {
        return chCode;
    }

    public void setChCode(String chCode) {
        this.chCode = chCode;
    }

    @JsonProperty("Today")
    public TodayOut getTodayOut() {
        return todayOut;
    }

    public void setTodayOut(TodayOut todayOut) {
        this.todayOut = todayOut;
    }

    @JsonProperty("Yestoday")
    public YestodayOut getYesDdayOut() {
        return yesDdayOut;
    }

    public void setYesDdayOut(YestodayOut yesDdayOut) {
        this.yesDdayOut = yesDdayOut;
    }

    @JsonProperty("Day7")
    public Day7Out getDay7Out() {
        return day7Out;
    }

    public void setDay7Out(Day7Out day7Out) {
        this.day7Out = day7Out;
    }

    @JsonProperty("Day30")
    public Day30Out getDay30Out() {
        return day30Out;
    }

    public void setDay30Out(Day30Out day30Out) {
        this.day30Out = day30Out;
    }

    @JsonProperty("RecordScope")
    public RecordScopeOut getRecordScopeOut() {
        return recordScopeOut;
    }

    public void setRecordScopeOut(RecordScopeOut recordScopeOut) {
        this.recordScopeOut = recordScopeOut;
    }
}
