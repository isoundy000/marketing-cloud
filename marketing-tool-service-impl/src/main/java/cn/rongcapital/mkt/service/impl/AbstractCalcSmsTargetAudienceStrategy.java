package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.enums.SmsTargetAudienceTypeEnum;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.dao.SmsTaskTargetAudienceCacheDao;
import cn.rongcapital.mkt.po.AudienceIDAndMobilePO;
import cn.rongcapital.mkt.po.SmsTaskTargetAudienceCache;
import cn.rongcapital.mkt.service.QueryReceiveMobileListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * Created by byf on 12/19/16.
 */
@PropertySource("classpath:${conf.dir}/application-api.properties")
public abstract class AbstractCalcSmsTargetAudienceStrategy implements QueryReceiveMobileListService {

    @Autowired
    private DataPartyDao dataPartyDao;

    @Autowired
    private SmsTaskTargetAudienceCacheDao smsTaskTargetAudienceCacheDao;

    private final int PAGE_SIZE = 10000;
    private Logger logger = LoggerFactory.getLogger(getClass());

    abstract protected List<Long> queryDataPartyIdList(@NotNull Long targetId);

    public List<String> queryReceiveMobileList(@NotNull Long taskHeadId,@NotNull Long targetId){
        List<Long> dataPartyIdList = queryDataPartyIdList(targetId);

        //3将选出来的这些数据做缓存存到cache表中,一开始先一条一条插入,后续优化成使用batchInsert进行插入
        if(CollectionUtils.isEmpty(dataPartyIdList)) return null;
        cacheDataPartyIdInSmsAudienceCache(taskHeadId, targetId, dataPartyIdList);

        //4根据dataPartyIdList选出相应的不同的mobile(去重),然后做为返回值返回
        Set<String> distinctMobileSet = new HashSet<>();
        List<String> subDistinctMobileList = null;
        int totalPage = (dataPartyIdList.size() + PAGE_SIZE)/PAGE_SIZE;
        for(int index = 0; index < totalPage; index++){
            if(index == totalPage - 1){
                subDistinctMobileList = dataPartyDao.selectDistinctMobileListByIdList(dataPartyIdList.subList(index*PAGE_SIZE,dataPartyIdList.size()));
                logger.info("index : " + index);
            }else {
                subDistinctMobileList = dataPartyDao.selectDistinctMobileListByIdList(dataPartyIdList.subList(index*PAGE_SIZE,(index+1) * PAGE_SIZE));
                logger.info("index : " + index);
            }
            distinctMobileSet.addAll(subDistinctMobileList);
        }
        logger.info("already search distinct mobile list " + subDistinctMobileList.size());
        List<String> distinctMobileList = new LinkedList<>();
        distinctMobileList.addAll(distinctMobileSet);
        if(CollectionUtils.isEmpty(distinctMobileList)) return null;
        return distinctMobileList;
    }

    protected void cacheDataPartyIdInSmsAudienceCache(Long taskHeadId, Long targetId, List<Long> dataPartyIdList) {
        logger.info("begin to cache target audience");
        //Todo:根据DataParty的Id將Mobile和Id选出来然后存放成List对象
        List<AudienceIDAndMobilePO> audienceIdAndMobilePOList = new ArrayList<>();
        Integer totalCount = dataPartyIdList.size();
        int totalPage = (totalCount + PAGE_SIZE)/PAGE_SIZE;
        for(int index = 0; index < totalPage; index++){
            List<AudienceIDAndMobilePO> subAudienceIdAndMobileList = null;
            if(index == totalPage - 1){
                subAudienceIdAndMobileList = dataPartyDao.selectCacheAudienceListByIdList(dataPartyIdList.subList(index*PAGE_SIZE,dataPartyIdList.size()));
                logger.info("index : " + index);
            }else {
                subAudienceIdAndMobileList = dataPartyDao.selectCacheAudienceListByIdList(dataPartyIdList.subList(index*PAGE_SIZE,(index+1) * PAGE_SIZE));
                logger.info("index : " + index);
            }
            audienceIdAndMobilePOList.addAll(subAudienceIdAndMobileList);
        }

        List<SmsTaskTargetAudienceCache> smsTaskTargetAudienceCacheList = new LinkedList<>();
        for(AudienceIDAndMobilePO audienceIDAndMobilePO : audienceIdAndMobilePOList){
            SmsTaskTargetAudienceCache smsTaskTargetAudienceCache = new SmsTaskTargetAudienceCache();
            smsTaskTargetAudienceCache.setTaskHeadId(taskHeadId);
            smsTaskTargetAudienceCache.setTargetId(targetId);
            smsTaskTargetAudienceCache.setDataPartyId(audienceIDAndMobilePO.getDataPartyId());
            smsTaskTargetAudienceCache.setMobile(audienceIDAndMobilePO.getMobile());
            smsTaskTargetAudienceCache.setTargetType(SmsTargetAudienceTypeEnum.SMS_TARGET_AUDIENCE.getTypeCode());
            smsTaskTargetAudienceCacheList.add(smsTaskTargetAudienceCache);
        }

        int totalNum = (smsTaskTargetAudienceCacheList.size() + PAGE_SIZE) / PAGE_SIZE;
        for(int index = 0; index < totalNum; index++){
            if(index == totalNum-1){
                logger.info("insert index : " + index);
                smsTaskTargetAudienceCacheDao.batchInsert(smsTaskTargetAudienceCacheList.subList(index*PAGE_SIZE,smsTaskTargetAudienceCacheList.size()));
            }else{
                logger.info("insert index : " + index);
                smsTaskTargetAudienceCacheDao.batchInsert(smsTaskTargetAudienceCacheList.subList(index*PAGE_SIZE,(index + 1)*PAGE_SIZE));
            }
        }
    }
}
