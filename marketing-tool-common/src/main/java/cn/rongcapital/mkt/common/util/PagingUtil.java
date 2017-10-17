package cn.rongcapital.mkt.common.util;

public class PagingUtil {

    // 整理下分页的参数
    public static void fixPagingParam(Integer index, Integer size) {
        if (index == null || index < 1) {
            index = 1;
        }

        if (size == null || size < 0) {
            size = 10;
        }

        if (size > 100) {
            size = 100;
        }

        index = (index - 1) * size;
    }

}
