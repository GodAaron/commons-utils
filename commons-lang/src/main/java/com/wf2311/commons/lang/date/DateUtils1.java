package com.wf2311.commons.lang.date;

import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间转换工具类
 */
@Deprecated
public class DateUtils1 implements Consts {
    private static final Logger logger = Logger.getLogger(DateUtils1.class);

    /**
     * 设置日期格式
     *
     * @param format
     * @return
     */
    public static SimpleDateFormat getDateFormat(String format) {
        return getDateFormat(format, DEFAULT_LOCALE);
    }

    /**
     * 设置日期格式
     *
     * @param format
     * @param locale
     * @return
     */
    public static SimpleDateFormat getDateFormat(String format, Locale locale) {
        if (locale == null) {
            locale = DEFAULT_LOCALE;
        }
        return new SimpleDateFormat(format, locale);
    }

    public static String long2String(long l){
        return date2String(new Date(l));
    }

    public static String long2String(long l,String format){
        return date2String(new Date(l),format);
    }

    /**
     * 获取当前时间并转为转为字符串
     *
     * @return
     */
    public static String nowDate2String() {
        return date2String(Calendar.getInstance().getTime());
    }

    /**
     * 日期转为字符串
     *
     * @param date
     * @return
     */
    public static String date2String(Date date) {
        return date2String(date, DEFAULT_DATE_FORMAT, DEFAULT_LOCALE);
    }

    /**
     * 日期转为字符串
     *
     * @param date
     * @param format
     * @return
     */
    public static String date2String(Date date,String format) {
        return date2String( date, format,DEFAULT_LOCALE);
    }

    /**
     * 日期转为字符串
     *
     * @param date
     * @param format
     * @param locale
     * @return
     */
    public static String date2String(Date date,String format,  Locale locale) {
        if (date != null) {
            return getDateFormat(format, locale).format(date);
        }
        return null;
    }

    /**
     * 字符串转日期
     *
     * @param format
     * @param dateString
     * @return
     * @throws ParseException
     */
    public static Date string2Date(String dateString,String format) throws ParseException {
        return string2Date(dateString, format, DEFAULT_LOCALE);
    }

    /**
     * 字符串转日期
     *
     * @param format
     * @param dateString
     * @param locale
     * @return
     * @throws ParseException
     */
    public static Date string2Date(String dateString, String format, Locale locale) {
        try {
            return getDateFormat(format, locale).parse(dateString);
        } catch (ParseException e) {
            logger.error("转换错误：" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private DateUtils1() {
    }

    public static String currentDate(String pattern) {
        return formatDate(new Date(),pattern );
    }

    public static String formatDate( Date date,String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.CHINA);
        if (date != null) {
            return format.format(date);
        }
        return null;
    }

    /**
     * 指定locale格式化日期
     *
     * @param pattern
     * @param date
     * @param locale
     * @return
     */
    public static String formatDate( Date date, String pattern,Locale locale) {
        SimpleDateFormat format = new SimpleDateFormat(pattern, locale);
        if (date != null) {
            return format.format(date);
        }
        return null;
    }

    /**
     * 解析日期，注:此处为严格模式解析，即20151809这样的日期会解析错误
     *
     * @param pattern
     * @param date
     * @return
     */
    public static Date parse(String pattern, String date) {
        return parse(pattern, date, Locale.CHINA);
    }

    /**
     * 解析日期，注:此处为严格模式解析，即20151809这样的日期会解析错误
     *
     * @param pattern
     * @param date
     * @param locale
     * @return
     */
    public static Date parse(String pattern, String date, Locale locale) {
        SimpleDateFormat format = new SimpleDateFormat(pattern, locale);
        format.setLenient(false);
        Date result = null;
        try {
            result = format.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String getSpeed(long start, long end) {
        long l = end - start;
        String s;
        s = l % 1000 + "ms ";
        l = l / 1000;
        if (l > 0) {
            s = l % 60 + "s " + s;
            l = l / 60;
            if (l > 0) {
                s = l % 60 + "min " + s;
                l = l / 60;
                if (l > 0) {
                    s = l % 60 + "h " + s;
                    l = l / 60;
                    if (l > 0) {
                        s = l % 24 + "d " + s;
                    }
                }
            }
        }
        return s;
    }

    public static void main(String[] args) throws ParseException {
        String text = "2016-01-01";
        System.out.println(string2Date("2016-01-01", DATE_FORMAT));
//        System.out.println(new Date(""));
//        System.out.println();
        SimpleDateFormat formatter =
                new SimpleDateFormat("yyyy-MM-dd");
        String s = "2011-07-09";
        Date date = formatter.parse(text);
        System.out.println(date);

    }

}
