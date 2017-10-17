package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * Created by chengjincheng on 2016-8-15.
 */
public interface QrcodeCreateCountService {
	BaseOutput getCreateCount(String batch_id);
}
