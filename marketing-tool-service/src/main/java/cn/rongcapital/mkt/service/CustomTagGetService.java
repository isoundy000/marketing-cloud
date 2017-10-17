package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;


public interface CustomTagGetService {

    /**
     * mkt.tag.custom.list.get
     * 
     * @功能简述 : 自定义标签列表
     * @author wangyi
     * @param method
     * @param userToken
     * @param index
     * @param size
     * @return
     */
	public BaseOutput getCustomTagList(String method, String userToken, Integer index, Integer size);

}
