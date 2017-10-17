package cn.rongcapital.mkt.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.service.HomePageUserCountListService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class HomePageUserCountListServiceImpl implements HomePageUserCountListService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DataPartyDao dataPartyDao;


    @Override
    public BaseOutput getHomePageUserCountList(Integer dateType) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
                        ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
        try {
            // 返回结果集合
            Map<String, Object> resultMap = new HashMap<>();

            SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat hourFormat = new SimpleDateFormat("yyyy-MM-dd HH");
            SimpleDateFormat hourMinus = new SimpleDateFormat("HH:mm");
            // 判断是否是当天
            Integer arrayLength = dateType == 0 ? 24 : dateType;
            // 结果数组
            List<String> dataList = new ArrayList<>();
            List<Integer> countList = new ArrayList<>();
            // 当前日期_yyyy-MM-dd
            Date nowDateDay = dayFormat.parse(dayFormat.format(new Date()));
            // 当前日期_yyyy-MM-dd HH
            Date nowDateHour = hourFormat.parse(hourFormat.format(new Date()));
            // 当天零点
            Date minTime = hourFormat.parse(hourFormat.format(nowDateDay));
            Calendar calendar = new GregorianCalendar();
            int i = 0;
            if (dateType != 0) {
                // 临时日期变量
                Date tempDate = nowDateDay;
                calendar.setTime(tempDate);
                while (i < arrayLength) {
                    // 数量
                    Integer count = getCount(tempDate, 1);
                    countList.add(count);
                    dataList.add(dayFormat.format(tempDate));
                    // 向前推一天
                    calendar.add(Calendar.DATE, -1);
                    tempDate = calendar.getTime();
                    i++;
                }
            } else {
                // 临时日期变量
                Date tempDate = nowDateHour;
                calendar.setTime(tempDate);
                while (tempDate.compareTo(minTime) >= 0) {
                    // 数量
                    Integer count = getCount(tempDate, 0);
                    countList.add(count);
                    dataList.add(hourMinus.format(tempDate));
                    // 向前推一小时
                    calendar.add(Calendar.HOUR_OF_DAY, -1);
                    tempDate = calendar.getTime();
                    i++;
                }
            }
            Collections.reverse(dataList);
            Collections.reverse(countList);
            resultMap.put("date_array", dataList);
            resultMap.put("count_array", countList);
            baseOutput.getData().add(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("统计公众号用户数量方法出现异常：----------------->" + e.getMessage());
        }
        return baseOutput;
    }

    /**
     * @Title: getCount @Description: 查询数量 @param: @param searchDate @param: @param
     *         searchFlag @param: @return @return: Integer @throws
     */
    private Integer getCount(Date searchDate, Integer searchFlag) {
        Integer count = 0;
        try {
            String sqlField = searchFlag == 0 ? "searchHours" : "searchDate";
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put(sqlField, searchDate);
            count = dataPartyDao.getPubUserCount(paramMap);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询data_party表统计公众号用户出现异常:------------->" + e.getMessage());
        }
        return count;

    }

}
