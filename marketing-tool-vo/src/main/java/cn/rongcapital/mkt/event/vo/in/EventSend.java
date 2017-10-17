/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统)
 * @author:liuhaizhan
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017年1月6日 
 * @date(最后修改日期)：2017年1月6日 
 * @复审人：
 *************************************************/

package cn.rongcapital.mkt.event.vo.in;

import java.util.Map;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonProperty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class EventSend {

    @NotNull
    private Map<String,Object> subject;
    
    public Map<String, Object> getSubject() {
        return subject;
    }
    public void setSubject(Map<String, Object> subject) {
        this.subject = subject;
    }
    public Long getTime() {
        return time;
    }
    public void setTime(Long time) {
        this.time = time;
    }
    @NotNull
    private Long time;
    //@NotNull
    private Map<String,Object> object;
    
    private Boolean subscribed;

    public Map<String, Object> getObject() {
        return object;
    }
    public void setObject(Map<String, Object> object) {
        this.object = object;
    }
    public Boolean getSubscribed() {
        return subscribed;
    }
    public void setSubscribed(Boolean subscribed) {
        this.subscribed = subscribed;
    }
    
    
}
