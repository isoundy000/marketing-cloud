package cn.rongcapital.mkt.job.tag.service.impl;

/*************************************************
 * @功能简述: Redis Key常量类
 * @项目名称: marketing cloud
 * @see:
 * @author: 王伟强
 * @version: 0.0.1
 * @date: 2016/11/10
 * @复审人:
 *************************************************/
public class RedisKey {
	
	public static final String TAG_ID = "tagid";	//标签ID
	
	public static final String TAG_NAME = "tagname";//标签名称
	
	public static final String TAG_PATH = "tagpath";//标签路径
	
	public static final String TAG_VALUE = "tagvalue";//标签值
	
	public static final String TAG_VALUE_ID = "tagvalueid";//标签值ID
	
	public static final String COVER_COUNT = "covercount";//覆盖数
	
	public static final String IS_TAG_VALUE = "istagvalue";//是否是标签标识，1-标签，0-标签值
	
	public static final String OVER_UPDATE_TIME = "overupdatetime";//最后更新时间
	
	public static final String TAG_COVER_ID = "tagcoverid";//标签覆盖的人ID
	
	public static final String TAG_VALUE_ORDER = "tagvalueorder";//标签值排序
	
	public static final String ALL_DATAPARY_MID = "tagcoverid:all";//所有人ID
	
}
