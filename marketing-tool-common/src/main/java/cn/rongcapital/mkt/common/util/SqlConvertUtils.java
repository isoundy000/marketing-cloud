/*************************************************
 * @功能简述: SQL转换工具类
 * @对应项目名称：
 * @see
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2016.12.21
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.common.util;

import org.apache.commons.lang3.StringUtils;

import cn.rongcapital.mkt.common.constant.ApiConstant;

/**
 * 功能描述：SQL参数按照 escape字符进行转换
 * 
 * @param param SQL参数
 * @author zhuxuelong
 * @Data 2016.12.21
 */
public class SqlConvertUtils {
    public static String escapeSQLCharacter(String param) {
        if (StringUtils.isNotBlank(param)) {
            param =
                            param.replace(ApiConstant.SQL_ESCAPE_CHARACTER, ApiConstant.SQL_ESCAPE_CHARACTER
                                            + ApiConstant.SQL_ESCAPE_CHARACTER);
            param = param.replace("_", ApiConstant.SQL_ESCAPE_CHARACTER + "_");
            param = param.replace("%", ApiConstant.SQL_ESCAPE_CHARACTER + "%");
        }
        return param;
    }

}
