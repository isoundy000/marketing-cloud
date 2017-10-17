package cn.rongcapital.mkt.job.service;

import cn.rongcapital.mkt.job.service.vo.DataPartySyncVO;

/**
 * Created by ethan on 16/6/30.
 */
public interface DataPartySyncService<T> {

    int queryTotalCount();

    void querySyncData(Integer totalSize, Integer pageSize);

    void doSync();

    void doSyncAfter(DataPartySyncVO<T> dataPartySyncVO);

}
