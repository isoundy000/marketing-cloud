package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.*;
import cn.rongcapital.mkt.po.WechatAsset;
import cn.rongcapital.mkt.po.WechatMember;
import cn.rongcapital.mkt.po.WechatMemberCountCache;
import cn.rongcapital.mkt.service.GetWechatUserListService;
import cn.rongcapital.mkt.vo.out.WechatUserListData;
import cn.rongcapital.mkt.vo.out.WechatUserListOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Yunfeng on 2016-7-26.
 */
@Service
public class GetWechatUserListServiceImpl implements GetWechatUserListService{

    //Todo:1.将时间数据先组织起来，因为这个不依赖于数据库
    private static Integer DATA_LIST_SIZE = 12;
    private static Integer MAX_MONTH_VALUE = 12;

    //Todo:2.组织服务号，个人号，订阅号的数据
    private static Integer SERVER_ACCOUNT_TYPE = 2;
    private static Integer REGISTER_ACCOUNT_TYPE = 1;
    private static Integer PERSONAL_ACCOUNT_TYPE = 0;

    private static String SERVER_ACCOUNT_TYPE_NAME = "服务号关注量";
    private static String REGISTER_ACCOUNT_TYPE_NAME = "订阅号关注量";
    private static String PERSONAL_ACCOUNT_TYPE_NAME = "个人号关注量";

    @Autowired
    private WechatMemberCountCacheDao wechatMemberCountCacheDao;

    @Override
    public WechatUserListOut getWechatUserListByType() {
        WechatUserListOut wechatUserListOut = new WechatUserListOut(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO);

        //1将时间数据先组织起来，因为这个不依赖于数据库
        WechatUserListData<String> timeDataList = new WechatUserListData<String>();
        timeDataList.setName("date");
        Calendar calendar = Calendar.getInstance();
        Integer baseYear = calendar.get(Calendar.YEAR);
        Integer baseMonth = calendar.get(Calendar.MONTH) + 1;
        buildTimeDataForTimeDataList(baseYear,baseMonth,timeDataList);
        wechatUserListOut.getDataCustom().add(timeDataList);

        //Todo：2.从人数缓存数据表中获取相关的人数
        //Todo: 2.1获取相应的服务号的人数
        WechatUserListData<Integer> serverDataList = new WechatUserListData<Integer>();
        serverDataList.setName(SERVER_ACCOUNT_TYPE_NAME);
        buildDataListByType(SERVER_ACCOUNT_TYPE,timeDataList,serverDataList);
        wechatUserListOut.getDataCustom().add(serverDataList);

        //Todo: 2.3获取相应的个人号的人数
        WechatUserListData<Integer> personalDataList = new WechatUserListData<Integer>();
        personalDataList.setName(PERSONAL_ACCOUNT_TYPE_NAME);
        buildDataListByType(PERSONAL_ACCOUNT_TYPE,timeDataList,personalDataList);
        wechatUserListOut.getDataCustom().add(personalDataList);

        //Todo: 2.2获取相应的订阅号的人数
        WechatUserListData<Integer> registerDataList = new WechatUserListData<Integer>();
        registerDataList.setName(REGISTER_ACCOUNT_TYPE_NAME);
        buildDataListByType(REGISTER_ACCOUNT_TYPE,timeDataList,registerDataList);
        wechatUserListOut.getDataCustom().add(registerDataList);

        return wechatUserListOut;
    }

    private void buildDataListByType(Integer accountType, WechatUserListData<String> timeDataList, WechatUserListData<Integer> dataList) {
        dataList.setValueData(new ArrayList<>(DATA_LIST_SIZE));
        WechatMemberCountCache wechatMemberCountCache = new WechatMemberCountCache();
        for(int index = 0; index < timeDataList.getValueData().size(); index++){
            String searchTimeIndex = timeDataList.getValueData().get(index);
            wechatMemberCountCache.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
            wechatMemberCountCache.setCountTime(searchTimeIndex);
            wechatMemberCountCache.setWechatAssetType(accountType);
            Integer searchTimeCount = wechatMemberCountCacheDao.selectCountByTypeAndTimeIndex(wechatMemberCountCache);
            if(searchTimeCount == null){
                dataList.getValueData().add(0);
            }else{
                dataList.getValueData().add(searchTimeCount);
            }
        }
    }

    private void buildTimeDataForTimeDataList(Integer baseYear, Integer baseMonth, WechatUserListData<String> timeDataList) {
        timeDataList.setValueData(new ArrayList(DATA_LIST_SIZE));
        //Debug发现即使申请了初始容量，在按索引添加元素时仍然会报数组越界的错误，因此对数组用进行初始化。
        for(int index = 0 ; index < DATA_LIST_SIZE; index ++){
            timeDataList.getValueData().add("" + index);
        }
        for(int index = DATA_LIST_SIZE - 1; index > -1; index-- ){
            String timeData = baseYear + "/" + formatMonth(baseMonth);
            baseMonth--;
            if(baseMonth == 0){
                baseYear --;
                baseMonth = MAX_MONTH_VALUE;
            }
            timeDataList.getValueData().set(index,timeData);
        }
    }

    private String formatMonth(Integer baseMonth) {
        if(baseMonth < 10){
            return "0" + baseMonth;
        }
        return  Integer.toString(baseMonth);
    }

}
