package cn.rongcapital.mkt.job.service;

public interface SystemTagSynchService {
	
	/**
	 * 初始化MySQL中标签属性表
	 * @param tagId
	 */
	void initTagValueCount(String tagId);

}
