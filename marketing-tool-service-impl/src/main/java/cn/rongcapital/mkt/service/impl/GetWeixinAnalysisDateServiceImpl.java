package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.WechatChannelDao;
import cn.rongcapital.mkt.dao.WechatQrcodeDao;
import cn.rongcapital.mkt.dao.WechatQrcodeFocusDao;
import cn.rongcapital.mkt.po.WechatChannel;
import cn.rongcapital.mkt.po.WechatQrcode;
import cn.rongcapital.mkt.po.WechatQrcodeFocus;
import cn.rongcapital.mkt.service.GetWeixinAnalysisDateService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by Yunfeng on 2016-8-31.
 */
@Service
public class GetWeixinAnalysisDateServiceImpl implements GetWeixinAnalysisDateService {

    private static final String DAY_7_SCOPE = "Day7";
    private static final String DAY_30_SCOPE = "Day30";

    @Autowired
    private WechatQrcodeFocusDao wechatQrcodeFocusDao;

    @Autowired
    private WechatQrcodeDao wechatQrcodeDao;

    @Autowired
    private WechatChannelDao wechatChannelDao;

    @Override
    public BaseOutput getWeixinAnalysisDate(String qrcodeId) {
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,null);
        WechatAnalysisDateOut wechatAnalysisDateOut = new WechatAnalysisDateOut();

        //获取今天的时间
        Calendar calendar = Calendar.getInstance();
        String todayDate = DateUtil.getStringFromDate(calendar.getTime(),"yyyy-MM-dd");
        TodayOut todayOut = new TodayOut();
        todayOut.setToday(todayDate);
        todayOut.setStarDate(todayDate);
        todayOut.setEndDate(todayDate);
        wechatAnalysisDateOut.setTodayOut(todayOut);

        //获取昨天的时间
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        String yesToday = DateUtil.getStringFromDate(calendar.getTime(),"yyyy-MM-dd");
        YestodayOut yestodayOut = new YestodayOut();
        yestodayOut.setYestoday(yesToday);
        yestodayOut.setStarDate(yesToday);
        yestodayOut.setEndDate(todayDate);
        wechatAnalysisDateOut.setYesDdayOut(yestodayOut);

        //获取7天前的时间
        calendar.add(calendar.DAY_OF_MONTH,1);
        calendar.add(calendar.DAY_OF_MONTH,-7);
        String day7Ago = DateUtil.getStringFromDate(calendar.getTime(),"yyyy-MM-dd");
        Day7Out day7Out = new Day7Out();
        day7Out.setStarDate(day7Ago);
        day7Out.setDay7("");
        day7Out.setEndDate(todayDate);
        wechatAnalysisDateOut.setDay7Out(day7Out);

        //获取30天前的时间
        calendar.add(calendar.DAY_OF_MONTH,7);
        calendar.add(calendar.DAY_OF_MONTH,-30);
        String day30Ago = DateUtil.getStringFromDate(calendar.getTime(),"yyyy-MM-dd");
        Day30Out day30Out = new Day30Out();
        day30Out.setStarDate(day30Ago);
        day30Out.setDay30("");
        day30Out.setEndDate(todayDate);
        wechatAnalysisDateOut.setDay30Out(day30Out);

        //获取两年的总时间
        calendar.add(calendar.DAY_OF_MONTH,30);
        calendar.add(calendar.YEAR,-2);
        String year2Ago = DateUtil.getStringFromDate(calendar.getTime(),"yyyy-MM-dd");
        RecordScopeOut recordScopeOut = new RecordScopeOut();
        recordScopeOut.setStarDate(year2Ago);
        recordScopeOut.setEndDate(todayDate);
        recordScopeOut.setRecordScope("");
        wechatAnalysisDateOut.setRecordScopeOut(recordScopeOut);

        if(qrcodeId == null || qrcodeId.length()==0){
            constructNullResultForWxInfoAndChannelInfo(wechatAnalysisDateOut);
        }else {
            WechatQrcode wechatQrcode = new WechatQrcode();
            wechatQrcode.setId(Integer.valueOf(qrcodeId));
            List<WechatQrcode> wechatQrcodeList = wechatQrcodeDao.selectList(wechatQrcode);
            if(!CollectionUtils.isEmpty(wechatQrcodeList)){
                wechatAnalysisDateOut.setWxmpName(wechatQrcodeList.get(0).getWxName());
                wechatAnalysisDateOut.setWxAcct(wechatQrcodeList.get(0).getWxAcct());

                WechatChannel wechatChannel = new WechatChannel();
                wechatChannel.setId(wechatQrcodeList.get(0).getChCode());
                List<WechatChannel> wechatChannelList = wechatChannelDao.selectList(wechatChannel);
                if(!CollectionUtils.isEmpty(wechatChannelList)){
                    wechatAnalysisDateOut.setChName(wechatChannelList.get(0).getChName());
                    wechatAnalysisDateOut.setChCode(String.valueOf(wechatChannelList.get(0).getId()));
                }else {
                    wechatAnalysisDateOut.setChName("");
                    wechatAnalysisDateOut.setChCode("");
                }
            }else{
                constructNullResultForWxInfoAndChannelInfo(wechatAnalysisDateOut);
            }
        }

        result.getData().add(wechatAnalysisDateOut);
        result.setTotal(result.getData().size());
        return result;
    }

    private void constructNullResultForWxInfoAndChannelInfo(WechatAnalysisDateOut wechatAnalysisDateOut) {
        wechatAnalysisDateOut.setChCode("");
        wechatAnalysisDateOut.setChName("");
        wechatAnalysisDateOut.setWxmpName("");
        wechatAnalysisDateOut.setWxAcct("");
    }
}
