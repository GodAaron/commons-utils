package com.wf2311.commons.lang.date;

import java.util.Calendar;
import java.util.Locale;

/**
 * 常用日期格式常量
 *
 * @author wf2311
 * @date 2016/03/21 12:46.
 */
public interface Consts {
    /**
     * 默认日期格式(24小时制):yyyy-MM-dd HH:mm:ss
     */
    String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 12小时制日期格式:yyyy-MM-dd hh:mm:ss
     */
    String DATE_FORMAT_T12 = "yyyy-MM-dd hh:mm:ss";
    /**
     * 年月日格式:yyyy-MM-dd
     */
    String DATE_FORMAT = "yyyy-MM-dd";
    /**
     * 24小时制时间格式:HH:mm:ss
     */
    String TIME_FORMAT = "HH:mm:ss";
    /**
     * 12小时制时间格式:hh:mm:ss
     */
    String TIME_FORMAT_T12 = "hh:mm:ss";
    /**
     * 默认时区中国大陆:Locale.SIMPLIFIED_CHINESE
     */
    Locale DEFAULT_LOCALE = Locale.SIMPLIFIED_CHINESE;

    /**
     * 精确到小时的时间格式:yyyy-MM-dd HH
     */
    String DATE_FORMAT_YEAR_TO_HOUR = "yyyy-MM-dd HH:mm";

    /**
     * 精确到分钟的时间格式:yyyy-MM-dd HH:mm
     */
    String DATE_FORMAT_YEAR_TO_MIN = "yyyy-MM-dd HH:mm";

    /**
     * 设置一周的第一天为周一
     */
    int FIRST_DAY_OF_WEEK = Calendar.MONDAY;

    /**
     * 一毫秒
     */
    long ONE_MILLIS = 1L;

    /**
     * 一秒的毫秒数
     */
    long ONE_SECOND = 1000L;
    /**
     * 一分钟的毫秒数
     */
    long ONE_MINUTE = 60 * ONE_SECOND;
    /**
     * 一小时的毫秒数
     */
    long ONE_HOUR = 60 * ONE_MINUTE;
    /**
     * 一天的毫秒数
     */
    long ONE_DAY = 24 * ONE_HOUR;
    /**
     * 一星期的毫秒数
     */
    long ONE_WEEK = 7 * ONE_DAY;

    /**
     * 一个月(30天)的毫秒数
     */
    long ONE_MONTH = 30 * ONE_DAY;

    /**
     * 一年(365天)的毫秒数
     */
    long ONE_YEAR = 365 * ONE_DAY;

    /**
     * 刚刚
     */
    String JUST_NOW = "刚刚";
    /**
     * 几秒前
     */
    String ONE_SECOND_AGO = "秒前";

    /**
     * 几分钟前
     */
    String ONE_MINUTE_AGO = "分钟前";

    /**
     * 半小时前
     */
    String PART_HOUR_AGO = "半小时前";

    /**
     * 几小时前
     */
    String ONE_HOUR_AGO = "小时前";

    /**
     * 半天前
     */
    String PART_DAY_AGO = "半天前";

    /**
     * 几天前
     */
    String ONE_DAY_AGO = "天前";

    /**
     * 昨天
     */
    String YESTERDAY = "昨天";

    /**
     * 几周前
     */
    String ONE_WEEK_AGO = "周前";

    /**
     * 几月前
     */
    String ONE_MONTH_AGO = "月前";

    /**
     * 几年前
     */
    String ONE_YEAR_AGO = "年前";
    /**
     * 未知
     */
    String ONE_UNKNOWN = "未知";
}
