package cn.rongcapital.mkt.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.rongcapital.mkt.po.base.BaseTag;
import cn.rongcapital.mkt.service.FindCustomTagInfoService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.CustomTagDao;
import cn.rongcapital.mkt.dao.WechatChannelDao;
import cn.rongcapital.mkt.dao.WechatQrcodeDao;
import cn.rongcapital.mkt.po.CustomTag;
import cn.rongcapital.mkt.po.WechatChannel;
import cn.rongcapital.mkt.po.WechatQrcode;
import cn.rongcapital.mkt.service.WeixinQrcodeInfoService;
import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * 
 * 查询二维码详细信息
 * 
 * @author shuiyangyang
 * @Data 2016.08.24
 */
@Service
public class WeixinQrcodeInfoServiceImpl implements WeixinQrcodeInfoService {

	@Autowired
	private WechatQrcodeDao wechatQrcodeDao;

	@Autowired
	private WechatChannelDao wechatChannelDao;

	@Autowired
	CustomTagDao customTagDao;

	@Autowired
	private FindCustomTagInfoService findCustomTagInfoService;

	private static final String RELATION_TAG_SEPARATOR = ";";

	@Override
	public BaseOutput getWeiXinQrocdeInfo(String qrcodeId) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ONE, null);
		
		WechatQrcode wechatQrcode = new WechatQrcode();
		wechatQrcode.setId(Integer.valueOf(qrcodeId));

		List<WechatQrcode> wechatQrcodeLists = wechatQrcodeDao.selectList(wechatQrcode);
		// 如果没有查询结果 设置错误
		if (wechatQrcodeLists == null || wechatQrcodeLists.isEmpty()) {
			result.setCode(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getCode());
			result.setMsg(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getMsg());
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			wechatQrcode = wechatQrcodeLists.get(0);
			
			this.getWechatQrcodeMap(map,wechatQrcode);
			
			result.getData().add(map);
		}

		return result;
	}

	/**
	 * 构建输出结果
	 * @param map
	 * @param wechatQrcode
	 */
	private void getWechatQrcodeMap(Map<String, Object> map, WechatQrcode wechatQrcode) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		if(wechatQrcode != null){
			map.put("wxmp_name", wechatQrcode.getWxName());

			// 根据渠道号获取渠道名
			WechatChannel wechatChannel = new WechatChannel();
			wechatChannel.setId(wechatQrcode.getChCode());
			List<WechatChannel> wechatChannelLists = wechatChannelDao.selectList(wechatChannel);
			if (!(wechatChannelLists == null) && !wechatChannelLists.isEmpty()) {
				map.put("ch_name", wechatChannelLists.get(0).getChName());
				map.put("ch_id", wechatChannelLists.get(0).getId());
			} else {
				map.put("ch_name", "");// 查询不到的时候默认传空字符串
				map.put("ch_id", "");
			}
			//微信公众号名称
			map.put("qrcode_name", wechatQrcode.getQrcodeName());
			// 微信公众号
			map.put("wx_acct", wechatQrcode.getWxAcct());

			// create_time为空检查
			if (wechatQrcode.getCreateTime() != null) {
				map.put("create_time", sdf.format(wechatQrcode.getCreateTime()));
			} else {
				map.put("create_time", "");
			}
			// 日期格式化处理
			Date expirationTime = wechatQrcode.getExpirationTime();
			if(expirationTime == null ){
				map.put("expiration_time", "");
			}else{
				map.put("expiration_time", sdf.format(expirationTime));
			}
			//人群信息
			if (wechatQrcode.getAudienceName() != null) {
				map.put("fixed_audience", wechatQrcode.getAudienceName());
			} else {
				map.put("fixed_audience", "");
			}
			// 关联标签
			String relatedTag = wechatQrcode.getRelatedTags();
			if(relatedTag != null){
				BaseTag baseTag = null;
				if(relatedTag.contains(";")){
					List<Map<String,Object>> returnDataList = new ArrayList<>();
					String[] tagIds = relatedTag.split(";");
					for(String tagId : tagIds){
						baseTag = findCustomTagInfoService.findCustomTagInfoByTagId(tagId);
						if(baseTag == null) continue;

						Map<String,Object> dataMap = new HashMap<>();
						dataMap.put("id", baseTag.getTagId());
						dataMap.put("name", baseTag.getTagName());
						returnDataList.add(dataMap);
					}
					map.put("association_tags", returnDataList);
				}else{
					baseTag = findCustomTagInfoService.findCustomTagInfoByTagId(relatedTag);
					List<Map<String,Object>> returnDataList = new ArrayList<>();
					if(baseTag != null){
						Map<String,Object> dataMap = new HashMap<>();
						dataMap.put("id", baseTag.getTagId());
						dataMap.put("name",baseTag.getTagName());
						returnDataList.add(dataMap);
					}

					map.put("association_tags", returnDataList);
				}
			}else {
				map.put("association_tags", new ArrayList<String>());
			}

			if (wechatQrcode.getComments() != null) {
				map.put("comment", wechatQrcode.getComments());
			} else {
				map.put("comment", "");
			}
		}
		
	}

}
