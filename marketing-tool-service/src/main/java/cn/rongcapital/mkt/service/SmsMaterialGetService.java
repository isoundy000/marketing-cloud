package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * Created by byf on 11/9/16.
 */
public interface SmsMaterialGetService {
    BaseOutput getSmsMaterialById(Long id);
    BaseOutput getSmsMaterialListByKeyword(String searchWord,Integer channelType,Integer smsType, Integer index,Integer size);
    BaseOutput getSmsMaterialCount(Integer channelType);
}
