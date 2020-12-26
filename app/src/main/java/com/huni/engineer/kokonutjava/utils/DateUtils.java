package com.huni.engineer.kokonutjava.utils;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static final String DATE_FORMAT        = "yyyyMMddHHmmss";
    public static final String DATE_FORMAT_FOR_IM = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final String DATE_TIME_ONLY     = "HH:mm";

    @SuppressLint("SimpleDateFormat")
    public static String toFormatString(long time, String format) {
        DateFormat df  = new SimpleDateFormat(format);
        Date now = new Date(time);

        return df.format(now);
    }

    public static String toFormatString() {
        return toFormatString(System.currentTimeMillis(), DATE_FORMAT);
    }

    public static String toTimeString() {
        return toFormatString(System.currentTimeMillis(), DATE_TIME_ONLY);
    }
}
