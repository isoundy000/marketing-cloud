package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.AudienceListPartyMapDao;
import cn.rongcapital.mkt.po.AudienceListPartyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by byf on 12/19/16.
 */
@Service
public class AudienceCalcSmsTargetAudienceStrategy extends AbstractCalcSmsTargetAudienceStrategy{

    @Autowired
    private AudienceListPartyMapDao audienceListPartyMapDao;


    private final int PAGE_SIZE = 10000;

    @Override
    protected List<Long> queryDataPartyIdList(@NotNull Long targetId) {
        //1将data_party的Id从audienceListPartyMap表中取出来
        AudienceListPartyMap paramAudienceListPartyMap = new AudienceListPartyMap();
        paramAudienceListPartyMap.setAudienceListId(targetId.intValue());
        paramAudienceListPartyMap.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        Integer totalCount = audienceListPartyMapDao.selectListCount(paramAudienceListPartyMap);
        int totalAudienceListPartyMapPage = (totalCount+PAGE_SIZE)/PAGE_SIZE;
        List<AudienceListPartyMap> audienceListPartyMapList =new ArrayList<>();
        for(int index = 0; index < totalAudienceListPartyMapPage; index++){
            paramAudienceListPartyMap.setStartIndex(index * PAGE_SIZE + 1);
            paramAudienceListPartyMap.setPageSize(PAGE_SIZE);
            List<AudienceListPartyMap> subAudienceListPartyMapList = audienceListPartyMapDao.selectList(paramAudienceListPartyMap);
            audienceListPartyMapList.addAll(subAudienceListPartyMapList);
        }
        if(CollectionUtils.isEmpty(audienceListPartyMapList)) return null;

        //2构造出DataPartIdList得合集
        List<Long> dataPartyIdList = new ArrayList<>();
        for(AudienceListPartyMap audienceListPartyMap : audienceListPartyMapList){
            if(audienceListPartyMap.getPartyId() != null){
                dataPartyIdList.add(Long.valueOf(audienceListPartyMap.getPartyId()));
            }
        }
        return dataPartyIdList;
    }
}
