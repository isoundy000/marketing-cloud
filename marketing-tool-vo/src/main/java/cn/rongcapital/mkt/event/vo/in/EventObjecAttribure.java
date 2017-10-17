/*************************************************
 * @功能简述: EventObjecAttribure
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2017/01/09
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.event.vo.in;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventObjecAttribure {
    /**
     * 属性值
     */
    @NotEmpty
    @Length(min = 1, max = 50)
    private String name;

    /**
     * 名称
     */
    @NotEmpty
    @Length(min = 1, max = 50)
    private String label;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "EventObjecAttribure [name=" + name + ", label=" + label + "]";
    }


}
