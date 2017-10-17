package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.sms.in.SmsMaterialDeleteIn;
import cn.rongcapital.mkt.vo.sms.in.SmsMaterialIn;

public interface SmsMaterialService {

	/**
	 * 增加素材
	 * @param smsMaterialIn
	 * @return
	 */
	BaseOutput insertOrUpdateSmsMaterial(SmsMaterialIn smsMaterialIn);
	
	/**
	 * 删除素材
	 * @param id
	 * @return
	 */
	BaseOutput deleteSmsMaterial(SmsMaterialDeleteIn smsMaterialDeleteIn);

	/**
	 * 判断素材是否可以删除
	 * @param id
	 * @return
	 */
	boolean smsMaterialValidate(Integer id);
}
