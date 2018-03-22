package com.jdd.sample.wb.common.utils;

/**
 * @author lc. 2018-03-19 14:31
 * @since 1.0.0
 */

public class CommonUtils {

    public static long str2long(String str) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
