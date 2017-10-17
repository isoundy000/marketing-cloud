/*************************************************
 * @功能简述: EventGeneralOut
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2017/01/09
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.event.vo.out;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventGeneralOut {

    private Long id;

    private String name;

    private String code;

    @JsonProperty("source_id")
    private Long sourceId;

    @JsonProperty("object_id")
    private Long objectId;

    @JsonProperty("object_name")
    private String objectName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    @Override
    public String toString() {
        return "EventGeneralOut [id=" + id + ", name=" + name + ", code=" + code + ", sourceId=" + sourceId
                + ", objectId=" + objectId + ", objectName=" + objectName + "]";
    }

}
