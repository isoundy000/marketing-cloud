package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.FileTagUpdateIn;

/**
 * Created by Yunfeng on 2016-6-25.
 */
public interface FileTagUpdateService {
    BaseOutput updateFileTag(FileTagUpdateIn fileTagUpdateIn);
}
