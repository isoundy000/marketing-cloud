/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统)
 * @author:liuhaizhan
 * @version: 版本v1.6
 * @date(创建、开发日期)：2016年12月13日 
 * @date(最后修改日期)：2016年12月13日 
 * @复审人：
 *************************************************/

package cn.rongcapital.mkt.material.coupon.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

public class PropertiesOut {

    @JsonProperty("code")
    private String code;
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
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
}
