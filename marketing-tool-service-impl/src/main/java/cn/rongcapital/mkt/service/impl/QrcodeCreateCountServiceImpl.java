package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.WechatQrcodeLogDao;
import cn.rongcapital.mkt.po.WechatQrcodeLog;
import cn.rongcapital.mkt.service.QrcodeCreateCountService;
import cn.rongcapital.mkt.vo.BaseOutput;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
public class QrcodeCreateCountServiceImpl implements QrcodeCreateCountService {
	@Autowired
	WechatQrcodeLogDao qrcodeDao;

	@Override
	@ReadWrite(type = ReadWriteType.READ)
	public BaseOutput getCreateCount(String batch_id) {
		WechatQrcodeLog qlog = new WechatQrcodeLog();
		qlog.setBatchId(batch_id);

		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		WechatQrcodeLog qrcodeLog = qrcodeDao.selectCountByBatchId(qlog);
		if (qrcodeLog != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("qrcode_succ", qrcodeLog.getSuccess());
			map.put("qrcode_fail", qrcodeLog.getTotalRows() - qrcodeLog.getSuccess());
			result.setTotal(1);
			result.getData().add(map);
		}

		return result;
	}
}
