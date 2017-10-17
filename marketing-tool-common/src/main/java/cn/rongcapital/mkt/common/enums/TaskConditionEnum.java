package cn.rongcapital.mkt.common.enums;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum TaskConditionEnum {

    ALL(0, "A", "任意时间"), H(1, "H", "最近1小时"), D(2, "D", "最近一天"), W(3, "W", "最近一周"),

    ;

    private static Logger logger = LoggerFactory.getLogger(TaskConditionEnum.class);

    private TaskConditionEnum(int code, String abbreviation, String description) {
        this.code = code;
        this.abbreviation = abbreviation;
        this.description = description;
    }

    private int code;

    private String abbreviation;

    private String description;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTime() {
        Calendar calendar = Calendar.getInstance();
        if (this.getCode() == 0) {
//            calendar.setTimeInMillis(0);
            return null;
        }
        if (this.getCode() == 1) {
            calendar.add(Calendar.HOUR, -1);
        } else if (this.getCode() == 2) {
            calendar.add(Calendar.DATE, -1);
            return calendar.getTime();
        } else if (this.getCode() == 3) {
            calendar.add(Calendar.DATE, -7);
            return calendar.getTime();
        }

        return calendar.getTime();
    }

    public static TaskConditionEnum getEnumByAbbreviation(String abbreviation) {
        TaskConditionEnum[] enums = TaskConditionEnum.values();
        if (StringUtils.isEmpty(abbreviation)) {
            logger.error("通过缩写获取TaskConditionEnum失败 : 参数为空");
            return null;
        }

        for (TaskConditionEnum taskConditionEnum : enums) {
            if (taskConditionEnum.getAbbreviation().equalsIgnoreCase(abbreviation)) {
                return taskConditionEnum;
            }
        }

        logger.info("缩写参数{}错误,不含该缩写的枚举", abbreviation);
        return null;
    }

    public static TaskConditionEnum getEnumByCode(Integer code) {
        TaskConditionEnum[] enums = TaskConditionEnum.values();
        if (code == null) {
            logger.error("通过编码获取TaskConditionEnum失败 : 参数为空");
            return null;
        }

        for (TaskConditionEnum taskConditionEnum : enums) {
            if (taskConditionEnum.getCode() == code) {
                return taskConditionEnum;
            }
        }

        logger.info("缩写参数{}错误,不含该编码的枚举", code);
        return null;
    }
}
