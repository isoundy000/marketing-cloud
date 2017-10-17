package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.out.ContactsCommitCountListOutput;

/**
 * Created by Yunfeng on 2016-8-16.
 */
public interface ContactsCommitCountGetService {

    ContactsCommitCountListOutput getContactsCommitCount(Long contactId);

}
