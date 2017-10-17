package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.enums.*;
import cn.rongcapital.mkt.common.jedis.JedisClient;
import cn.rongcapital.mkt.common.jedis.JedisException;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.*;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.factory.CalcSmsTargetAudienceStrateyFactory;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;
import cn.rongcapital.mkt.material.coupon.po.MaterialCouponCode;
import cn.rongcapital.mkt.material.coupon.service.CouponCodeListService;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponEditDetailService;
import cn.rongcapital.mkt.material.coupon.vo.out.CouPonEditInfoOut;
import cn.rongcapital.mkt.po.*;
import cn.rongcapital.mkt.service.MQTopicService;

import cn.rongcapital.mkt.vo.BaseOutput;
import com.alibaba.druid.support.json.JSONUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

import static cn.rongcapital.mkt.common.enums.SmsTempletTypeEnum.FIXED;
import static cn.rongcapital.mkt.common.enums.SmsTempletTypeEnum.VARIABLE;

/**
 * Created by byf on 10/18/16.
 */
@Service
public class GenerateSmsDetailTask implements TaskService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SmsTaskDetailDao smsTaskDetailDao;

    @Autowired
    private SmsTaskDetailStateDao smsTaskDetailStateDao;

    @Autowired
    private SmsTaskHeadDao smsTaskHeadDao;

    @Autowired
    private SmsTaskBodyDao smsTaskBodyDao;

    @Autowired
    private SmsTaskTargetAudienceCacheDao smsTaskTargetAudienceCacheDao;

    @Autowired
    private AudienceListDao audienceListDao;

    @Autowired
    private AudienceListPartyMapDao audienceListPartyMapDao;

    @Autowired
    private DataPartyDao dataPartyDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MQTopicService mqTopicService;
    
    @Autowired
    private SmsSignatureDao smsSignatureDao;

    @Autowired
    private SmsMaterialDao smsMaterialDao;

    @Autowired
    private SmsMaterialMaterielMapDao smsMaterialMaterielMapDao;

    @Autowired
    private SmsMaterialVariableMapDao smsMaterialVariableMapDao;

    @Autowired
    private MaterialCouponEditDetailService materialCouponEditDetailService;

    @Autowired
    private CouponCodeListService couponCodeListService;

    @Autowired
    private CalcSmsTargetAudienceStrateyFactory calcSmsTargetAudienceStrateyFactory;

    @Autowired
    private SegmentCalcSmsTargetAudienceStrategy segmentCalcSmsTargetAudienceStrategy;

    @Autowired
    private AudienceCalcSmsTargetAudienceStrategy audienceCalcSmsTargetAudienceStrategy;

    @Autowired
    private MaterialCouponDao materialCouponDao;

    private Map<Integer,AbstractCalcSmsTargetAudienceStrategy> strategyMap = new HashMap<>();


    private final String SEGMENTATION_HEAD_ID = "segmentation_head_id";
    private final int PAGE_SIZE = 10000;
    private static final String FRONT_SMS_VARIABLE_MASK="{";
    private static final String BACK_SMS_VARIABLE_MASK="}";

    public static final Integer POOL_INDEX = 2;
    
    @Override
    public void task(Integer taskId) {

    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void task(String taskHeadIdStr) {
        strategyMap.put(SmsTargetAudienceTypeEnum.SMS_TARGET_SEGMENTATION.getTypeCode(),segmentCalcSmsTargetAudienceStrategy);
        strategyMap.put(SmsTargetAudienceTypeEnum.SMS_TARGET_AUDIENCE.getTypeCode(), audienceCalcSmsTargetAudienceStrategy);

        logger.info("taskHeadId :" + taskHeadIdStr);
        Long taskHeadId = Long.valueOf(taskHeadIdStr);

        //1根据headId选出模板内容
        SmsTaskHead paramSmsTaskHead = new SmsTaskHead();
        paramSmsTaskHead.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        paramSmsTaskHead.setId(taskHeadId);
        List<SmsTaskHead> smsTaskHeads = smsTaskHeadDao.selectList(paramSmsTaskHead);
        if(CollectionUtils.isEmpty(smsTaskHeads)) return;

        SmsTaskHead targetHead = smsTaskHeads.get(0);

        //2根据headId依次选出受众人群
        Set<String> targetDistinctReceiveMobiles = new HashSet<>();
        SmsTaskBody paramSmsTaskBody = new SmsTaskBody();
        paramSmsTaskBody.setSmsTaskHeadId(taskHeadId);
        paramSmsTaskBody.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        List<SmsTaskBody> smsTaskBodies = smsTaskBodyDao.selectList(paramSmsTaskBody);
        if(CollectionUtils.isEmpty(smsTaskBodies)) return;
        for(SmsTaskBody smsTaskBody : smsTaskBodies){
            AbstractCalcSmsTargetAudienceStrategy strategy = strategyMap.get(smsTaskBody.getTargetType());
            List<String> receiveMobileList = strategy.queryReceiveMobileList(smsTaskBody.getSmsTaskHeadId(),smsTaskBody.getTargetId());
            if(CollectionUtils.isEmpty(receiveMobileList)) continue;
            logger.info("sms list distinct mobile size: " + receiveMobileList.size());
            targetDistinctReceiveMobiles.addAll(receiveMobileList);
        }

        logger.info("sms task audience size:" + targetDistinctReceiveMobiles.size());
        //3将受众人群+模板内容+受众类型存入Task_Detail表中
        if(targetDistinctReceiveMobiles.contains(null)){
            targetDistinctReceiveMobiles.remove(null);
        }
        if(targetDistinctReceiveMobiles.contains("")){
            targetDistinctReceiveMobiles.remove("");
        }
        //3.5如果targetDistinctReceiveMobiles的容量为空,则不执行下面得短信生成语句
        if(CollectionUtils.isEmpty(targetDistinctReceiveMobiles)) return;
        insertDataToSmsDetailAndDetailState(taskHeadId, targetHead, targetDistinctReceiveMobiles);

        //4更新SmsTaskHead表的人群相关的字段
        targetHead.setAudienceGenerateStatus(ApiConstant.INT_ZERO);
        targetHead.setTotalCoverNum(targetDistinctReceiveMobiles.size());
        targetHead.setWaitingNum(targetDistinctReceiveMobiles.size());
        smsTaskHeadDao.updateById(targetHead);
        logger.info("sms task head update info");

        smsTaskHeads = smsTaskHeadDao.selectList(paramSmsTaskHead);
        if(CollectionUtils.isEmpty(smsTaskHeads)) return;
        SmsTaskHead currentTaskHead = smsTaskHeads.get(0);
        //4检测TaskHead的发送状态
        if(currentTaskHead.getSmsTaskStatus() == SmsTaskStatusEnum.TASK_EXECUTING.getStatusCode()){
            mqTopicService.sendSmsByTaskId(taskHeadIdStr);
        }
        
    }

    private void insertDataToSmsDetailAndDetailState(Long taskHeadId, SmsTaskHead targetHead, Set<String> targetDistinctReceiveMobiles) {
        List<SmsTaskDetail> smsTaskDetailList = new LinkedList<>();
        List<SmsTaskDetailState> smsTaskDetailStateList = new LinkedList<>();
        if(targetHead!=null){
        	long signatureId = targetHead.getSmsTaskSignatureId();
        	SmsSignature smsSignature = new SmsSignature();
        	smsSignature.setId(signatureId);
        	List<SmsSignature> smsSignatures =smsSignatureDao.selectList(smsSignature);
        	if(!CollectionUtils.isEmpty(smsSignatures)){
        		smsSignature = smsSignatures.get(0);
        	}

        	//Todo:添加方法,在这里根据短信类型生成不同类型的短信.
            generateSmsTaskDetailAccordSmsType(taskHeadId, targetHead, targetDistinctReceiveMobiles, smsTaskDetailList, smsSignature);
        }

        if(CollectionUtils.isEmpty(smsTaskDetailList)) return;
        int totalNum = (smsTaskDetailList.size() + PAGE_SIZE) / PAGE_SIZE;
        for(int index = 0; index < totalNum; index++){
            if(index == totalNum-1){
                logger.info("insert SmsTaskDetail index : " + index);
                smsTaskDetailDao.batchInsert(smsTaskDetailList.subList(index*PAGE_SIZE,smsTaskDetailList.size()));
            }else{
                logger.info("insert SmsTaskDetail index : " + index);
                smsTaskDetailDao.batchInsert(smsTaskDetailList.subList(index*PAGE_SIZE,(index + 1)*PAGE_SIZE));
            }
        }

        for(SmsTaskDetail taskDetail : smsTaskDetailList){
            SmsTaskDetailState smsTaskDetailState = new SmsTaskDetailState();
            smsTaskDetailState.setSmsTaskDetailId(taskDetail.getId());
            smsTaskDetailState.setSmsTaskSendStatus(SmsDetailSendStateEnum.SMS_DETAIL_WAITING.getStateCode());
            smsTaskDetailStateList.add(smsTaskDetailState);
        }

        for(int index = 0; index < totalNum; index++){
            if(index == totalNum-1){
                logger.info("insert SmsTaskDetailState index : " + index);
                smsTaskDetailStateDao.batchInsert(smsTaskDetailStateList.subList(index*PAGE_SIZE,smsTaskDetailStateList.size()));
            }else{
                logger.info("insert SmsTaskDetailState index : " + index);
                smsTaskDetailStateDao.batchInsert(smsTaskDetailStateList.subList(index*PAGE_SIZE,(index + 1)*PAGE_SIZE));
            }
        }
    }

    //Todo:如果是固定短信,按照现有的生成方法保持不变;如果是变量短信按照规则一步一步生成.
    private void generateSmsTaskDetailAccordSmsType(Long taskHeadId, SmsTaskHead targetHead, Set<String> targetDistinctReceiveMobiles, List<SmsTaskDetail> smsTaskDetailList, SmsSignature smsSignature) {
        SmsMaterial paramSmsMaterial = new SmsMaterial();
        paramSmsMaterial.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        paramSmsMaterial.setId(targetHead.getSmsTaskMaterialId().intValue());
        List<SmsMaterial> targetSmsMaterialList = smsMaterialDao.selectList(paramSmsMaterial);
        if(CollectionUtils.isEmpty(targetSmsMaterialList)) return;
        Integer smsType = targetSmsMaterialList.get(0).getSmsType().intValue();

        if(smsType.equals(FIXED.getStatusCode())){
            for(String distinctReceiveMobile : targetDistinctReceiveMobiles){
                SmsTaskDetail smsTaskDetail = new SmsTaskDetail();
                smsTaskDetail.setReceiveMobile(distinctReceiveMobile);
                if(smsSignature!=null&& StringUtils.isNotEmpty(smsSignature.getSmsSignatureName())){
                    String sendMessage = smsSignature.getSmsSignatureName()+targetHead.getSmsTaskMaterialContent();
                    smsTaskDetail.setSendMessage(sendMessage);
                }
                smsTaskDetail.setSendTime(new Date(System.currentTimeMillis()));
                smsTaskDetail.setSmsTaskHeadId(taskHeadId);
                smsTaskDetailList.add(smsTaskDetail);
            }
        }else if(smsType.equals(VARIABLE.getStatusCode())){
            //Todo:整体上要完成一个变量短信的变量替换过程,那么整体上要完成获取变量目标,准备好源短信,根据短信映射规则获取建立起源到目标的Map,完成替换.
            //Todo:1查出对应的优惠券信息
            SmsMaterialMaterielMap paramSmsMaterialMaterielMap = new SmsMaterialMaterielMap();
            paramSmsMaterialMaterielMap.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
            paramSmsMaterialMaterielMap.setSmsMaterialId(targetHead.getSmsTaskMaterialId());
            List<SmsMaterialMaterielMap> smsMaterialMaterielMapList = smsMaterialMaterielMapDao.selectList(paramSmsMaterialMaterielMap);

            //Todo:2.1查出这个素材对应的具体的优惠券信息
            MaterialCoupon mcp = materialCouponDao.selectOneCoupon(smsMaterialMaterielMapList.get(0).getSmsMaterielId());
            CouPonEditInfoOut couPonEditInfoOut = new CouPonEditInfoOut();
            BeanUtils.copyProperties(mcp, couPonEditInfoOut);
            couPonEditInfoOut.setAmount(mcp.getAmount()==null?0:mcp.getAmount().doubleValue());
            couPonEditInfoOut.setStartTime(mcp.getStartTime() == null ? 0 : mcp.getStartTime().getTime());
            couPonEditInfoOut.setEndTime(mcp.getEndTime() == null ? 0 : mcp.getEndTime().getTime());

            //Todo:2.2创建一个方法传入变量名称和优惠券信息,返回该变量对应的值,需要增加物料类型在传入参数中,针对优惠码等特殊变量要传入额外字段,这块可以做成策略模式，方便日后做扩展
            //Todo:2查出对应的变量信息
            SmsMaterialVariableMap paramSmsMaterialVariableMap = new SmsMaterialVariableMap();
            paramSmsMaterialVariableMap.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
            paramSmsMaterialVariableMap.setSmsMaterialId(targetHead.getSmsTaskMaterialId());
            List<SmsMaterialVariableMap> smsMaterialVariableMapList = smsMaterialVariableMapDao.selectList(paramSmsMaterialVariableMap);

            //Todo:5优惠码必定比手机号要多,將手机号拆成1W一页的然后逐页生成
            List<String> targetDistinctReceiveMobileList = new ArrayList<>(targetDistinctReceiveMobiles);
            int totalPage = (targetDistinctReceiveMobileList.size() + PAGE_SIZE) / PAGE_SIZE;
            for(int index = 0; index < totalPage; index++){
                //Todo:1选出每页所需要的优惠码
                BaseOutput couponCodeList = couponCodeListService.couponCodeList(smsMaterialMaterielMapList.get(0).getSmsMaterielId(),index*PAGE_SIZE,PAGE_SIZE);

                //Todo:2拆分出每页的手机号
                List<String> subTargetDistinctReceiveMobileList = null;
                if(index == totalPage-1){
                    subTargetDistinctReceiveMobileList = targetDistinctReceiveMobileList.subList(index*PAGE_SIZE,targetDistinctReceiveMobileList.size());
                }else{
                    subTargetDistinctReceiveMobileList = targetDistinctReceiveMobileList.subList(index*PAGE_SIZE,(index+1)*PAGE_SIZE);
                }

                //Todo:3进行短信内容的生成
                int couponCodeIndex = 0;
                for(String distinctReceiveMobile : subTargetDistinctReceiveMobileList){
                    MaterialCouponCode materialCouponCode = (MaterialCouponCode) couponCodeList.getData().get(couponCodeIndex);
                    couponCodeIndex++;
                    SmsTaskDetail smsTaskDetail = new SmsTaskDetail();
                    smsTaskDetail.setReceiveMobile(distinctReceiveMobile);
                    if(smsSignature != null && StringUtils.isNotEmpty(smsSignature.getSmsSignatureName())){
                        //Todo:3进行一次初始替换,替换变量值,然后用一个Map保存起来
                        Map<String,String> variableToValueMap = new HashMap<>();
                        for(SmsMaterialVariableMap smsMaterialVariableMap : smsMaterialVariableMapList){
                            if(SmsMaterialVariableEnum.VARIABLE_COUPON_COUPON_CODE.getVariableName().equals(smsMaterialVariableMap.getSmsVariableValue())){
                                setVariableValueInMaterialVariable(variableToValueMap,smsMaterialVariableMap,couPonEditInfoOut,materialCouponCode.getCode());
                                continue;
                            }
                            setVariableValueInMaterialVariable(variableToValueMap,smsMaterialVariableMap,couPonEditInfoOut,null);
                        }

                        for(String key : variableToValueMap.keySet()){
                            targetHead.setSmsTaskMaterialContent(targetHead.getSmsTaskMaterialContent().replace(key,variableToValueMap.get(key)));
                        }

                        String sendMessage = smsSignature.getSmsSignatureName()+targetHead.getSmsTaskMaterialContent();
                        smsTaskDetail.setSendMessage(sendMessage);
                    }
                    smsTaskDetail.setMaterielCouponCodeId(materialCouponCode.getId());
                    smsTaskDetail.setMaterielCouponCodeCode(materialCouponCode.getCode());
                    smsTaskDetail.setSendTime(new Date(System.currentTimeMillis()));
                    smsTaskDetail.setSmsTaskHeadId(taskHeadId);
                    smsTaskDetailList.add(smsTaskDetail);
                }
            }


        }
    }

    private void setVariableValueInMaterialVariable(Map<String, String> variableToValueMap, SmsMaterialVariableMap smsMaterialVariableMap, CouPonEditInfoOut couPonEditInfoOut, String specialTypeValue) {
        SmsMaterialVariableEnum smsMaterialVariableEnum = SmsMaterialVariableEnum.getSmsMaterialVariableEnum(smsMaterialVariableMap.getSmsVariableType(),smsMaterialVariableMap.getSmsVariableValue());
        switch (smsMaterialVariableEnum){
            case VARIABLE_COUPON_EFFECTIVE_TIME:
                String startTime = DateUtil.getStringFromDate(new Date(couPonEditInfoOut.getStartTime()),"MM月dd日-");
                String endTime = DateUtil.getStringFromDate(new Date(couPonEditInfoOut.getEndTime()),"MM月dd日");
                variableToValueMap.put(deractorSmsVariableName(smsMaterialVariableMap.getSmsVariableName()),startTime + endTime);
                break;
            case VARIABLE_COUPON_AMOUNT:
                variableToValueMap.put(deractorSmsVariableName(smsMaterialVariableMap.getSmsVariableName()),couPonEditInfoOut.getAmount() + "");
                break;
            case VARIABLE_COUPON_CHANNEL_CODE:
                variableToValueMap.put(deractorSmsVariableName(smsMaterialVariableMap.getSmsVariableName()),couPonEditInfoOut.getChannelCode());
                break;
            case VARIABLE_COUPON_COUPON_CODE:
                variableToValueMap.put(deractorSmsVariableName(smsMaterialVariableMap.getSmsVariableName()),specialTypeValue);
                break;
        }
    }

    private String deractorSmsVariableName(String smsVariableName) {
        return FRONT_SMS_VARIABLE_MASK + smsVariableName + BACK_SMS_VARIABLE_MASK;
    }


//    private List<String> queryReceiveMobileListByTargetAudienceIdAndType(SmsTaskBody smsTaskBody) {
//        if(SmsTargetAudienceTypeEnum.SMS_TARGET_SEGMENTATION.getTypeCode() == smsTaskBody.getTargetType()){
//            return queryAndCacheAudienceDetailBySegmentationId(smsTaskBody.getSmsTaskHeadId(),smsTaskBody.getTargetId());
//        }else if(SmsTargetAudienceTypeEnum.SMS_TARGET_AUDIENCE.getTypeCode() == smsTaskBody.getTargetType()){
//            return queryAndCacheAudienceDetailByAudienceListId(smsTaskBody.getSmsTaskHeadId(),smsTaskBody.getTargetId());
//        }
//        return null;
//    }
//
//    private List<String> queryAndCacheAudienceDetailBySegmentationId(Long taskHeadId,Long targetId) {
//        //1根据segmentation的Id在Redis中选出相应得细分,然后取出dataPartyId
//        List<Long> dataPartyIdList = new ArrayList<>();
//		Set<String> mids = new HashSet<>();
//		try {
//			mids = JedisClient.smembers("segmentcoverid:"+targetId, POOL_INDEX);
//		} catch (JedisException e) {
//			e.printStackTrace();
//		}
//		if(CollectionUtils.isEmpty(mids)) return null;
//		for(String mid : mids){
//			dataPartyIdList.add(Long.valueOf(mid));
//		}
//        logger.info("dataParty id list size : " + dataPartyIdList.size());
//
//        //3将选出来的这些数据做缓存存到cache表中,一开始先一条一条插入,后续优化成使用batchInsert进行插入
//        if(CollectionUtils.isEmpty(dataPartyIdList)) return null;
//        cacheDataPartyIdInSmsAudienceCache(taskHeadId, targetId, dataPartyIdList);
//
//        //4根据dataPartyIdList选出相应的不同的mobile(去重),然后做为返回值返回
//        Set<String> distinctMobileSet = new HashSet<>();
//        List<String> subDistinctMobileList = null;
//        int totalPage = (dataPartyIdList.size() + PAGE_SIZE)/PAGE_SIZE;
//        for(int index = 0; index < totalPage; index++){
//            if(index == totalPage - 1){
//                subDistinctMobileList = dataPartyDao.selectDistinctMobileListByIdList(dataPartyIdList.subList(index*PAGE_SIZE,dataPartyIdList.size()));
//                logger.info("index : " + index);
//            }else {
//                subDistinctMobileList = dataPartyDao.selectDistinctMobileListByIdList(dataPartyIdList.subList(index*PAGE_SIZE,(index+1) * PAGE_SIZE));
//                logger.info("index : " + index);
//            }
//            distinctMobileSet.addAll(subDistinctMobileList);
//        }
//        logger.info("already search distinct mobile list " + subDistinctMobileList.size());
//        List<String> distinctMobileList = new LinkedList<>();
//        distinctMobileList.addAll(distinctMobileSet);
//        if(CollectionUtils.isEmpty(distinctMobileList)) return null;
//        return distinctMobileList;
//    }
//
//    private List<String> queryAndCacheAudienceDetailByAudienceListId(Long taskHeadId,Long targetId) {
//        //1将data_party的Id从audienceListPartyMap表中取出来
//        AudienceListPartyMap paramAudienceListPartyMap = new AudienceListPartyMap();
//        paramAudienceListPartyMap.setAudienceListId(targetId.intValue());
//        paramAudienceListPartyMap.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
//        Integer totalCount = audienceListPartyMapDao.selectListCount(paramAudienceListPartyMap);
//        int totalAudienceListPartyMapPage = (totalCount+PAGE_SIZE)/PAGE_SIZE;
//        List<AudienceListPartyMap> audienceListPartyMapList =new ArrayList<>();
//        for(int index = 0; index < totalAudienceListPartyMapPage; index++){
//            paramAudienceListPartyMap.setStartIndex(index * PAGE_SIZE + 1);
//            paramAudienceListPartyMap.setPageSize(PAGE_SIZE);
//            List<AudienceListPartyMap> subAudienceListPartyMapList = audienceListPartyMapDao.selectList(paramAudienceListPartyMap);
//            audienceListPartyMapList.addAll(subAudienceListPartyMapList);
//        }
//        if(CollectionUtils.isEmpty(audienceListPartyMapList)) return null;
//
//        //2构造出DataPartIdList得合集
//        List<Long> dataPartyIdList = new ArrayList<>();
//        for(AudienceListPartyMap audienceListPartyMap : audienceListPartyMapList){
//            if(audienceListPartyMap.getPartyId() != null){
//                dataPartyIdList.add(Long.valueOf(audienceListPartyMap.getPartyId()));
//            }
//        }
//        if(CollectionUtils.isEmpty(dataPartyIdList)) return null;
//
//        //3将选出来的这些数据做缓存存到cache表中,一开始先一条一条插入,后续优化成使用batchInsert进行插入
//        cacheDataPartyIdInSmsAudienceCache(taskHeadId, targetId, dataPartyIdList);
//
//        //4根据dataPartyIdList选出相应的不同的mobile(去重),然后做为返回值返回
//        Set<String> distinctMobileSet = new HashSet<>();
//        List<String> subDistinctMobileList = null;
//        int totalPage = (dataPartyIdList.size() + PAGE_SIZE)/PAGE_SIZE;
//        for(int index = 0; index < totalPage; index++){
//            if(index == totalPage - 1){
//                subDistinctMobileList = dataPartyDao.selectDistinctMobileListByIdList(dataPartyIdList.subList(index*PAGE_SIZE,dataPartyIdList.size()));
//                logger.info("index : " + index);
//            }else {
//                subDistinctMobileList = dataPartyDao.selectDistinctMobileListByIdList(dataPartyIdList.subList(index*PAGE_SIZE,(index+1) * PAGE_SIZE));
//                logger.info("index : " + index);
//            }
//            distinctMobileSet.addAll(subDistinctMobileList);
//        }
//        logger.info("already search distinct mobile list " + subDistinctMobileList.size());
//        List<String> distinctMobileList = new LinkedList<>();
//        distinctMobileList.addAll(distinctMobileSet);
//        if(CollectionUtils.isEmpty(distinctMobileList)) return null;
//        return distinctMobileList;
//    }
//
//    private void cacheDataPartyIdInSmsAudienceCache(Long taskHeadId, Long targetId, List<Long> dataPartyIdList) {
//        logger.info("begin to cache target audience");
//        //Todo:根据DataParty的Id將Mobile和Id选出来然后存放成List对象
//        List<AudienceIDAndMobilePO> audienceIdAndMobilePOList = new ArrayList<>();
//        Integer totalCount = dataPartyIdList.size();
//        int totalPage = (totalCount + PAGE_SIZE)/PAGE_SIZE;
//        for(int index = 0; index < totalPage; index++){
//            List<AudienceIDAndMobilePO> subAudienceIdAndMobileList = null;
//            if(index == totalPage - 1){
//                subAudienceIdAndMobileList = dataPartyDao.selectCacheAudienceListByIdList(dataPartyIdList.subList(index*PAGE_SIZE,dataPartyIdList.size()));
//                logger.info("index : " + index);
//            }else {
//                subAudienceIdAndMobileList = dataPartyDao.selectCacheAudienceListByIdList(dataPartyIdList.subList(index*PAGE_SIZE,(index+1) * PAGE_SIZE));
//                logger.info("index : " + index);
//            }
//            audienceIdAndMobilePOList.addAll(subAudienceIdAndMobileList);
//        }
//
//        List<SmsTaskTargetAudienceCache> smsTaskTargetAudienceCacheList = new LinkedList<>();
//        for(AudienceIDAndMobilePO audienceIDAndMobilePO : audienceIdAndMobilePOList){
//            SmsTaskTargetAudienceCache smsTaskTargetAudienceCache = new SmsTaskTargetAudienceCache();
//            smsTaskTargetAudienceCache.setTaskHeadId(taskHeadId);
//            smsTaskTargetAudienceCache.setTargetId(targetId);
//            smsTaskTargetAudienceCache.setDataPartyId(audienceIDAndMobilePO.getDataPartyId());
//            smsTaskTargetAudienceCache.setMobile(audienceIDAndMobilePO.getMobile());
//            smsTaskTargetAudienceCache.setTargetType(SmsTargetAudienceTypeEnum.SMS_TARGET_AUDIENCE.getTypeCode());
//            smsTaskTargetAudienceCacheList.add(smsTaskTargetAudienceCache);
//        }
//
//        int totalNum = (smsTaskTargetAudienceCacheList.size() + PAGE_SIZE) / PAGE_SIZE;
//        for(int index = 0; index < totalNum; index++){
//            if(index == totalNum-1){
//                logger.info("insert index : " + index);
//                smsTaskTargetAudienceCacheDao.batchInsert(smsTaskTargetAudienceCacheList.subList(index*PAGE_SIZE,smsTaskTargetAudienceCacheList.size()));
//            }else{
//                logger.info("insert index : " + index);
//                smsTaskTargetAudienceCacheDao.batchInsert(smsTaskTargetAudienceCacheList.subList(index*PAGE_SIZE,(index + 1)*PAGE_SIZE));
//            }
//        }
//    }
}
