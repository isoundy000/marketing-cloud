package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.jedis.JedisClient;
import cn.rongcapital.mkt.common.jedis.JedisException;
import cn.rongcapital.mkt.dao.DataPartyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by byf on 12/19/16.
 */
@Service
public class SegmentCalcSmsTargetAudienceStrategy extends AbstractCalcSmsTargetAudienceStrategy{

    @Autowired
    private DataPartyDao dataPartyDao;

    public static final Integer POOL_INDEX = 2;

    @Override
    protected List<Long> queryDataPartyIdList(@NotNull Long targetId) {
        List<Long> dataPartyIdList = new ArrayList<>();
        Set<String> mids = new HashSet<>();
        try {
            mids = JedisClient.smembers("segmentcoverid:"+targetId, POOL_INDEX);
        } catch (JedisException e) {
            e.printStackTrace();
        }
        if(CollectionUtils.isEmpty(mids)) return null;
        for(String mid : mids){
            dataPartyIdList.add(Long.valueOf(mid));
        }
//        dataPartyDao.selectDistinctMobileListByIdList(dataPartyIdList);
        return dataPartyIdList;
    }

}
