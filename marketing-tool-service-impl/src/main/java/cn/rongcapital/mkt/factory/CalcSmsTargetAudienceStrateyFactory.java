package cn.rongcapital.mkt.factory;

import cn.rongcapital.mkt.common.enums.SmsTargetAudienceTypeEnum;
import cn.rongcapital.mkt.service.impl.AbstractCalcSmsTargetAudienceStrategy;
import cn.rongcapital.mkt.service.impl.AudienceCalcSmsTargetAudienceStrategy;
import cn.rongcapital.mkt.service.impl.SegmentCalcSmsTargetAudienceStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by byf on 12/19/16.
 */
@Service
public class CalcSmsTargetAudienceStrateyFactory {

    @Autowired
    private SegmentCalcSmsTargetAudienceStrategy segmentCalcSmsTargetAudienceStrategy;

    @Autowired
    private AudienceCalcSmsTargetAudienceStrategy audienceCalcSmsTargetAudienceStrategy;

    private Map<Integer,AbstractCalcSmsTargetAudienceStrategy> strategyMap = new HashMap<>();

    private CalcSmsTargetAudienceStrateyFactory(){

    }

    public AbstractCalcSmsTargetAudienceStrategy creator(Integer type){
        return strategyMap.get(type);
    }
}
