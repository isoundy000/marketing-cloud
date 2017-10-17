/*************************************************
 * @功能简述: 获取系统标签内容列表
 * @see MktApi
 * @author: nianjun
 * @version: 1.0 @date：2016-06-24
 *************************************************/
package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface TagSystemListGetService {

    /**
     * mkt.tag.system.list.get
     * 
     * @author nianjun
     * @功能简述 : 获取系统标签内容列表
     * @param method
     * @param userToken
     * @param tagGroupId
     * @param index
     * @param size
     * @return
     */
    public BaseOutput getTagcount(String method, String userToken, Integer tagGroupId, Integer index, Integer size);
    
    /**
     * 获取系统标签内容列表
     * 
     * @author congshulin
     * @功能简述 : 获取系统标签内容列表
     * @param method
     * @param userToken
     * @param tagGroupId
     * @param index
     * @param size
     * @return
     */
    public BaseOutput getMongoTagList(String method, String userToken, String tagGroupId, Integer index, Integer size);

}
