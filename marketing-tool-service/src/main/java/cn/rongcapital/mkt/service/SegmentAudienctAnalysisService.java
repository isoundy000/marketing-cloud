package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * Created by ljk 2016-12-05
 * .
 */
public interface SegmentAudienctAnalysisService {

    BaseOutput getSegmentAudienctAnalysis(String tagId,Integer headId);

}
