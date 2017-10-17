package cn.rongcapital.mkt.dao;

import java.util.List;

import cn.rongcapital.mkt.dao.base.BaseDao;
import cn.rongcapital.mkt.vo.out.SegmentSearchDownloadOut;

public interface SegmentSearchDownloadServiceDao extends BaseDao<DataPopulationDao> {
	List<SegmentSearchDownloadOut> getSegmentSearchDownload(List<Integer> head_ids);
}