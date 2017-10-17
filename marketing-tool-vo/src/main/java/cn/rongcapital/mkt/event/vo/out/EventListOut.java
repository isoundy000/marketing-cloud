/*************************************************
 * @功能及特点的描述简述: 分页查询获取事件列表(支持模糊查询)结果类
 * 该类被编译测试过
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017-1-7 
 * 最后修改日期：2017-1-7 
 * @复审人：
 *************************************************/ 
package cn.rongcapital.mkt.event.vo.out;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import cn.rongcapital.mkt.vo.BaseOutput;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventListOut extends BaseOutput{
    
    private List<EventListItemOut> listItems = new ArrayList<>();

    public EventListOut() {
        super();
    }

    /**
     * @param code
     * @param msg
     * @param total
     */
    public EventListOut(int code, String msg, int total) {
        super(code, msg, total, null);
    }
    
    @JsonProperty("data")
    public List<EventListItemOut> getListItems() {
        return listItems;
    }

    public void setListItems(List<EventListItemOut> listItems) {
        this.listItems = listItems;
    }
}
