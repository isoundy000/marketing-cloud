package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.CustomTagDeleteIn;


public interface CustomTagDeleteService {

    /**
     * mkt.tag.custom.delete
     * 
     * @功能简述 : 删除某个自定义标签
     * @author wangyi
     * @param method
     * @param userToken
     * @param tag_id
     * @return
     */
	public BaseOutput deleteCustomTag(CustomTagDeleteIn body);

}
