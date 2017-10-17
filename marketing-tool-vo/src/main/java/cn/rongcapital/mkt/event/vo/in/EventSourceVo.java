/*************************************************
 * @功能简述: EventSourceVo
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2017/01/09
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.event.vo.in;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import cn.rongcapital.mkt.vo.BaseInput;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventSourceVo extends BaseInput {

    /**
     * 事件源标识符
     */
    @NotEmpty
    @Length(min = 1, max = 50)
    private String code;

    /**
     * 事件源名称
     */
    @NotEmpty
    @Length(min = 1, max = 50)
    private String name;

    /**
     * 事件源平台
     */
    @JsonProperty("platform_code")
    @NotEmpty
    private String platformCode;

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

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    @Override
    public String toString() {
        return "EventSourceVo [code=" + code + ", name=" + name + ", platformCode=" + platformCode + "]";
    }

}
