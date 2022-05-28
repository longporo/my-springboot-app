package com.longporo.dev.common.utils;

import com.google.common.collect.Maps;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ConcurrentMap;

/**
 * Date Util<br>
 *
 * @param
 * @author Zihao Long
 * @return
 */
public class DateUtil {
    private static final ConcurrentMap<String, DateTimeFormatter> FORMATTER_CACHE = Maps.newConcurrentMap();

    private static final int PATTERN_CACHE_SIZE = 500;

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDD = "yyyyMMdd";

    /**
     * yyyy-MM-dd HH:mm:ss
     **/
    public static String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";

    public static final String yyyyMMddTHH_mm_ssSSSZ = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";


    /**
     * Date to String time<br>
     *
     * @param [date, pattern]
     * @return java.lang.String
     * @author Zihao Long
     */
    public static String format(Date date, String pattern) {
        return format(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()), pattern);
    }

    /**
     * LocalDateTime to string time<br>
     *
     * @param [localDateTime, pattern]
     * @return java.lang.String
     * @author Zihao Long
     */
    public static String format(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter formatter = createCacheFormatter(pattern);
        return localDateTime.format(formatter);
    }

    /**
     * String time to date<br>
     *
     * @param [time, pattern]
     * @return java.util.Date
     * @author Zihao Long
     */
    public static Date parseDate(String time, String pattern) {
        return Date.from(parseLocalDateTime(time, pattern).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Get current date<br>
     *
     * @param []
     * @return java.util.Date
     * @author Zihao Long
     */
    public static Date getCurrDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    /**
     * String time to LocalDateTime<br>
     *
     * @param [time, pattern]
     * @return java.time.LocalDateTime
     * @author Zihao Long
     */
    public static LocalDateTime parseLocalDateTime(String time, String pattern) {
        DateTimeFormatter formatter = createCacheFormatter(pattern);
        return LocalDateTime.parse(time, formatter);
    }


    /**
     * Get DateTimeFormatter in FORMATTER_CACHE<br>
     *
     * @param [pattern]
     * @return java.time.format.DateTimeFormatter
     * @author Zihao Long
     */
    private static DateTimeFormatter createCacheFormatter(String pattern) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Invalid pattern specification");
        }
        DateTimeFormatter formatter = FORMATTER_CACHE.get(pattern);
        if (formatter == null) {
            if (FORMATTER_CACHE.size() < PATTERN_CACHE_SIZE) {
                formatter = DateTimeFormatter.ofPattern(pattern);
                DateTimeFormatter oldFormatter = FORMATTER_CACHE.putIfAbsent(pattern, formatter);
                if (oldFormatter != null) {
                    formatter = oldFormatter;
                }
            }
        }

        return formatter;
    }

    public static String getCurrDateTimeString(String paramString) {
        if ((paramString == null) || ("".equals(paramString))) {
            return format(getCurrDate(), "yyyyMMddHHmmssSSS");
        }
        return format(getCurrDate(), paramString);
    }

    public static Date StringToDate(String paramString1, String paramString2) {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(paramString2);
        Date localDate = null;
        try {
            localDate = localSimpleDateFormat.parse(paramString1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return localDate;
    }
}
