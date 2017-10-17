/*************************************************
 * @功能简述: QrcodeUsedCountService实现类
 * @see: MkyApi
 * @author: 程金成
 * @version: 1.0
 * @date: 2016/8/11
*************************************************/

package cn.rongcapital.mkt.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.WechatQrcodeDao;
import cn.rongcapital.mkt.po.WechatQrcode;
import cn.rongcapital.mkt.service.QrcodeUsedCountService;
import cn.rongcapital.mkt.vo.BaseOutput;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
public class QrcodeUsedCountServiceImpl implements QrcodeUsedCountService {

	@Autowired
	WechatQrcodeDao wechatQrcodeDao;
	
	@Override
	@ReadWrite(type=ReadWriteType.READ)
	public BaseOutput getListCount(String wxName)
	{
		WechatQrcode wQrcode = new WechatQrcode();
		wQrcode.setWxName(wxName);
		wQrcode.setStatus(Integer.valueOf(1).byteValue());
		
		int listCount = wechatQrcodeDao.selectListCount(wQrcode);
		
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO,null);
		
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("qrcode_used", listCount);
		result.getData().add(map);
		result.setTotal(1);
		
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
//		Date now = new Date();
//		String newDate = format.format(now);
//		List<String> list = new ArrayList<String>();
		
		
		
		return result;
	}
}
