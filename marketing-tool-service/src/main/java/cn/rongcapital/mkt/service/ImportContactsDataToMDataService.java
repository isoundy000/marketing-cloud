package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.ImportContactsDataIn;

/**
 * Created by Yunfeng on 2016-8-22.
 */
public interface ImportContactsDataToMDataService {

    BaseOutput importContactsDataToMData(Long contactId);
}
