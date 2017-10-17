package cn.rongcapital.mkt.job.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.WechatAssetDao;
import cn.rongcapital.mkt.dao.WechatAssetGroupDao;
import cn.rongcapital.mkt.dao.WechatMemberCountCacheDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.WechatAsset;
import cn.rongcapital.mkt.po.WechatAssetGroup;
import cn.rongcapital.mkt.po.WechatMemberCountCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

/**
 * Created by Yunfeng on 2016-7-26.
 */
@Service
public class CacheWechatMemberCountByTypeServiceImpl implements TaskService{

    //这样我查的时候只要查这个缓存表就OK了
    private static Integer SERVER_ACCOUNT_TYPE = 2;
    private static Integer REGISTER_ACCOUNT_TYPE = 1;
    private static Integer PERSONAL_ACCOUNT_TYPE = 0;

    @Autowired
    private WechatAssetDao wechatAssetDao;

    @Autowired
    private WechatMemberCountCacheDao wechatMemberCountCacheDao;

    @Autowired
    private WechatAssetGroupDao wechatAssetGroupDao;

    @Override
    public void task(Integer taskId) {

        String nowTime = getCountTime(); //获取统计时间
        //1获取服务号，订阅号的总数并且将其缓存到Cache表中
        cachePubFansCount(nowTime,searchMemberCountByType(SERVER_ACCOUNT_TYPE),SERVER_ACCOUNT_TYPE);  //缓存服务号人数
        cachePubFansCount(nowTime,searchMemberCountByType(REGISTER_ACCOUNT_TYPE),REGISTER_ACCOUNT_TYPE);  //缓存订阅号人数
        cachePubFansCount(nowTime,searchFriendCount(),PERSONAL_ACCOUNT_TYPE);  //缓存个人号好友人数
    }

    private Integer searchFriendCount() {
        WechatAssetGroup wechatAssetGroup = new WechatAssetGroup();
        wechatAssetGroup.setName("好友组");
        wechatAssetGroup.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        return wechatAssetGroupDao.selectFriendCount(wechatAssetGroup);
    }

    private String getCountTime() {
        Calendar calendar = Calendar.getInstance();
        Integer year = calendar.get(Calendar.YEAR);
        Integer month = calendar.get(Calendar.MONTH) + 1;
        return year + "/" + formatMonth(month);
    }

    private void cachePubFansCount(String nowTime, Integer fansCount, Integer pubAccountType) {
        WechatMemberCountCache wechatMemberCountCache = new WechatMemberCountCache();
        wechatMemberCountCache.setCountTime(nowTime);
        wechatMemberCountCache.setWechatAssetType(pubAccountType);
        if(fansCount != null){
            wechatMemberCountCache.setWechatAssetTypeMemberCount(fansCount);
        }else{
            wechatMemberCountCache.setWechatAssetTypeMemberCount(0);
        }
        wechatMemberCountCacheDao.insert(wechatMemberCountCache);
    }

    private Integer searchMemberCountByType(Integer pubAccountType) {
        WechatAsset wechatAsset = new WechatAsset();
        wechatAsset.setAssetType(pubAccountType);
        wechatAsset.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        return wechatAssetDao.selectMemberCountByType(wechatAsset);
    }

    private String formatMonth(Integer baseMonth) {
        if(baseMonth < 10){
            return "0" + baseMonth;
        }
        return  Integer.toString(baseMonth);
    }
}
