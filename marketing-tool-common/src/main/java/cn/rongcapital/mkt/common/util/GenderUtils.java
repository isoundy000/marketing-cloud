package cn.rongcapital.mkt.common.util;

/**
 * Created by Yunfeng on 2016-6-29.
 */
public class GenderUtils {
    public static String byteToChar(Byte genderFlag){
        if(genderFlag != null){
            switch (genderFlag){
                case 1:
                    return "男";
                case 2:
                    return "女";
                case 3:
                    return "未确定";
                case 4:
                    return "不确定";
            }
        }
        return "不确定";
    }
    
    public static Integer charToInt(String genderFlag){
        if(genderFlag != null){
            switch (genderFlag){
                case "男":
                    return 1;
                case "女":
                    return 2;
                case "未确定":
                    return 3;                
            }
        }
        return 3;
    }
}
