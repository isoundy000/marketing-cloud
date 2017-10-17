package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * Created by chengjincheng on 2016-8-12.
 */
public interface QrcodeUsedCountService {
	BaseOutput getListCount(String wxName);
}
