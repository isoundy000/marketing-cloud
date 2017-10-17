package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SaveContactListKeysIn;

/**
 * Created by Yunfeng on 2016-8-19.
 */
public interface ContactListKeysSaveService {
    BaseOutput saveContactListKeys(SaveContactListKeysIn saveContactListKeysIn);
}
