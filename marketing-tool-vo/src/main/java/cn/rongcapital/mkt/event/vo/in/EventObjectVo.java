/*************************************************
 * @功能简述: EventObjectVo
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2017/01/09
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.event.vo.in;

import java.util.List;

import javax.validation.Valid;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import cn.rongcapital.mkt.vo.BaseInput;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventObjectVo extends BaseInput {

    /**
     * 客体类别标识
     */
    @NotEmpty
    @Length(min = 1, max = 50)
    private String code;

    /**
     * 客体类别名称
     */
    @NotEmpty
    @Length(min = 1, max = 50)
    private String name;

    /**
     * 客体类别名称
     */
    @NotEmpty
    @Length(min = 1, max = 50)
    @JsonProperty("instance_name_prop")
    private String instanceNameProp;

    /**
     * 客体类别对象名称
     */
    @NotEmpty
    @Length(min = 1, max = 50)
    @JsonProperty("instance_name_label")
    private String instanceNameLabel;

    /**
     * 客体属性
     */
    @JsonProperty("attributes")
    @Valid
    private List<EventObjecAttribure> attributes;


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


    public String getInstanceNameProp() {
        return instanceNameProp;
    }


    public void setInstanceNameProp(String instanceNameProp) {
        this.instanceNameProp = instanceNameProp;
    }


    public String getInstanceNameLabel() {
        return instanceNameLabel;
    }


    public void setInstanceNameLabel(String instanceNameLabel) {
        this.instanceNameLabel = instanceNameLabel;
    }


    public List<EventObjecAttribure> getAttributes() {
        return attributes;
    }


    public void setAttributes(List<EventObjecAttribure> attributes) {
        this.attributes = attributes;
    }


    @Override
    public String toString() {
        return "EventObjectVo [code=" + code + ", name=" + name + ", instanceNameProp=" + instanceNameProp
                + ", instanceNameLabel=" + instanceNameLabel + ", attributes=" + attributes + "]";
    }

}
