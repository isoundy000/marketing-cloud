package cn.rongcapital.mkt.vo.out;

import cn.rongcapital.mkt.vo.BaseOutput;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yunfeng on 2016-7-26.
 */
public class WechatUserListOut extends BaseOutput{

    private List<WechatUserListData> dataCustom = new ArrayList<WechatUserListData>();

    public WechatUserListOut(int code, String msg, int total) {
        super(code, msg, total, null);
    }

    @JsonProperty("data")
    public List<WechatUserListData> getDataCustom() {
        return dataCustom;
    }

    public void setDataCustom(List<WechatUserListData> dataCustom) {
        this.dataCustom = dataCustom;
    }
}
