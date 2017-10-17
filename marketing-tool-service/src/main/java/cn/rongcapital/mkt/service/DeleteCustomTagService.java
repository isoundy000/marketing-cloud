package cn.rongcapital.mkt.service;

/**
 * Created by Yunfeng on 2016-9-27.
 */
public interface DeleteCustomTagService {

    void deleteCustomTagByTagNameAndTagSource(String tagName,String tagSource);

    void deleteCustomTagLeafByTagId(String tagId);
}
