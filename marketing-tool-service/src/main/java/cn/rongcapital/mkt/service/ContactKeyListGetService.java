package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.out.GetContactKeyListOutput;

/**
 * Created by Yunfeng on 2016-8-15.
 */
public interface ContactKeyListGetService {

    GetContactKeyListOutput getContactKeyList(Long contactId);

}
