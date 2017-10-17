package cn.rongcapital.mkt.po.mongodb.event;
import java.io.Serializable;
import java.util.Map;

import org.bson.types.BSONTimestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author liuhaizhan
 *
 */
@Document(collection = "event_behavior")
public class EventBehavior implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Field(value = "time")
    private Long time;

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    @Field(value = "object")
    private Map<String, Object> object;

    @Field(value = "subject")
    private Map<String, Object> subject;

    @Field(value = "event")
    private Map<String, Object> event;

    @Field(value = "subscribed")
    private Boolean subscribed;

    public Map<String, Object> getObject() {
        return object;
    }

    public void setObject(Map<String, Object> object) {
        this.object = object;
    }

    public Map<String, Object> getSubject() {
        return subject;
    }

    public void setSubject(Map<String, Object> subject) {
        this.subject = subject;
    }

    public Map<String, Object> getEvent() {
        return event;
    }

    public void setEvent(Map<String, Object> event) {
        this.event = event;
    }

    public Boolean getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(Boolean subscribed) {
        this.subscribed = subscribed;
    }

    @Override
    public String toString() {
        return "EventBehavior [object=" + object + ", subject=" + subject
                + ", event=" + event + ", subscribed=" + subscribed + "]";
    }



}
