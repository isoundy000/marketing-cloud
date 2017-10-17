package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.DataMainSearchIn;

/**
 * Created by Yunfeng on 2016-6-7.
 */
public interface GetDataMainSearchService {
    BaseOutput searchDataMain(DataMainSearchIn dataMainSearchIn);
}
