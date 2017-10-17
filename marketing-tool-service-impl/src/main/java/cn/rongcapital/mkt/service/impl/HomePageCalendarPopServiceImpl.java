package cn.rongcapital.mkt.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.enums.CampaignHeadStatusEnum;
import cn.rongcapital.mkt.dao.CampaignHeadDao;
import cn.rongcapital.mkt.po.CampaignHead;
import cn.rongcapital.mkt.service.HomePageCalendarPopService;
import cn.rongcapital.mkt.vo.out.HomePageCalendarPopData;
import cn.rongcapital.mkt.vo.out.HomePageCalendarPopOut;

@Service
public class HomePageCalendarPopServiceImpl implements HomePageCalendarPopService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CampaignHeadDao campaignHeadDao;

    @Override
    public HomePageCalendarPopOut getCalendarPop(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        HomePageCalendarPopOut result = new HomePageCalendarPopOut();

        if (StringUtils.isEmpty(date)) {
            logger.info("活动日历弹窗时传入的参数date为空");
            return result;
        }

        result.setDate(date);
        Calendar now = Calendar.getInstance();
        Calendar startTime = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();
        try {
            now.setTime(simpleDateFormat.parse(date));
        } catch (ParseException e) {
            logger.info("活动日历弹窗时传入的参数有问题 : {}", date);
            return result;
        }

        // 初始化下时间,将时间定义为date这天的0点0分0秒到下一天的0点0分0秒
        initParamTime(startTime, endTime, now);
        Map<String, Date> paramMap = new HashMap<>();
        paramMap.put("startTime", startTime.getTime());
        paramMap.put("endTime", endTime.getTime());
        List<CampaignHead> campaignHeads = campaignHeadDao.selectInProgressandPrepareStatusCampaignHeadLimitation(paramMap);
        List<HomePageCalendarPopData> content = new ArrayList<>();
        if (!CollectionUtils.isEmpty(campaignHeads)) {
            for (CampaignHead campaignHead : campaignHeads) {
                HomePageCalendarPopData homePageCalendarPopDataObj = new HomePageCalendarPopData();
                homePageCalendarPopDataObj.setActivity(campaignHead.getName());
                homePageCalendarPopDataObj.setStatus(campaignHead.getStatus());
                homePageCalendarPopDataObj
                                .setStatusDescription(CampaignHeadStatusEnum.getStatusByCode(campaignHead.getStatus()));

                content.add(homePageCalendarPopDataObj);
            }
        }

        result.setContent(content);
        return result;
    }

    // 将时间段定义为now这一天的时间段
    private void initParamTime(Calendar startTime, Calendar endTime, Calendar now) {
        startTime.setTime(now.getTime());
        endTime.setTime(now.getTime());

        startTime.set(startTime.get(Calendar.YEAR), startTime.get(Calendar.MONTH), startTime.get(Calendar.DATE), 0, 0,
                        0);
        endTime.set(endTime.get(Calendar.YEAR), endTime.get(Calendar.MONTH), startTime.get(Calendar.DATE) + 1, 0, 0, 0);
        startTime.set(Calendar.MILLISECOND, 0);
        endTime.set(Calendar.MILLISECOND, 0);
    }
}
