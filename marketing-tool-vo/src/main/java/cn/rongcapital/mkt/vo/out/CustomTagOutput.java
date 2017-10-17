package cn.rongcapital.mkt.vo.out;

import cn.rongcapital.mkt.vo.BaseOutput;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yunfeng on 2016-9-28.
 */
public class CustomTagOutput extends BaseOutput{

    private List<CustomTagShown> customTagShownList = new ArrayList<>();
    private List<CustomTagColumnShown> customTagColumnShownList = new ArrayList<>();

    public CustomTagOutput(int code, String msg, int total){
        super(code,msg,total,null);
    }

    @JsonProperty("data")
    public List<CustomTagShown> getCustomTagShownList() {
        return customTagShownList;
    }

    public void setCustomTagShownList(List<CustomTagShown> customTagShownList) {
        this.customTagShownList = customTagShownList;
    }

    @JsonProperty("col_names")
    public List<CustomTagColumnShown> getCustomTagColumnShownList() {
        return customTagColumnShownList;
    }

    public void setCustomTagColumnShownList(List<CustomTagColumnShown> customTagColumnShownList) {
        this.customTagColumnShownList = customTagColumnShownList;
    }
}
