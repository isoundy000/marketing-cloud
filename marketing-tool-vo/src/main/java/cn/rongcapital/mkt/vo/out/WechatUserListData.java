package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;

/**
 * Created by Yunfeng on 2016-7-26.
 */
public class WechatUserListData<T> {

    private String name;
    private ArrayList<T> valueData;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("value_data")
    public ArrayList<T> getValueData() {
        return valueData;
    }

    public void setValueData(ArrayList<T> valueData) {
        this.valueData = valueData;
    }
}
