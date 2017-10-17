/*************************************************
 * @功能简述: EventObjectPropsOut
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2017/01/09
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.event.vo.out;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventObjectPropsOut {


    private String name;

    private String label;

    private List<String> values = new ArrayList<String>();

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

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
    
    public void addValue(String value) {
        this.values.add(value);
    }

    @Override
    public String toString() {
        return "EventObjectPropsOut [name=" + name + ", label=" + label + ", values=" + values + "]";
    }



}
