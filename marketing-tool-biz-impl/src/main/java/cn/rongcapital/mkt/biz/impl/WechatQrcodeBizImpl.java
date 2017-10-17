package cn.rongcapital.mkt.biz.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.stream.FileImageOutputStream;

import cn.rongcapital.mkt.dao.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.tagsin.tutils.http.HttpResult;
import com.tagsin.tutils.http.Requester;
import com.tagsin.tutils.http.Requester.Method;
import com.tagsin.tutils.json.JsonUtils;
import com.tagsin.wechat_sdk.App;

import com.tagsin.wechat_sdk.token.TokenType;

import cn.rongcapital.mkt.biz.WechatQrcodeBiz;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.TagSourceEnum;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.common.util.ImageCompressUtil;
import cn.rongcapital.mkt.common.util.NumUtil;
import cn.rongcapital.mkt.po.CustomTag;
import cn.rongcapital.mkt.po.WebchatAuthInfo;
import cn.rongcapital.mkt.po.WechatChannel;
import cn.rongcapital.mkt.po.WechatInterfaceLog;
import cn.rongcapital.mkt.po.WechatQrcode;
import cn.rongcapital.mkt.po.WechatQrcodeTicket;
import cn.rongcapital.mkt.po.WechatRegister;
import cn.rongcapital.mkt.po.base.BaseTag;
import cn.rongcapital.mkt.po.mongodb.CustomTagLeaf;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.WechatQrcodeIn;
import cn.rongcapital.mkt.service.InsertCustomTagService;
import cn.rongcapital.mkt.service.SaveAudienceListService;
import cn.rongcapital.mkt.vo.in.Audience;
@Service
public class WechatQrcodeBizImpl extends BaseBiz implements WechatQrcodeBiz {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	WechatQrcodeDao wechatQrcodeDao;
	@Autowired
	WechatQrcodeTicketDao wechatQrcodeTicketDao;	
	@Autowired
	WebchatAuthInfoDao webchatAuthInfoDao;
	@Autowired
	WechatChannelDao wechatChannelDao;
	@Autowired
	SaveAudienceListService saveAudienceListService;
	@Autowired
	InsertCustomTagService insertCustomTagService;
	@Autowired
	CustomTagMapDao customTagMapDao;
	@Autowired
	WechatRegisterDao wechatRegisterDao;
	/**
	 * 从微信获取二维码信息
	 * @param sceneId
	 * @param actionName
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	private WechatQrcodeTicket getWechatQrcodeTicketFromWeiXin(App app,int sceneId,String actionName,String wxAcct){
		WechatQrcodeTicket wechatQrcodeTicket =null;				
		Requester req = Requester.builder().setMethod(Method.POST)
				.setUrl(ApiConstant.WEIXIN_QRCODE_CREATE)
				.addUrlParm("access_token", app.tokenManager.getAuthToken(TokenType.AUTHORIZER_ACCESS_TOKEN));					
		Map<String,Object> reqData = convertPathValueToMap(sceneId, "action_info","scene","scene_id");
		reqData.put("action_name", actionName);		
		req.setBody(JsonUtils.toJson(reqData));
		HttpResult result = req.execute();			
		if(result.getCode()==200){
			ObjectNode objNode = JsonUtils.readJsonObject(result.getRespBody());
			/**
	    	 * 记入接口日志到数据库
	    	 */
			WechatInterfaceLog wechatInterfaceLog = new WechatInterfaceLog("WechatQrcodeBizImpl","getWechatQrcodeTicketFromWeiXin",objNode.toString(),new Date());
			wechatInterfaceLogService.insert(wechatInterfaceLog);	
			/**
			 * 组装二维码对象
			 */
			String ticket = objNode.get("ticket").getTextValue();
			String url = objNode.get("url").getTextValue();
			wechatQrcodeTicket = new WechatQrcodeTicket();
			/**
			 * 获取授权的微信公众号账号
			 */
			WechatRegister wechatRegister = getWechatRegisterByAuthAppId(wxAcct);
			wechatQrcodeTicket.setWxAcct(wechatRegister.getWxAcct());
			wechatQrcodeTicket.setSceneId(sceneId);
			wechatQrcodeTicket.setTicket(ticket);
			wechatQrcodeTicket.setUrl(url);
			wechatQrcodeTicket.setState(0);
		}
		return wechatQrcodeTicket;
	}
	
	private WechatRegister getWechatRegisterByAuthAppId(String authAppId){
		WechatRegister wechatRegisterTemp = new WechatRegister();
		wechatRegisterTemp.setAppId(authAppId);
		List<WechatRegister> wechatRegisters = wechatRegisterDao.selectList(wechatRegisterTemp);
		if(CollectionUtils.isNotEmpty(wechatRegisters)){
			WechatRegister wechatRegister = wechatRegisters.get(0);
			return wechatRegister;
		}
		return null;
	}
	
	
	/**
	 * 根据从微信获取的二维码ticket生成二维码图片
	 * @param wechatQrcodeTicket
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void createQrcodeFromWechatQrcodeTicket(WechatQrcodeTicket wechatQrcodeTicket) throws FileNotFoundException, IOException{
		if(wechatQrcodeTicket!=null){
			Requester req = Requester.builder()
					.setUrl(ApiConstant.WEIXIN_QRCODE_SHOW)
					.addUrlParm("ticket", wechatQrcodeTicket.getTicket());				
			HttpResult result = req.execute();
			if(result.getCode()==200){
				String fileType = result.getContentType().toLowerCase().replace("image/", "");
				StringBuilder sb = new StringBuilder();
				String respBodyString = Base64.encodeBase64String(result.getRespBody());
				sb.append("data:image/").append(fileType).append(";base64, ").append(respBodyString);
				byte[] streamByte = Base64.decodeBase64(respBodyString);							
				this.byte2image(streamByte,ApiConstant.UPLOAD_IMG_PATH_LARGE+wechatQrcodeTicket.getSceneId()+".jpg",1200,1200);
				this.byte2image(streamByte,ApiConstant.UPLOAD_IMG_PATH_MIDDLE+wechatQrcodeTicket.getSceneId()+".jpg",800,800);
				this.byte2image(streamByte,ApiConstant.UPLOAD_IMG_PATH_SMALL+wechatQrcodeTicket.getSceneId()+".jpg",200,200);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see cn.rongcapital.mkt.biz.WechatQrcodeBiz#getQrcode(int, java.lang.String)
	 * 获取微信二维码ticket生成二维码图片
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public BaseOutput getQrcode(int sceneId,String actionName,String wxAcct) throws FileNotFoundException, IOException {
		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
		App app = this.getApp();
		/**
		 * 从微信获取二维码信息
		 */
		getWechatRegisterByAuthAppId(app.getAuthAppId());
		WechatQrcodeTicket  wechatQrcodeTicket  = this.getWechatQrcodeTicketFromWeiXin(app,sceneId,actionName,wxAcct);
		
		List<Object> data = new ArrayList<Object>();
		if(wechatQrcodeTicket!=null){
			/**
			 * 生成二维码对象到数据库
			 */
			wechatQrcodeTicketDao.insert(wechatQrcodeTicket);
			long id = wechatQrcodeTicket.getId();
			wechatQrcodeTicket.setSceneId(Integer.parseInt(String.valueOf(id)));
			/**
			 * 创建二维码图片到服务器
			 */
			createQrcodeFromWechatQrcodeTicket(wechatQrcodeTicket);
			
			/**
			 * 构建返回信息
			 */
			Map<String,Object> mapBack = new HashMap<String,Object>();
			mapBack.put("ticket", wechatQrcodeTicket.getTicket());
			mapBack.put("url", wechatQrcodeTicket.getUrl());
			data.add(mapBack);
			baseOutput.setData(data);
			baseOutput.setTotal(1);
		}
		return baseOutput;
	}

	
	/* (non-Javadoc)
	 * @see cn.rongcapital.mkt.biz.WechatQrcodeBiz#getQrcodes(int, int, java.lang.String)
	 * 批量生成二维码
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public BaseOutput getQrcodes(int startSceneId, int endSceneId, String actionName) throws FileNotFoundException, IOException {
		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
			List<Object> data = new ArrayList<Object>();
			int totalSucc=0;
			WebchatAuthInfo webchatAuthInfo = new WebchatAuthInfo();		
			List<WebchatAuthInfo> webchatAuthInfos = webchatAuthInfoDao.selectList(webchatAuthInfo);
			if(webchatAuthInfos!=null&&webchatAuthInfos.size()>0){				
				for(Iterator<WebchatAuthInfo> iter = webchatAuthInfos.iterator();iter.hasNext();){
					App app = this.getApp();
					WebchatAuthInfo webchatAuthInfoTemp = iter.next();
					app.setAuthAppId(webchatAuthInfoTemp.getAuthorizerAppid());
					app.setAuthRefreshToken(webchatAuthInfoTemp.getAuthorizerRefreshToken());
					WechatQrcodeTicket wechatQrcodeTicketTemp =getWechatQrcodeTicketOfLast(webchatAuthInfoTemp.getAuthorizerAppid());
					if(wechatQrcodeTicketTemp!=null){
						int pageSize = endSceneId-startSceneId;
						if(wechatQrcodeTicketTemp.getSceneId()!=null){
							startSceneId = Integer.parseInt(String.valueOf(wechatQrcodeTicketTemp.getSceneId()+1));
						}else{
							startSceneId = 1;
						}
						
						endSceneId = startSceneId+pageSize;
					}
					for(int i=startSceneId;i<=endSceneId;i++){
						try {
							WechatQrcodeTicket  wechatQrcodeTicket = this.getWechatQrcodeTicketFromWeiXin(app, i, actionName,webchatAuthInfoTemp.getAuthorizerAppid());							
							if(wechatQrcodeTicket!=null){
								/**
								 * 生成二维码对象到数据库
								 */
								wechatQrcodeTicketDao.insert(wechatQrcodeTicket);
								
								long id = wechatQrcodeTicket.getId();
								wechatQrcodeTicket.setSceneId(Integer.parseInt(String.valueOf(id)));
								/**
								 * 创建二维码图片到服务器
								 */
								createQrcodeFromWechatQrcodeTicket(wechatQrcodeTicket);
								
								/**
								 * 构建返回信息
								 */
								Map<String,Object> mapBack = new HashMap<String,Object>();
								mapBack.put("ticket", wechatQrcodeTicket.getTicket());
								mapBack.put("url", wechatQrcodeTicket.getUrl());
								data.add(mapBack);
								totalSucc++;
							}
						} catch (Exception e) {
							logger.info(e.getMessage());
							continue;
						}
					}
				}
			}			
			baseOutput.setTotal(totalSucc);
			baseOutput.setData(data);
		return baseOutput;
	}

	@Override
	public BaseOutput getWechatQrcodeTicket() {
		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);		
			WechatQrcodeTicket  wechatQrcodeTicket  = new WechatQrcodeTicket();
			wechatQrcodeTicket.setState(0);
			wechatQrcodeTicket.setOrderField("id");
			wechatQrcodeTicket.setOrderFieldType("ASC");

			List<WechatQrcodeTicket> wechatQrcodeTickets = wechatQrcodeTicketDao.selectList(wechatQrcodeTicket);
			if(wechatQrcodeTickets!=null&&wechatQrcodeTickets.size()>0){
				WechatQrcodeTicket wechatQrcodeTicketBack = wechatQrcodeTickets.get(0);
				
				baseOutput.setTotal(1);
				List<Object> data = new ArrayList<Object>();
				Map<String,Object> mapBack = new HashMap<String,Object>();
				mapBack.put("scene_id", ApiConstant.RETURN_IMG_PATH_SMALL+wechatQrcodeTicketBack.getId()+".jpg");
				mapBack.put("url", wechatQrcodeTicketBack.getUrl());
				data.add(mapBack);
				baseOutput.setData(data);
			}

		return baseOutput;
	}

	private WechatQrcode getWechatQrcodeFromWechatQrcodeIn(WechatQrcodeIn wechatQrcodeIn,WechatQrcodeTicket wechatQrcodeTicket){
		WechatQrcode wechatQrcode = new WechatQrcode();
		
		if(wechatQrcodeIn.getId()!=0){
			wechatQrcode.setId(Integer.valueOf(wechatQrcodeIn.getId()+""));
		}
		
		if(StringUtils.isNotEmpty(wechatQrcodeIn.getWx_acct())){
			wechatQrcode.setWxAcct(wechatQrcodeIn.getWx_acct());
		}
		
		if(StringUtils.isNotEmpty(wechatQrcodeIn.getWx_name())){
			wechatQrcode.setWxName(wechatQrcodeIn.getWx_name());
		}			
		if(wechatQrcodeIn.getCh_code()!=null){
			wechatQrcode.setChCode(wechatQrcodeIn.getCh_code());
		}
		if(StringUtils.isNotEmpty(wechatQrcodeIn.getFixed_audience())){
			wechatQrcode.setAudienceName(wechatQrcodeIn.getFixed_audience());
			wechatQrcode.setIsAudience(NumUtil.int2OneByte(1));
		}else{
			wechatQrcode.setIsAudience(NumUtil.int2OneByte(0));
		}			
		//失效时间补足时分秒00:00:00
		if(wechatQrcodeIn.getExpiration_time() !=null && !"".equals(wechatQrcodeIn.getExpiration_time())){
			wechatQrcode.setExpirationTime(DateUtil.getDateFromString(wechatQrcodeIn.getExpiration_time()+" 00:00:00","yyyy-MM-dd hh:mm:ss"));
		 }
		
		if(StringUtils.isNotEmpty(wechatQrcodeIn.getQrcode_name())){
			wechatQrcode.setQrcodeName(wechatQrcodeIn.getQrcode_name());
		}
		
		if(StringUtils.isNotEmpty(wechatQrcodeIn.getComments())){
			wechatQrcode.setComments(wechatQrcodeIn.getComments());
		}			
		List<String> associationTags = wechatQrcodeIn.getAssociation_tags();
		if(associationTags!=null&&associationTags.size()>0){
			StringBuffer tagIdsb = new StringBuffer();
			for(Iterator<String> iter = associationTags.iterator();iter.hasNext();){
				String associationTag = iter.next();
					
				tagIdsb.append(associationTag).append(";");				
			}
			tagIdsb.substring(0, tagIdsb.length()-1);
			wechatQrcode.setRelatedTags(tagIdsb.toString());
		}			
		
		wechatQrcode.setStatus(NumUtil.int2OneByte(1));
		wechatQrcode.setCreateTime(new Date());
		if(wechatQrcodeTicket!=null){
			wechatQrcode.setQrcodePic(String.valueOf(wechatQrcodeTicket.getId())+".jpg");
			wechatQrcode.setQrcodeUrl(wechatQrcodeTicket.getUrl());				
			wechatQrcode.setTicket(String.valueOf(wechatQrcodeTicket.getId()));
		}
		return wechatQrcode;		
	}
	
	/**
	 * 获取未被使用的ticket
	 * @return
	 */
	private WechatQrcodeTicket getWechatQrcodeTicketByState(String wxAcct){
		WechatQrcodeTicket wechatQrcodeTicketBack = null;
		WechatQrcodeTicket wechatQrcodeTicket = new WechatQrcodeTicket();
		wechatQrcodeTicket.setState(0);
		wechatQrcodeTicket.setOrderField("id");
		wechatQrcodeTicket.setOrderFieldType("ASC");
		wechatQrcodeTicket.setWxAcct(wxAcct);
		List<WechatQrcodeTicket> wechatQrcodeTickets = wechatQrcodeTicketDao.selectList(wechatQrcodeTicket);
		if(wechatQrcodeTickets!=null&&wechatQrcodeTickets.size()>0){
			wechatQrcodeTicketBack = wechatQrcodeTickets.get(0);				
		}
		return wechatQrcodeTicketBack;			

	}
	
	private WechatQrcodeTicket getWechatQrcodeTicketOfLast(String authorizerAppid){
		WechatQrcodeTicket wechatQrcodeTicketBack = new WechatQrcodeTicket();
		WechatRegister wechatRegister = getWechatRegisterByAuthAppId(authorizerAppid);
		WechatQrcodeTicket wechatQrcodeTicket = new WechatQrcodeTicket();
		wechatQrcodeTicket.setWxAcct(wechatRegister.getWxAcct());
		wechatQrcodeTicket.setOrderField("id");
		wechatQrcodeTicket.setOrderFieldType("DESC");
		wechatQrcodeTicket.setStartIndex(0);
		wechatQrcodeTicket.setPageSize(1);
		List<WechatQrcodeTicket> wechatQrcodeTicketes = wechatQrcodeTicketDao.selectList(wechatQrcodeTicket);
		if(CollectionUtils.isNotEmpty(wechatQrcodeTicketes)){
			wechatQrcodeTicketBack = wechatQrcodeTicketes.get(0);
		}
		return wechatQrcodeTicketBack;
	}
	
	/**
	 * @param wechatQrcode
	 * @param wechatQrcodeIn
	 * @param wechatQrcodeTicket
	 * 抽取李金凯代码，待其梳理
	 * @return
	 */
	private WechatQrcode getWechatQrcode(WechatQrcode wechatQrcode, WechatQrcodeIn wechatQrcodeIn){
		if(wechatQrcodeIn.getId()!=0){
			wechatQrcode.setId(Integer.valueOf(wechatQrcodeIn.getId()+""));
		}else{
			//同一公众号下,微信二维码名称重复校验
			wechatQrcode.setWxAcct(wechatQrcodeIn.getWx_acct());
			wechatQrcode.setQrcodeName(wechatQrcodeIn.getQrcode_name());
			wechatQrcode.setChCode(wechatQrcodeIn.getCh_code());
			wechatQrcode.setStatus(NumUtil.int2OneByte(1));
			List<WechatQrcode> selectList = wechatQrcodeDao.selectList(wechatQrcode);
			
			if(selectList != null && selectList.size() > 0){				
				return null ;
			}
		}
		
		if(StringUtils.isNotEmpty(wechatQrcodeIn.getWx_acct())){
			wechatQrcode.setWxAcct(wechatQrcodeIn.getWx_acct());
		}
		
		if(StringUtils.isNotEmpty(wechatQrcodeIn.getWx_name())){
			wechatQrcode.setWxName(wechatQrcodeIn.getWx_name());
		}		
		//渠道Code
		Integer channelCode = wechatQrcodeIn.getCh_code(); 
		if(channelCode != null){
			
			WechatChannel wechatChannel = new WechatChannel();
			wechatChannel.setId(channelCode);
			List<WechatChannel> channelList = wechatChannelDao.selectList(wechatChannel);
			//设置自定义渠道为不了编辑
			if(channelList != null && channelList.size() > 0){
				
				wechatChannel = channelList.get(0);
				/*if(wechatChannel != null && wechatChannel.getIsRemoved() == ApiConstant.TABLE_DATA_REMOVED_DEL){
					wechatChannel.setIsRemoved(ApiConstant.TABLE_DATA_REMOVED_NOTDEL);
					wechatChannelDao.updateById(wechatChannel);
				}*/
			}
			
			wechatQrcode.setChCode(channelCode);
		}
		if(StringUtils.isNotEmpty(wechatQrcodeIn.getFixed_audience())){
			wechatQrcode.setAudienceName(wechatQrcodeIn.getFixed_audience());
			wechatQrcode.setIsAudience(NumUtil.int2OneByte(1));
			
			//新建人群管理保存
			Audience audience = new Audience();
			audience.setAudience_name(wechatQrcodeIn.getFixed_audience());
			saveAudienceListService.saveAudienceList(audience);
		}else{
			wechatQrcode.setIsAudience(NumUtil.int2OneByte(0));
		}			
		//失效时间补足时分秒00:00:00
		if(wechatQrcodeIn.getExpiration_time() !=null && !"".equals(wechatQrcodeIn.getExpiration_time())){
			wechatQrcode.setExpirationTime(DateUtil.getDateFromString(wechatQrcodeIn.getExpiration_time()+" 00:00:00","yyyy-MM-dd hh:mm:ss"));
		 }
		
		if(StringUtils.isNotEmpty(wechatQrcodeIn.getQrcode_name())){
			wechatQrcode.setQrcodeName(wechatQrcodeIn.getQrcode_name());
		}
		
		if(StringUtils.isNotEmpty(wechatQrcodeIn.getComments())){
			wechatQrcode.setComments(wechatQrcodeIn.getComments());
		}

		List<String> associationTags = wechatQrcodeIn.getAssociation_tags();
		if(associationTags!=null&&associationTags.size()>0){
			StringBuffer tagIdsb = new StringBuffer();
			BaseTag tag = null;
			for(Iterator<String> iter = associationTags.iterator();iter.hasNext();){
				String associationTag = iter.next();
				if(associationTag!=null){
					tag = insertCustomTagService.insertCustomTagLeafFromSystemIn(associationTag,TagSourceEnum.WECHAT_QRCODE_SOURCE_ACCESS.getTagSourceName());
					String tagId = tag == null ? "":tag.getTagId();					
					tagIdsb.append(tagId).append(";");
				}					
			}
			String tagIds = tagIdsb.substring(0, tagIdsb.length()-1);
			wechatQrcode.setRelatedTags(tagIds);

			//添加二维码与自定义标签关联表的信息
		}
		wechatQrcode.setCreateTime(new Date());
		return wechatQrcode;		
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public BaseOutput createQrcode(WechatQrcodeIn wechatQrcodeIn) {
		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
			
			WechatQrcode wechatQrcode = new WechatQrcode();
			
			wechatQrcode = this.getWechatQrcode(wechatQrcode, wechatQrcodeIn);
			if(wechatQrcode==null){
				baseOutput.setCode(ApiErrorCode.VALIDATE_ERROR.getCode());
				baseOutput.setMsg(ApiErrorCode.VALIDATE_ERROR.getMsg());
				return baseOutput;
			}
			
			WechatQrcodeTicket wechatQrcodeTicket = this.getWechatQrcodeTicketByState(wechatQrcodeIn.getWx_acct());	
			if(wechatQrcodeTicket != null){
				
				wechatQrcodeTicket.setState(1);
				wechatQrcodeTicketDao.updateById(wechatQrcodeTicket); 
				
				wechatQrcode.setQrcodePic(String.valueOf(wechatQrcodeTicket.getId())+".jpg");
				wechatQrcode.setQrcodeUrl(wechatQrcodeTicket.getUrl());				
				wechatQrcode.setTicket(String.valueOf(wechatQrcodeTicket.getId()));		
			}
			
			//有Id更新,无id新增
			if(wechatQrcode.getId() != null && wechatQrcode.getId() != 0){
				wechatQrcodeDao.updateById(wechatQrcode);
			}else{
				wechatQrcode.setStatus(NumUtil.int2OneByte(0));
				wechatQrcode.setAuthorization(NumUtil.int2OneByte(0));
				wechatQrcodeDao.insert(wechatQrcode);
			}
			
			baseOutput.setTotal(1);
			List<Object> data = new ArrayList<Object>();
			Map<String,Object> mapBack = new HashMap<String,Object>();
			mapBack.put("id", wechatQrcode.getId());
			mapBack.put("qrcodePic", ApiConstant.RETURN_IMG_PATH_SMALL+wechatQrcode.getQrcodePic());
			Date createTime = wechatQrcode.getCreateTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			mapBack.put("createtime", sdf.format(createTime));
			
			data.add(mapBack);
			baseOutput.setData(data);
		return baseOutput;
	}

	
	/**
	 * 启用
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public BaseOutput enableQrcode(WechatQrcodeIn wechatQrcodeIn) {
		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
			WechatQrcode wechatQrcode = new WechatQrcode();			
			if(wechatQrcodeIn.getId()!=0){
				wechatQrcode.setId(Integer.valueOf(wechatQrcodeIn.getId()+""));
			}else{
				baseOutput.setCode(ApiErrorCode.VALIDATE_ERROR.getCode());
				baseOutput.setMsg(ApiErrorCode.VALIDATE_ERROR.getMsg());
				return baseOutput;
			}

            //修改是否可删除逻辑2016-9-30 by lihaiguang
            WechatQrcode wechatQ =  new WechatQrcode();
            wechatQ.setId(Integer.valueOf(String.valueOf(wechatQrcodeIn.getId())));
            List<WechatQrcode> wechatQList = wechatQrcodeDao.selectList(wechatQ);
            if(wechatQList != null && wechatQList.size() > 0){
                Integer chCode = wechatQList.get(0).getChCode();
                WechatChannel channel = new WechatChannel();
                channel.setId(chCode);
                List<WechatChannel> selectChannelList = wechatChannelDao.selectList(channel);
                if(selectChannelList != null && selectChannelList.size() > 0) {
                    channel = selectChannelList.get(0);
                    channel.setIsRemoved(ApiConstant.TABLE_DATA_REMOVED_NOTDEL);
                    wechatChannelDao.updateById(channel);
                }
            }
			
			wechatQrcode.setStatus(NumUtil.int2OneByte(1));
			wechatQrcodeDao.updateById(wechatQrcode);

			baseOutput.setTotal(1);
			List<Object> data = new ArrayList<Object>();
			Map<String,Object> mapBack = new HashMap<String,Object>();
			mapBack.put("id", wechatQrcode.getId());
			
			data.add(mapBack);
			baseOutput.setData(data);
		return baseOutput;
	}
		
	public static Map<String,Object> convertPathValueToMap(Object pathValue , String ... path){
		Map<String,Object> root = new HashMap<String,Object>();
		Map<String,Object> current = root;
		for(int i=0;i<path.length;i++){
			String key = path[i];
			if(i==path.length-1){
				current.put(key, pathValue);
			}else{
				Map<String,Object> sub = new HashMap<String,Object>();
				current.put(key,sub);
				current = sub;
			}
		}
		return root;
	}	
	
	//byte数组到图片
	public void byte2image(byte[] data,String path,int width,int height) throws FileNotFoundException, IOException{
	    if(data.length<3||path.equals("")){
	    	 return;
	    }	   
	    FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
	    imageOutput.write(data, 0, data.length);
	    imageOutput.close();
	    ImageCompressUtil.zipImageFile(path, width, height, 1f, "");
	  }

}
