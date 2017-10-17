package cn.rongcapital.mkt.vo.in;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotEmpty;

/*************************************************
 * @功能简述: 系统标签值修改传入参数实体
 * @项目名称: marketing cloud
 * @see:
 * @author: 王伟强
 * @version: 0.0.1
 * @date: 2016/12/13
 * @复审人:
 *************************************************/
@JsonIgnoreProperties(ignoreUnknown=true)
public class TagValueUpdateIn {
	@NotEmpty
	private String tagId;
	@NotEmpty
	private List<TagValueElement> elements;

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public List<TagValueElement> getElements() {
		return elements;
	}

	public void setElements(List<TagValueElement> elements) {
		this.elements = elements;
	}
}
