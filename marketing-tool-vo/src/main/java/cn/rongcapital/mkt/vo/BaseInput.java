/*************************************************
 * @功能简述: 输入参数VO基类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 宋世涛
 * @version: 0.0.1
 * @date: 2016/5/16
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.vo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class BaseInput {

//    @NotEmpty
//	private String method;
    
    @NotEmpty
    private String ver;

	private int index;
	private int size;
	  
//	@JsonProperty("method")
//	public String getMethod() {
//		return method;
//	}
//	public void setMethod(String method) {
//	  this.method = method;
//	}
	@JsonProperty("ver")
	public String getVer() {
		return ver;
	}
	public void setVer(String ver) {
		this.ver = ver;
	}

	@JsonProperty("index")
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@JsonProperty("size")
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
