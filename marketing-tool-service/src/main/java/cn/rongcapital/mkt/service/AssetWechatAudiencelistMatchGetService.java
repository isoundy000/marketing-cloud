/**
 * 
 */
package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * @author shuiyangyang
 *
 */
public interface AssetWechatAudiencelistMatchGetService {
	/**
	 * 精确查询固定人群是否存在
	 * 接口：mkt.asset.wechat.audiencelist.match.get
	 * @param audienceName
	 * @return
	 */
	BaseOutput assetWechatAudiencelistMatchGet(String audienceName);
}
