package cn.rongcapital.mkt.vo.in;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/*************************************************
 * @功能简述: 系统标签标签值修改表单参数Vo
 * @项目名称: marketing cloud
 * @see:
 * @author: 王伟强
 * @version: 0.0.1
 * @date: 2016/12/13
 * @复审人:
 *************************************************/
@JsonIgnoreProperties(ignoreUnknown=true)
public class TagValueElement {
	
	private String startValue;
	
	private String endValue;
	
	
	public TagValueElement() {
		
	}

	public TagValueElement(String startValue, String endValue) {
		this.startValue = startValue;
		this.endValue = endValue;
	}

	public String getStartValue() {
		return startValue;
	}

	public void setStartValue(String startValue) {
		this.startValue = startValue;
	}

	public String getEndValue() {
		return endValue;
	}

	public void setEndValue(String endValue) {
		this.endValue = endValue;
	}
	
}
