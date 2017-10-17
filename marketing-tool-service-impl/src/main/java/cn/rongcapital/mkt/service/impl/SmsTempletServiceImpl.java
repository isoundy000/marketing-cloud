package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.SmsTempletTypeEnum;
import cn.rongcapital.mkt.common.enums.SmsTempleteAuditStatusEnum;
import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.common.util.NumUtil;
import cn.rongcapital.mkt.dao.SmsMaterialDao;
import cn.rongcapital.mkt.dao.SmsTempletDao;
import cn.rongcapital.mkt.dao.SmsTempletMaterialMapDao;
import cn.rongcapital.mkt.po.SmsMaterial;
import cn.rongcapital.mkt.po.SmsTaskHead;
import cn.rongcapital.mkt.po.SmsTemplet;
import cn.rongcapital.mkt.po.SmsTempletMaterialMap;
import cn.rongcapital.mkt.service.SmsTempletService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.ColumnsOut;
import cn.rongcapital.mkt.vo.out.SmsTempletOut;
import cn.rongcapital.mkt.vo.sms.in.SmsTempletIn;
import cn.rongcapital.mkt.vo.sms.in.SmsTempletListIn;
import cn.rongcapital.mkt.vo.sms.in.SmstempletMaterialVo;
import cn.rongcapital.mkt.vo.sms.out.SmsTempletCountVo;

