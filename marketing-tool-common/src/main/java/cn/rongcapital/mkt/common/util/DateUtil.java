package cn.rongcapital.mkt.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Yunfeng on 2016-6-1.
 */
public class DateUtil {
	
	/**
     * 将字符串转化为时间
     * 
     * @param dateString
     * @param format
     * @return
     */
    public static Date getDateFromString(String dateString, String format) {
        if (StringUtils.isBlank(dateString)) {
            return null;
        }
        Date result = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            result = sdf.parse(dateString);
        } catch (Exception e) {
            return null;
        }
        return result;
    }
    
    /**
     * 将时间转化为字符串
     * 
     * @param date,format
     * @param format
     * @return
     */
    public static String getStringFromDate(Date date, String format) {
        if (null == date) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String result = sdf.format(date);
        return result;
    }

    public static Date parseTimeInUploadFile(String timeInUploadFile) {
        if(timeInUploadFile.length() == "yyyy-MM-dd".length()){
            return DateUtil.getDateFromString(timeInUploadFile,"yyyy-MM-dd");
        }else if(timeInUploadFile.length() == "yyyy-MM-ddHH:mm".length()){
            return DateUtil.getDateFromString(timeInUploadFile,"yyyy-MM-ddHH:mm");
        }else if(timeInUploadFile.length() == "yyyy-MM-ddHH:mm:ss".length()){
            return  DateUtil.getDateFromString(timeInUploadFile,"yyyy-MM-ddHH:mm:ss");
        }

        return null;
    }
    
    /**
     * 日期计算
     * @param date
     * @param days
     * @return
     */
    public static Date addDays(Date date,int days) {

    	 Calendar theCa = Calendar.getInstance();
    	 theCa.setTime(date);
    	 theCa.add(Calendar.DATE,days);
    	 return theCa.getTime();
    }
}
