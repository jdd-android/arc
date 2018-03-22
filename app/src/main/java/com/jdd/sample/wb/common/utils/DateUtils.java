package com.jdd.sample.wb.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author lc. 2018-03-22 15:09
 * @since 1.0.0
 */

public class DateUtils {

    private static final SimpleDateFormat POST_DATE_FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);

    public static String readableDate(String dateOrigin) {
        try {
            Date date = POST_DATE_FORMAT.parse(dateOrigin);
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateOrigin;
        }
    }

}
