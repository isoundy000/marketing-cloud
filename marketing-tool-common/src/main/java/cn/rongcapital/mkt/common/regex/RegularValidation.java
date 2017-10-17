package cn.rongcapital.mkt.common.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class RegularValidation {

    
    public static final String MIX="^[0-9a-zA-Z _-]+$";
    
    public static final String NUM_VALIDATE ="^[0-9]+$";
    
    public static final String ALPHANUMERIC_VALIDATE ="^[0-9a-zA-Z]+$";
    
    public static final String TITLE ="^[a-zA-Z0-9\u4E00-\u9FA5_-]+$";
    
    private static boolean getFlag(String template, String str){
        Matcher matcher  = Pattern.compile(template).matcher(str);
        return matcher.find();
    }
    
    /**
     * 汉字数字字母-_
     * @param str
     * @return
     */
    public static boolean titleValidation(String str) {
        boolean flag = false;
        if (StringUtils.isNotEmpty(str)) {
            flag = getFlag(TITLE, str);
        }
        return flag;
    }
    
    /**
     * 字母校验
     * @param str
     * @return
     */
    public static boolean numValidation(String str) {
        boolean flag = false;
        if (StringUtils.isNotEmpty(str)) {
            flag = getFlag(NUM_VALIDATE, str);
        }
        return flag;
    }
    
    /**
     * 数字字母校验
     * @param str
     * @return
     */
    public static boolean alphanumericValidation(String str) {
        boolean flag = false;
        if (StringUtils.isNotEmpty(str)) {
            flag = getFlag(ALPHANUMERIC_VALIDATE, str);
        }
        return flag;
    }
}
