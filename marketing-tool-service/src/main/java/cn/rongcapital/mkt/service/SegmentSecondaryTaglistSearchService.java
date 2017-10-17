package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.TagListSecondarySearchIn;

/**
 * Created by byf on 11/9/16.
 */
public interface SegmentSecondaryTaglistSearchService {
    BaseOutput searchSegmentSecondaryTaglist(TagListSecondarySearchIn tagListSecondarySearchIn);
}
