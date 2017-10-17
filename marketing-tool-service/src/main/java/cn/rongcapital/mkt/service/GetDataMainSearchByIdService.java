package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * Created by Yunfeng on 2016-6-8.
 */
public interface GetDataMainSearchByIdService {
    BaseOutput searchDataMainById(int id,int type);
}
