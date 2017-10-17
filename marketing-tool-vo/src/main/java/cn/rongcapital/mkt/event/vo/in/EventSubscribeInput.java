/*************************************************
 * @功能及特点的描述简述: 事件订阅input bean
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.7
 * @date(创建、开发日期)：2017-1-11
 * @date(最后修改日期)：2017-1-11
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.event.vo.in;

import cn.rongcapital.mkt.vo.BaseInput;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventSubscribeInput extends BaseInput {
    @NotNull
    @JsonProperty("event_id")
    private long eventId;
    @NotNull
    private boolean subscribe;

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public boolean isSubscribe() {
        return subscribe;
    }

    public void setSubscribe(boolean subscribe) {
        this.subscribe = subscribe;
    }
}
