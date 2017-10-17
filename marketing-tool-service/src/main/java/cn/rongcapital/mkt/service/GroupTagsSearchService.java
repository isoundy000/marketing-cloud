package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.out.SerarchTagGroupTagsOut;

public interface GroupTagsSearchService {

	public SerarchTagGroupTagsOut groupTagsSearch(String method,String userToken,String tagGroupName);
}