@Service
public class SmsTempletServiceImpl implements SmsTempletService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private final String FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";
	
	@Autowired
	private SmsTempletDao smsTempletDao;
	
	@Autowired
	private SmsMaterialDao smsMaterialDao;
	
    @Autowired
    private SmsTempletMaterialMapDao smsTempletMaterialMapDao;
	
	private final String TEMPLETE_ID= "模板ID";
	
	private final String TEMPLETE_NAME= "模板名称";
	
	private final String TEMPLETE_CREATE_TIME ="提交时间";
	
	private final String TEMPLETE_AUDIT_STATUS ="审核状态";
	
	private final String TEMPLETE_TYPE="模板类型";
	
	private final String AUDIT_REASON="无";
		
	@Override
	public SmsTempletOut smsTempletList(String userId, Integer index, Integer size, String channelType,
			String type, String name,String content) {
		logger.info("this is SmsTempletServiceImpl.smsTempletList");
		
		SmsTempletOut smsTempletOut = this.newSuccessSmsTempletOut();

		SmsTemplet smsTempletTemp = new SmsTemplet();
		if(StringUtils.isNotEmpty(channelType)){
			Integer channelTypeInt = Integer.parseInt(channelType);			
			smsTempletTemp.setChannelType(channelTypeInt.byteValue());
		}
		
		if(!"-1".equals(type)){
			smsTempletTemp.setType(NumUtil.int2OneByte(Integer.parseInt(type)));
		}		
		if(StringUtils.isNotEmpty(name)){
			smsTempletTemp.setName(name);
		}
		if(StringUtils.isNotEmpty(content)){
			smsTempletTemp.setContent(content);
		}
		smsTempletTemp.setStatus(NumUtil.int2OneByte(StatusEnum.ACTIVE.getStatusCode()));
		smsTempletTemp.setOrderField("create_time");
		smsTempletTemp.setOrderFieldType("DESC");
		smsTempletTemp.setStartIndex((index-1)*size);
		smsTempletTemp.setPageSize(size);
		
		/**
		 * 统计总数
		 */
		int totalCount = smsTempletDao.selectListCount(smsTempletTemp);
		/**
		 * 统计模板类型总数
		 */
		List<SmsTempletCountVo> smsTempletCountVosByType= smsTempletDao.selectListCountGroupByType(smsTempletTemp);
		/**
		 * 统计审核总数
		 */
		List<SmsTempletCountVo> smsTempletCountVosByAuditStatus= smsTempletDao.selectListCountGroupByAuditStatus(smsTempletTemp);
		/**
		 * 注入输出总数
		 */
		smsTempletOut = this.setSmsTempletOutView(smsTempletOut, smsTempletCountVosByType, smsTempletCountVosByAuditStatus);
		/**
		 * 查询模板列表
		 */
		List<SmsTemplet> dataList = smsTempletDao.selectList(smsTempletTemp);
		/**
		 * 注入返回的属性
		 */
		dataList = this.setSmsTempletView(dataList);
		smsTempletOut.setTotalCount(totalCount);		
		this.setBaseOut(smsTempletOut, dataList);
		List<Object> colNames = this.getColumnsOutList();
		smsTempletOut.setColNames(colNames);		
		smsTempletOut.setTotalCount(totalCount);
		
		return smsTempletOut;
	}
	
	/**
	 * 注入返回模板总数和审核总数
	 * @param smsTempletOut
	 * @param smsTempletCountVosByType
	 * @param smsTempletCountVosByAuditStatus
	 * @return
	 */
	private SmsTempletOut setSmsTempletOutView(SmsTempletOut smsTempletOut,List<SmsTempletCountVo> smsTempletCountVosByType,List<SmsTempletCountVo> smsTempletCountVosByAuditStatus){
		if(CollectionUtils.isNotEmpty(smsTempletCountVosByType)){
			for(SmsTempletCountVo smsTempletCountVo:smsTempletCountVosByType){
				Integer  type = smsTempletCountVo.getType();
				if(SmsTempletTypeEnum.FIXED.getStatusCode().equals(type)){
					smsTempletOut.setTypeFixedCount(smsTempletCountVo.getDataCount());
				}
				if(SmsTempletTypeEnum.VARIABLE.getStatusCode().equals(type)){
					smsTempletOut.setTypeVariableCount(smsTempletCountVo.getDataCount());
				}				
			}
		}
		if(CollectionUtils.isNotEmpty(smsTempletCountVosByAuditStatus)){
			for(SmsTempletCountVo smsTempletCountVo:smsTempletCountVosByAuditStatus){
				Integer auditStatus = smsTempletCountVo.getAuditStatus();
				if(SmsTempleteAuditStatusEnum.AUDIT_STATUS_NO_CHECK.getStatusCode().equals(auditStatus)){
					smsTempletOut.setAuditStatusNoCheckCount(smsTempletCountVo.getDataCount());
				}
				if(SmsTempleteAuditStatusEnum.AUDIT_STATUS_NO_PASS.getStatusCode().equals(auditStatus)){
					smsTempletOut.setAuditStatusNoPassCount(smsTempletCountVo.getDataCount());
				}
				if(SmsTempleteAuditStatusEnum.AUDIT_STATUS_PASS.getStatusCode().equals(auditStatus)){
					smsTempletOut.setAuditStatusPassCount(smsTempletCountVo.getDataCount());
				}
			}			
		}
		return smsTempletOut;		
	}
	
	/**
	 * 返回结果的组装
	 * @param dataList
	 * @return
	 */
	public List<SmsTemplet> setSmsTempletView(List<SmsTemplet> dataList){
		if(CollectionUtils.isNotEmpty(dataList)){
			for(Iterator<SmsTemplet> iter = dataList.iterator();iter.hasNext();){
				SmsTemplet smsTemplet = iter.next();
				if(smsTemplet!=null){
					smsTemplet.setCreateTimeStr(DateUtil.getStringFromDate(smsTemplet.getCreateTime(),FORMAT_STRING));
					if(smsTemplet.getAuditStatus()!=null){
						int auditStatus = smsTemplet.getAuditStatus() & 0xff;
						smsTemplet.setAuditStatusStr(SmsTempleteAuditStatusEnum.getDescriptionByStatus(auditStatus));
					}
					if(smsTemplet.getType()!=null){
						int typeTemp = smsTemplet.getType() & 0xff;
						smsTemplet.setTypeStr(SmsTempletTypeEnum.getDescriptionByStatus(typeTemp));						
					}
					smsTemplet.setEditCheck(true);
					if(smsTempletValidate(smsTemplet.getId())){						
						smsTemplet.setDeleteCheck(true);
					}else{						
						smsTemplet.setDeleteCheck(false);
					}					
				}				
			}
		}
		return dataList;		
	}
	
	
	/**
	 * 验证模板是否可以编辑或者删除
	 * @param id
	 * @return
	 */
	public boolean smsTempletValidate(Integer id){
		SmsMaterial smsMaterialTemp = new SmsMaterial();
		smsMaterialTemp.setSmsTempletId(id);
		smsMaterialTemp.setStatus(NumUtil.int2OneByte(StatusEnum.ACTIVE.getStatusCode()));
		List<SmsMaterial> smsMaterials = smsMaterialDao.selectList(smsMaterialTemp);		
		if(CollectionUtils.isNotEmpty(smsMaterials)){
			return false;
		}else{
			return true;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public BaseOutput saveOrUpdateSmsTemplet(SmsTempletIn smsTempletIn) {
		BaseOutput output = this.newSuccessBaseOutput();
		SmsTemplet smsTemplet = this.getSmsTempletBySmsTempletIn(smsTempletIn);
		if(smsTemplet!=null){
			if(smsTemplet.getId()!=null&&smsTemplet.getId()!=0){
				smsTemplet = setSmsTempletAuditProperties(smsTemplet);
				smsTempletDao.updateById(smsTemplet);
			}else{
				smsTemplet = setSmsTempletAuditProperties(smsTemplet);
				smsTempletDao.insert(smsTemplet);
			}
			
			//变量模板添加
			if(smsTempletIn.getType().intValue() == SmsTempletTypeEnum.VARIABLE.getStatusCode()){
				
				updateMaterial(smsTempletIn,smsTemplet.getId());
			}
			
			List<Object> smsTemplets = new ArrayList<Object>();
			smsTemplets.add(smsTemplet);
			output.setData(smsTemplets);
			output.setDate(DateUtil.getStringFromDate(new Date(),FORMAT_STRING));
		}else{
			output.setCode(ApiErrorCode.PARAMETER_ERROR.getCode());
			output.setMsg(ApiErrorCode.PARAMETER_ERROR.getMsg());
		}
		return output;		
	}

	private void updateMaterial(SmsTempletIn smsTempletIn, Integer templetId) {

		logger.info("create templet to material relations,templetId:"+ templetId);
		//1.删除原来的关联关系
		smsTempletMaterialMapDao.deleteByTempletId(templetId);
		
		//2.新增关联关系
		SmsTempletMaterialMap smsTempletMaterialMap ;
		
		for(SmstempletMaterialVo smstempletMaterial : smsTempletIn.getMaterialList()){
			
			smsTempletMaterialMap = new SmsTempletMaterialMap();
			
			BeanUtils.copyProperties(smstempletMaterial, smsTempletMaterialMap);
			
			smsTempletMaterialMap.setSmsTempletId(templetId.longValue());
			
			smsTempletMaterialMapDao.insert(smsTempletMaterialMap);
		}
		
		
	}

	/**
	 * 设置审核属性，以后加入审核流程，此方法废弃
	 * @param smsTemplet
	 * @return
	 */
	private SmsTemplet setSmsTempletAuditProperties(SmsTemplet smsTemplet){
		if(smsTemplet!=null){
			smsTemplet.setAuditReason(AUDIT_REASON);
			smsTemplet.setAuditTime(new Date());
			smsTemplet.setAuditor(smsTemplet.getCreator());
		}
		return smsTemplet;		
	}
	
	/**
	 * 输入转换成模板对象
	 * @param smsTempletIn
	 * @return
	 */
	private SmsTemplet getSmsTempletBySmsTempletIn(SmsTempletIn smsTempletIn){
		SmsTemplet smsTemplet = new SmsTemplet();
		if(smsTempletIn!=null){			
			if(smsTempletIn.getId()!=null&&smsTempletIn.getId()!=0){
				smsTemplet.setId(smsTempletIn.getId());
			}			
			if(StringUtils.isNotEmpty(smsTempletIn.getAuditor())){
				smsTemplet.setAuditor(smsTempletIn.getAuditor());
			}	
			
			smsTemplet.setAuditStatus(NumUtil.int2OneByte(SmsTempleteAuditStatusEnum.AUDIT_STATUS_PASS.getStatusCode()));
			if(smsTempletIn.getChannelType()!=null){
				smsTemplet.setChannelType(NumUtil.int2OneByte(smsTempletIn.getChannelType()));
			}else{
				return null;
			}
			if(smsTempletIn.getType()!=null){
				smsTemplet.setType(NumUtil.int2OneByte(smsTempletIn.getType()));
			}else{
				return null;
			}
			if(StringUtils.isNotEmpty(smsTempletIn.getContent())){
				smsTemplet.setContent(smsTempletIn.getContent());
			}else{
				return null;
			}
			if(StringUtils.isNotEmpty(smsTempletIn.getCreator())){
				smsTemplet.setCreator(smsTempletIn.getCreator());				
			}
			smsTemplet.setCreateTime(new Date());
			
			smsTemplet.setStatus(NumUtil.int2OneByte(StatusEnum.ACTIVE.getStatusCode()));
			if(StringUtils.isNotEmpty(smsTempletIn.getUpdateUser())){
				smsTemplet.setUpdateTime(new Date());
				smsTemplet.setUpdateUser(smsTempletIn.getUpdateUser());
			}
			
			if(StringUtils.isNotEmpty(smsTempletIn.getName())){
				smsTemplet.setName(smsTempletIn.getName());
			}
			
		}
		return smsTemplet;		
	}

	private SmsTempletOut newSuccessSmsTempletOut() {
		SmsTempletOut smsTempletOut = new SmsTempletOut();
		smsTempletOut.setCode(ApiErrorCode.SUCCESS.getCode());
		smsTempletOut.setMsg(ApiErrorCode.SUCCESS.getMsg());
		smsTempletOut.setTotal(ApiConstant.INT_ZERO);
		return smsTempletOut;
	}

	private BaseOutput newSuccessBaseOutput() {
		return new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,
				null);
	}
	
	private <O> void setBaseOut(BaseOutput out, List<O> dataList) {
		if (CollectionUtils.isEmpty(dataList)) {
			return;
		}
		out.setTotal(dataList.size());
		out.getData().addAll(dataList);
	}
	
	/**
	 * 返回列名
	 * @return
	 */
	private List<Object> getColumnsOutList(){
		List<Object> columnsOutList = new ArrayList<Object>(); 
		//ID
		ColumnsOut columnsOut0 = new ColumnsOut();
		columnsOut0.setColCode("id");
		columnsOut0.setColName(this.TEMPLETE_ID);
		columnsOutList.add(columnsOut0);
		//模板内容
		ColumnsOut columnsOut1 = new ColumnsOut();
		columnsOut1.setColCode("name");
		columnsOut1.setColName(this.TEMPLETE_NAME);
		columnsOutList.add(columnsOut1);		
		//createTime
		ColumnsOut columnsOut2 = new ColumnsOut();
		columnsOut2.setColCode("createTimeStr");
		columnsOut2.setColName(this.TEMPLETE_CREATE_TIME);
		columnsOutList.add(columnsOut2);
		//审核状态
		ColumnsOut columnsOut3 = new ColumnsOut();
		columnsOut3.setColCode("auditStatus");
		columnsOut3.setColName(this.TEMPLETE_AUDIT_STATUS);
		columnsOutList.add(columnsOut3);
		//模板类型
		ColumnsOut columnsOut4 = new ColumnsOut();
		columnsOut4.setColCode("typeStr");
		columnsOut4.setColName(this.TEMPLETE_TYPE);
		columnsOutList.add(columnsOut4);
		return columnsOutList;		
	}
	
}
