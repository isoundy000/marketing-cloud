package cn.rongcapital.mkt.job.service.vo;

import cn.rongcapital.mkt.po.DataPartyTagRuleMap;
import cn.rongcapital.mkt.po.mongodb.Tag;

/**
 * Created by ethan on 16/7/29.
 */
public class TagRuleExtraVO {

    private DataPartyTagRuleMap ruleMap;

    private Tag tag;

    public DataPartyTagRuleMap getRuleMap() {
        return ruleMap;
    }

    public void setRuleMap(DataPartyTagRuleMap ruleMap) {
        this.ruleMap = ruleMap;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
