/**
 * 
 */
package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.WechatQrcodeDao;
import cn.rongcapital.mkt.po.WechatQrcode;
import cn.rongcapital.mkt.service.WeixinQrcodeMatchGetService;
import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * @author shuiyangyang
 * @Date 2016.08.25
 */
@Service
public class WeixinQrcodeMatchGetServiceImpl implements WeixinQrcodeMatchGetService {
    
    private static final Byte QRCODE_NOT_USE = 0;
    private static final Byte QRCODE_USE = 1;
    private static final Byte QRCODE_DEL = 2;
    private static final Byte QRCODE_INVALID = 3;

	@Autowired
	private WechatQrcodeDao wechatQrcodeDao;

	/**
	 * 精确查询微信二维码名称是否存在 接口：mkt.weixin.qrcode.match.get
	 * 
	 * @author shuiyangyang
	 * @Date 2016.08.25
	 */
	@Override
	public BaseOutput weixinQrcodeMatchGet(String qrcodeName, String channelId, String wxName) {

		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ONE, null);

		WechatQrcode wechatQrcode = new WechatQrcode();
		wechatQrcode.setWxName(wxName);
		wechatQrcode.setChCode(Integer.parseInt(channelId));
		wechatQrcode.setQrcodeName(qrcodeName);

		List<WechatQrcode> wechatQrcodeLists = wechatQrcodeDao.selectList(wechatQrcode);

		Map<String, Object> map = new HashMap<String, Object>();
		
        map.put("is_match", 0);
        map.put("id", "");
        
		if (CollectionUtils.isNotEmpty(wechatQrcodeLists)) {
		    // 如果二维码为删除状态和未使用状态，则认为该二维码名不存在
		    for(int i = 0; i < wechatQrcodeLists.size(); i++) {
    		    Byte status = wechatQrcodeLists.get(i).getStatus();
    		    if(QRCODE_USE.equals(status) || QRCODE_INVALID.equals(status)) {
    		        map.put("is_match", 1);
    	            map.put("id", wechatQrcodeLists.get(i).getId());
    		    }
		    }
		}

		result.getData().add(map);

		return result;
	}
	

}
