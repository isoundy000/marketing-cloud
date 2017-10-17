package cn.rongcapital.mkt.vo.out;

import cn.rongcapital.mkt.vo.BaseOutput;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;

/**
 * Created by Yunfeng on 2016-8-26.
 */
public class GetContactListKeyListOut extends BaseOutput{

    private Integer showKeylistWindowStatus;
    private ArrayList<ImportContactKeyInfo> keyInfoList = new ArrayList<ImportContactKeyInfo>();

    public GetContactListKeyListOut(int code, String msg, int total) {
        super(code, msg, total, null);
    }

    @JsonProperty("show_keylist_window_status")
    public Integer getShowKeylistWindowStatus() {
        return showKeylistWindowStatus;
    }

    public void setShowKeylistWindowStatus(Integer showKeylistWindowStatus) {
        this.showKeylistWindowStatus = showKeylistWindowStatus;
    }

    @JsonProperty("data")
    public ArrayList<ImportContactKeyInfo> getKeyInfoList() {
        return keyInfoList;
    }

    public void setKeyInfoList(ArrayList<ImportContactKeyInfo> keyInfoList) {
        this.keyInfoList = keyInfoList;
    }
}
