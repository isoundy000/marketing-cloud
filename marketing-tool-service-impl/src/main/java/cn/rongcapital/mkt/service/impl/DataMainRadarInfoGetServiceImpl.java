/*************************************************
 * @功能简述: 查询某联系人雷达图数据 的业务类实现 
 * @see MktApi：
 * @author: xuning
 * @version: 1.0
 * @date：2016-06-07
 *************************************************/
package cn.rongcapital.mkt.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.PartyRadarDao;
import cn.rongcapital.mkt.po.PartyRadar;
import cn.rongcapital.mkt.service.DataMainRadarInfoGetService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class DataMainRadarInfoGetServiceImpl implements DataMainRadarInfoGetService{
	@Autowired
	private PartyRadarDao partyRadarDao;
	
	@Override
	public BaseOutput getRadarInfoByContactId(String contactId) {
		//根据标签组名模糊查询标签组
		PartyRadar param = new PartyRadar();
		param.setContactId(contactId);
		List<PartyRadar> resList = partyRadarDao.selectList(param);
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				   ApiErrorCode.SUCCESS.getMsg(),
				   ApiConstant.INT_ZERO,null);
		if(null != resList && !resList.isEmpty()){
			result.setTotal(1);
			DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			PartyRadar po = resList.get(0);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("contact_name", po.getContactName());
			map.put("recent_buy_time", fmt.format(po.getRecentBuyTime()));
			map.put("buy_rate", po.getBuyRate());
			map.put("goods_types", po.getGoodsTypes());
			map.put("average_trans_amt", po.getAverageTransAmt().toString());
			map.put("top_trans_amt", po.getTopTransAmt().toString());
			result.getData().add(map);
		}
		return result;
	}
}
