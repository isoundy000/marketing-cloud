/*************************************************
 * @功能简述: 
 * @项目名称: marketing cloud
 * @see:
 * @author: shanjingqi
 * @version: 0.0.1
 * @date: 20176/1/10
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.event.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

public class EventSourceListOut {
	@JsonProperty("id")
	private Long id;
	@JsonProperty("name")
	private String name;
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
