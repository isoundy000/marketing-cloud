package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;
/*************************************************
 * @功能简述: 视图结果实体
 * @项目名称: marketing cloud
 * @see: 
 * @author: 王伟强
 * @version: 0.0.1
 * @date: 2016/10/13
 * @复审人: 
*************************************************/
public class SystemTagResult extends BaseQuery {
	
	private static final long serialVersionUID = 1L;

	private Integer keyId;		//映射Mongo中mId

//    private String tagValue;	//标签值
    
    private String tag_value;
    

	public String getTag_value() {
		return tag_value;
	}

	public void setTag_value(String tag_value) {
		this.tag_value = tag_value;
	}

	public Integer getKeyId() {
		return keyId;
	}

	public void setKeyId(Integer keyId) {
		this.keyId = keyId;
	}

//	public String getTagValue() {
//		return tagValue;
//	}
//
//	public void setTagValue(String tagValue) {
//		this.tagValue = tagValue;
//	}
   
}
