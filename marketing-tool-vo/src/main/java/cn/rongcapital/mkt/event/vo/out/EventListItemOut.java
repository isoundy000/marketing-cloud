/*************************************************
 * @功能及特点的描述简述: 事件列表查询结果Item类
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017-1-7 
 * @date(最后修改日期)：2017-1-7 
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.event.vo.out;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventListItemOut {
    private Long id;

    private String code;

    private String name;

    private Long sourceId;

    private Long objectId;

    private Byte status;

    private Date createTime;

    private Date updateTime;

    private Boolean systemEvent;

    private Boolean subscribed;

    private String registerOpportunity;

    private String triggerOpportunity;

    private Boolean unsubscribable;

    private String attributes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getSystemEvent() {
        return systemEvent;
    }

    public void setSystemEvent(Boolean systemEvent) {
        this.systemEvent = systemEvent;
    }

    public Boolean getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(Boolean subscribed) {
        this.subscribed = subscribed;
    }

    public String getRegisterOpportunity() {
        return registerOpportunity;
    }

    public void setRegisterOpportunity(String registerOpportunity) {
        this.registerOpportunity = registerOpportunity;
    }

    public String getTriggerOpportunity() {
        return triggerOpportunity;
    }

    public void setTriggerOpportunity(String triggerOpportunity) {
        this.triggerOpportunity = triggerOpportunity;
    }

    public Boolean getUnsubscribable() {
        return unsubscribable;
    }

    public void setUnsubscribable(Boolean unsubscribable) {
        this.unsubscribable = unsubscribable;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    
}
