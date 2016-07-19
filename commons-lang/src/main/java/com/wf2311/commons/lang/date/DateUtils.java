package com.wf2311.commons.lang.date;

import com.wf2311.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期工具类
 * @author wf2311
 * @time 2016/05/13 13:57.
 */
public class DateUtils implements Consts {

    private static final ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<>();

    private static final Object                        object      = new Object();

    /**
     * 平年每个月份的天数
     */
    public static final int[] MONTH_DAYS_OF_YEAR = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    /**
     * 取得SimpleDateFormat
     * @param pattern 日期格式
     * @return SimpleDateFormat对象
     * @throws RuntimeException 异常：非法日期格式
     */
    private static SimpleDateFormat getDateFormat(String pattern) throws RuntimeException {
        SimpleDateFormat dateFormat = threadLocal.get();
        if (dateFormat == null) {
            synchronized (object) {
                dateFormat = new SimpleDateFormat(pattern);
                dateFormat.setLenient(false);
                threadLocal.set(dateFormat);
            }
        }
        dateFormat.applyPattern(pattern);
        return dateFormat;
    }

    /**
     * 取得日期中的某数值。如取得月份
     * @param date 日期
     * @param dateType 日期格式,参见{@link java.util.Calendar }的类型常量
     * @return 数值
     */
    private static int getInteger(Date date, int dateType) {
        int num = 0;
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
            num = calendar.get(dateType);
        }
        return num;
    }

    /**
     * 增加日期中某类型的某数值。如增加日期
     * @param date 日期字符串
     * @param dateType 类型,参见{@link java.util.Calendar }的类型常量
     * @param amount 数值
     * @return 修改后的日期字符串
     */
    private static String addInteger(String date, int dateType, int amount) {
        String dateString = null;
        DateStyle dateStyle = getDateStyle(date);
        if (dateStyle != null) {
            Date myDate = parseToDate(date, dateStyle);
            myDate = addInteger(myDate, dateType, amount);
            dateString = parseToString(myDate, dateStyle);
        }
        return dateString;
    }

    /**
     * 增加日期中某类型的某数值。如增加日期
     * @param date 日期
     * @param dateType 类型,参见{@link java.util.Calendar }的类型常量
     * @param amount 数值
     * @return 修改后的日期
     */
    private static Date addInteger(Date date, int dateType, int amount) {
        Date myDate = null;
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(dateType, amount);
            myDate = calendar.getTime();
        }
        return myDate;
    }

    /**
     * 修改日期中某类型的某数值。如修改小时数
     *
     * @param date     日期字符串
     * @param dateType 类型,参见{@link java.util.Calendar }的类型常量
     * @param amount   数值
     * @return 修改后日期字符串
     */
    private static String setInteger(String date, int dateType, int amount) {
        String dateString = null;
        DateStyle dateStyle = getDateStyle(date);
        if (dateStyle != null) {
            Date myDate = parseToDate(date, dateStyle);
            myDate = setInteger(myDate, dateType, amount);
            dateString = parseToString(myDate, dateStyle);
        }
        return dateString;
    }

    /**
     * 修改日期中某类型的某数值。如修改小时数
     *
     * @param date     日期
     * @param dateType 类型,参见{@link java.util.Calendar }的类型常量
     * @param amount   数值
     * @return 修改后日期
     */
    private static Date setInteger(Date date, int dateType, int amount) {
        Date myDate = null;
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(dateType, amount);
            myDate = calendar.getTime();
        }
        return myDate;
    }

    /**
     * 取得精确的日期
     * @param timestamps 时间long集合
     * @return 日期
     */
    private static Date getAccurateDate(List<Long> timestamps) {
        Date date = null;
        long timestamp = 0;
        Map<Long, long[]> map = new HashMap<>();
        List<Long> absoluteValues = new ArrayList<Long>();

        if (timestamps != null && timestamps.size() > 0) {
            if (timestamps.size() > 1) {
                for (int i = 0; i < timestamps.size(); i++) {
                    for (int j = i + 1; j < timestamps.size(); j++) {
                        long absoluteValue = Math.abs(timestamps.get(i) - timestamps.get(j));
                        absoluteValues.add(absoluteValue);
                        long[] timestampTmp = { timestamps.get(i), timestamps.get(j) };
                        map.put(absoluteValue, timestampTmp);
                    }
                }

                // 有可能有相等的情况。如2012-11和2012-11-01。时间戳是相等的。此时minAbsoluteValue为0
                // 因此不能将minAbsoluteValue取默认值0
                long minAbsoluteValue = -1;
                if (!absoluteValues.isEmpty()) {
                    minAbsoluteValue = absoluteValues.get(0);
                    for (int i = 1; i < absoluteValues.size(); i++) {
                        if (minAbsoluteValue > absoluteValues.get(i)) {
                            minAbsoluteValue = absoluteValues.get(i);
                        }
                    }
                }

                if (minAbsoluteValue != -1) {
                    long[] timestampsLastTmp = map.get(minAbsoluteValue);

                    long dateOne = timestampsLastTmp[0];
                    long dateTwo = timestampsLastTmp[1];
                    if (absoluteValues.size() > 1) {
                        timestamp = Math.abs(dateOne) > Math.abs(dateTwo) ? dateOne : dateTwo;
                    }
                }
            } else {
                timestamp = timestamps.get(0);
            }
        }

        if (timestamp != 0) {
            date = new Date(timestamp);
        }
        return date;
    }

    /**
     * 判断字符串是否为日期字符串
     * @param date 日期字符串
     * @return true or false
     */
    public static boolean isDate(String date) {
        boolean isDate = false;
        if (date != null) {
            if (getDateStyle(date) != null) {
                isDate = true;
            }
        }
        return isDate;
    }

    /**
     * 判断字符串是否是时间戳
     * @param date
     * @return
     */
    public static boolean isMillis(String date) {
        if (StringUtils.isLong(date)) {
            return true;
        }
        return false;
    }

    /**
     * 取得日期字符串的日期风格。失败返回null。
     * @param date 日期字符串
     * @return 日期风格
     */
    public static DateStyle getDateStyle(String date) {
        DateStyle dateStyle = null;
        Map<Long, DateStyle> map = new HashMap<>();
        List<Long> timestamps = new ArrayList<>();
        for (DateStyle style : DateStyle.values()) {
            if (style.isShowOnly()) {
                continue;
            }
            Date dateTmp = null;
            if (date != null) {
                try {
                    ParsePosition pos = new ParsePosition(0);
                    dateTmp = getDateFormat(style.getValue()).parse(date, pos);
                    if (pos.getIndex() != date.length()) {
                        dateTmp = null;
                    }
                } catch (Exception e) {
                }
            }
            if (dateTmp != null) {
                timestamps.add(dateTmp.getTime());
                map.put(dateTmp.getTime(), style);
            }
        }
        Date accurateDate = getAccurateDate(timestamps);
        if (accurateDate != null) {
            dateStyle = map.get(accurateDate.getTime());
        }
        return dateStyle;
    }

    /**
     * 将日期转为日历
     *
     * @param date 日期
     * @return Calendar类型时间
     */
    public static Calendar parseToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 将日期字符串转为日历。失败返回null。
     *
     * @param date 日期字符串
     * @return Calendar类型时间
     */
    public static Calendar parseToCalendar(String date) {
        Date d = parseToDate(date);
        if (d != null) {
            return parseToCalendar(d);
        }
        return null;
    }

    /**
     * 将日期字符串转化为日期。失败返回null。
     * @param date 日期字符串
     * @return 日期
     */
    public static Date parseToDate(String date) {
        DateStyle dateStyle = getDateStyle(date);
        if (dateStyle == null) {
            if (isMillis(date)) {
                return new Date(Long.valueOf(date));
            }
        }
        return parseToDate(date, dateStyle);
    }

    /**
     * 将日期字符串转化为日期。失败返回null。
     * @param date 日期字符串
     * @param pattern 日期格式
     * @return 日期
     */
    public static Date parseToDate(String date, String pattern) {
        Date myDate = null;
        if (date != null) {
            try {
                myDate = getDateFormat(pattern).parse(date);
            } catch (Exception e) {
                if (isMillis(date)) {
                    return new Date(Long.valueOf(date));
                }
            }
        }
        return myDate;
    }

    /**
     * 将日期字符串转化为日期。失败返回null。
     * @param date 日期字符串
     * @param dateStyle 日期风格
     * @return 日期
     */
    public static Date parseToDate(String date, DateStyle dateStyle) {
        Date myDate = null;
        if (dateStyle != null) {
            myDate = parseToDate(date, dateStyle.getValue());
        }
        return myDate;
    }


    /**
     * 日期转为时间戳。失败返回null。
     *
     * @param date 日期
     * @return 时间戳
     */
    public static Long parseToLong(Date date) {
        if (date == null) {
            return null;
        }
        return date.getTime();
    }

    /**
     * 时间转为时间戳。失败返回null。
     *
     * @param date 日期字符串
     * @return 时间戳
     */
    public static Long parseToLong(String date) {
        return parseToLong(parseToDate(date));
    }

    /**
     * 当前时间转为时间戳
     *
     * @return 时间戳
     */
    public static Long nowToLong() {
        return parseToLong(Calendar.getInstance().getTime());
    }

    /**
     * 将日期转化为日期字符串。失败返回null。
     * @param date 日期
     * @param pattern 日期格式
     * @return 日期字符串
     */
    public static String parseToString(Date date, String pattern) {
        String dateString = null;
        if (date != null) {
            try {
                dateString = getDateFormat(pattern).format(date);
            } catch (Exception e) {
            }
        }
        return dateString;
    }

    /**
     * 将日期转化为日期字符串（默认格式{@link Consts#DEFAULT_DATE_FORMAT}）。失败返回null。
     *
     * @param date 日期
     * @return 日期字符串
     */
    public static String parseToString(Date date) {
        return parseToString(date, Consts.DEFAULT_DATE_FORMAT);
    }

    /**
     * 将时间转化为日期字符串。失败返回null。
     *
     * @param pattern 日期格式
     * @return 日期字符串
     */
    public static String nowToString(String pattern) {
        return parseToString(Calendar.getInstance().getTime(), pattern);
    }

    /**
     * 将时间转化为日期字符串。失败返回null。
     *
     * @param dateStyle 日期风格
     * @return 日期字符串
     */
    public static String nowToString(DateStyle dateStyle) {
        return parseToString(Calendar.getInstance().getTime(), dateStyle);
    }

    /**
     * 将当前时间转化为日期字符串（默认格式{@link Consts#DEFAULT_DATE_FORMAT}）。失败返回null。
     *
     * @return 日期字符串
     */
    public static String nowToString() {
        return parseToString(Calendar.getInstance().getTime(), Consts.DEFAULT_DATE_FORMAT);
    }

    /**
     * 将日期转化为日期字符串。失败返回null。
     * @param date 日期
     * @param dateStyle 日期风格
     * @return 日期字符串
     */
    public static String parseToString(Date date, DateStyle dateStyle) {
        String dateString = null;
        if (dateStyle != null) {
            dateString = parseToString(date, dateStyle.getValue());
        }
        return dateString;
    }

    /**
     * 将日期字符串转化为默认的日期字符串默认格式{@link Consts#DEFAULT_DATE_FORMAT}）。失败返回null。
     *
     * @param date 旧日期字符串
     * @return 默认格式的日期字符串
     */
    public static String parseToDefaultString(String date) {
        return parseToString(parseToDate(date));
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     * @param date 旧日期字符串
     * @param newPattern 新日期格式
     * @return 新日期字符串
     */
    public static String parseToString(String date, String newPattern) {
        DateStyle oldDateStyle = getDateStyle(date);
        return parseToString(date, oldDateStyle, newPattern);
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     * @param date 旧日期字符串
     * @param newDateStyle 新日期风格
     * @return 新日期字符串
     */
    public static String parseToString(String date, DateStyle newDateStyle) {
        DateStyle oldDateStyle = getDateStyle(date);
        return parseToString(date, oldDateStyle, newDateStyle);
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     * @param date 旧日期字符串
     * @param oldPattern 旧日期格式
     * @param newPattern 新日期格式
     * @return 新日期字符串
     */
    public static String parseToString(String date, String oldPattern, String newPattern) {
        return parseToString(parseToDate(date, oldPattern), newPattern);
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     * @param date 旧日期字符串
     * @param oldDteStyle 旧日期风格
     * @param newPattern 新日期格式
     * @return 新日期字符串
     */
    public static String parseToString(String date, DateStyle oldDteStyle, String newPattern) {
        String dateString = null;
        if (oldDteStyle != null) {
            dateString = parseToString(date, oldDteStyle.getValue(), newPattern);
        }
        return dateString;
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     * @param date 旧日期字符串
     * @param oldPattern 旧日期格式
     * @param newDateStyle 新日期风格
     * @return 新日期字符串
     */
    public static String parseToString(String date, String oldPattern, DateStyle newDateStyle) {
        String dateString = null;
        if (newDateStyle != null) {
            dateString = parseToString(date, oldPattern, newDateStyle.getValue());
        }
        return dateString;
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     * @param date 旧日期字符串
     * @param oldDteStyle 旧日期风格
     * @param newDateStyle 新日期风格
     * @return 新日期字符串
     */
    public static String parseToString(String date, DateStyle oldDteStyle, DateStyle newDateStyle) {
        String dateString = null;
        if (oldDteStyle != null && newDateStyle != null) {
            dateString = parseToString(date, oldDteStyle.getValue(), newDateStyle.getValue());
        }
        return dateString;
    }

    /**
     * Timestamp转为Date
     *
     * @param timestamp
     * @return
     */
    public static Date parseToDate(Timestamp timestamp) {
        return new Date(timestamp.getTime());
    }

    /**
     * Date转Timestamp
     *
     * @param date
     * @return
     */
    public static Timestamp parseToTimestamp(Date date) {
        return new Timestamp(date.getTime());
    }

    /**
     * 增加日期的年份。失败返回null。
     * @param date 日期
     * @param yearAmount 增加数量。可为负数
     * @return 增加年份后的日期字符串
     */
    public static String addYear(String date, int yearAmount) {
        return addInteger(date, Calendar.YEAR, yearAmount);
    }

    /**
     * 增加日期的年份。失败返回null。
     * @param date 日期
     * @param yearAmount 增加数量。可为负数
     * @return 增加年份后的日期
     */
    public static Date addYear(Date date, int yearAmount) {
        return addInteger(date, Calendar.YEAR, yearAmount);
    }

    /**
     * 增加日期的月份。失败返回null。
     * @param date 日期
     * @param monthAmount 增加数量。可为负数
     * @return 增加月份后的日期字符串
     */
    public static String addMonth(String date, int monthAmount) {
        return addInteger(date, Calendar.MONTH, monthAmount);
    }

    /**
     * 增加日期的月份。失败返回null。
     * @param date 日期
     * @param monthAmount 增加数量。可为负数
     * @return 增加月份后的日期
     */
    public static Date addMonth(Date date, int monthAmount) {
        return addInteger(date, Calendar.MONTH, monthAmount);
    }

    /**
     * 增加日期的天数。失败返回null。
     * @param date 日期字符串
     * @param dayAmount 增加数量。可为负数
     * @return 增加天数后的日期字符串
     */
    public static String addDay(String date, int dayAmount) {
        return addInteger(date, Calendar.DATE, dayAmount);
    }

    /**
     * 增加日期的天数。失败返回null。
     * @param date 日期
     * @param dayAmount 增加数量。可为负数
     * @return 增加天数后的日期
     */
    public static Date addDay(Date date, int dayAmount) {
        return addInteger(date, Calendar.DATE, dayAmount);
    }

    /**
     * 增加日期的小时。失败返回null。
     * @param date 日期字符串
     * @param hourAmount 增加数量。可为负数
     * @return 增加小时后的日期字符串
     */
    public static String addHour(String date, int hourAmount) {
        return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);
    }

    /**
     * 增加日期的小时。失败返回null。
     * @param date 日期
     * @param hourAmount 增加数量。可为负数
     * @return 增加小时后的日期
     */
    public static Date addHour(Date date, int hourAmount) {
        return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);
    }

    /**
     * 增加日期的分钟。失败返回null。
     * @param date 日期字符串
     * @param minuteAmount 增加数量。可为负数
     * @return 增加分钟后的日期字符串
     */
    public static String addMinute(String date, int minuteAmount) {
        return addInteger(date, Calendar.MINUTE, minuteAmount);
    }

    /**
     * 增加日期的分钟。失败返回null。
     * @param date 日期
     * @param minuteAmount 增加数量。可为负数
     * @return 增加分钟后的日期
     */
    public static Date addMinute(Date date, int minuteAmount) {
        return addInteger(date, Calendar.MINUTE, minuteAmount);
    }

    /**
     * 增加日期的秒钟。失败返回null。
     * @param date 日期字符串
     * @param secondAmount 增加数量。可为负数
     * @return 增加秒钟后的日期字符串
     */
    public static String addSecond(String date, int secondAmount) {
        return addInteger(date, Calendar.SECOND, secondAmount);
    }

    /**
     * 增加日期的秒钟。失败返回null。
     * @param date 日期
     * @param secondAmount 增加数量。可为负数
     * @return 增加秒钟后的日期
     */
    public static Date addSecond(Date date, int secondAmount) {
        return addInteger(date, Calendar.SECOND, secondAmount);
    }

    /**
     * 取得日期的年份。失败返回0。
     * @param date 日期字符串
     * @return 年份
     */
    public static int getYear(String date) {
        return getYear(parseToDate(date));
    }

    /**
     * 取得日期的年份。失败返回0。
     * @param date 日期
     * @return 年份
     */
    public static int getYear(Date date) {
        return getInteger(date, Calendar.YEAR);
    }

    /**
     * 取得日期所在年份的总天数
     *
     * @param date 日期字符串
     * @return
     */
    public static int getDaysOfYear(String date) {
        return getDaysOfYear(parseToDate(date));
    }

    /**
     * 取得日期所在年份的总天数
     *
     * @param date 日期
     * @return
     */
    public static int getDaysOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
    }

    /**
     * 取得日期所在年份的第一天
     *
     * @param date 日期字符串
     * @return
     */
    public static String getFirstDayOfYear(String date) {
        DateStyle dateStyle = getDateStyle(date);
        return parseToString(getFirstDayOfYear(parseToDate(date, dateStyle)), dateStyle);
    }

    /**
     * 取得日期所在年份的第一天
     *
     * @param date 日期
     * @return
     */
    public static Date getFirstDayOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    /**
     * 取得日期所在年份的最后一天
     *
     * @param date 日期字符串
     * @return
     */
    public static String getLastDayOfYear(String date) {
        DateStyle dateStyle = getDateStyle(date);
        return parseToString(getLastDayOfYear(parseToDate(date, dateStyle)), dateStyle);
    }

    /**
     * 取得日期所在年份的最后一天
     *
     * @param date 日期
     * @return
     */
    public static Date getLastDayOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    /**
     * 取得日期所在年份已过的天数
     *
     * @param date 日期字符串
     * @return
     */
    public static int getPassDayOfYear(String date) {
        return getPassDayOfYear(parseToDate(date));
    }

    /**
     * 取得日期所在年份已过的天数
     *
     * @param date 日期
     * @return
     */
    public static int getPassDayOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 取得日期所在年份剩余的天数
     *
     * @param date 日期字符串
     * @return
     */
    public static int getRemainDayOfYear(String date) {
        return getRemainDayOfYear(parseToDate(date));
    }

    /**
     * 取得日期所在年份剩余的天数
     *
     * @param date 日期
     * @return
     */
    public static int getRemainDayOfYear(Date date) {
        return getPassDayOfYear(date) - getDaysOfYear(date);
    }

    /**
     * 取得日期所在季度的第一天
     *
     * @param date 日期字符串
     * @return
     */
    public static String getFirstDateOfSeason(String date) {
        DateStyle dateStyle = getDateStyle(date);
        return parseToString(getFirstDateOfSeason(parseToDate(date, dateStyle)), dateStyle);
    }

    /**
     * 取得日期所在季度的第一天
     *
     * @param date 日期
     * @return
     */
    public static Date getFirstDateOfSeason(Date date) {
        return getFirstDateOfMonth(getSeasonDate(date)[0]);
    }

    /**
     * 取得日期所在季度的最后一天
     *
     * @param date 日期字符串
     * @return
     */
    public static String getLastDateOfSeason(String date) {
        DateStyle dateStyle = getDateStyle(date);
        return parseToString(getLastDateOfSeason(parseToDate(date, dateStyle)), dateStyle);
    }

    /**
     * 取得日期所在季度的最后一天
     *
     * @param date 日期
     * @return
     */
    public static Date getLastDateOfSeason(Date date) {
        return getLastDateOfMonth(getSeasonDate(date)[2]);
    }

    /**
     * 取得日期所在季度的天数
     *
     * @param date 日期字符串
     * @return
     */
    public static int getDaysOfSeason(String date) {
        return getDaysOfSeason(parseToDate(date));
    }


    /**
     * 取得日期所在季度的天数
     *
     * @param date 日期
     * @return
     */
    public static int getDaysOfSeason(Date date) {
        int day = 0;
        Date[] seasonDates = getSeasonDate(date);
        for (Date date2 : seasonDates) {
            day += getDayOfMonth(date2);
        }
        return day;
    }

    /**
     * 取得日期的所在的季度
     *
     * @param date 日期字符串
     * @return 1:第一季度(1、2、3月);2:第二季度(4、5、6月);3:第三季度(7、8、9月);4:第四季度(10、11、12月);
     */
    public static int getSeason(String date) {
        return getSeason(parseToDate(date));
    }

    /**
     * 取得日期的所在的季度
     *
     * @param date 日期
     * @return 1:第一季度(1、2、3月);2:第二季度(4、5、6月);3:第三季度(7、8、9月);4:第四季度(10、11、12月);
     */
    public static int getSeason(Date date) {

        int season = 0;

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                season = 1;
                break;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                season = 2;
                break;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                season = 3;
                break;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                season = 4;
                break;
            default:
                break;
        }
        return season;
    }


    /**
     * 取得日期所在的三个月份的日期
     *
     * @param date 日期字符串
     * @return
     */
    public static Date[] getSeasonDate(String date) {
        return getSeasonDate(parseToDate(date));
    }

    /**
     * 取得日期所在的三个月份的日期
     *
     * @param date 日期
     * @return
     */
    public static Date[] getSeasonDate(Date date) {
        Date[] season = new Date[3];

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        int nSeason = getSeason(date);
        if (nSeason == 1) {// 第一季度
            c.set(Calendar.MONTH, Calendar.JANUARY);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.FEBRUARY);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.MARCH);
            season[2] = c.getTime();
        } else if (nSeason == 2) {// 第二季度
            c.set(Calendar.MONTH, Calendar.APRIL);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.MAY);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.JUNE);
            season[2] = c.getTime();
        } else if (nSeason == 3) {// 第三季度
            c.set(Calendar.MONTH, Calendar.JULY);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.AUGUST);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.SEPTEMBER);
            season[2] = c.getTime();
        } else if (nSeason == 4) {// 第四季度
            c.set(Calendar.MONTH, Calendar.OCTOBER);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.NOVEMBER);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.DECEMBER);
            season[2] = c.getTime();
        }
        return season;
    }

    /**
     * 取得日期所在的季度已过天数
     *
     * @param date 日期字符串
     * @return
     */
    public static int getPassDayOfSeason(String date) {
        return getPassDayOfSeason(parseToDate(date));
    }

    /**
     * 取得日期所在的季度已过天数
     *
     * @param date 日期
     * @return
     */
    public static int getPassDayOfSeason(Date date) {
        int day = 0;

        Date[] seasonDates = getSeasonDate(date);

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);

        if (month == Calendar.JANUARY || month == Calendar.APRIL
                || month == Calendar.JULY || month == Calendar.OCTOBER) {// 季度第一个月
            day = getPassDayOfMonth(seasonDates[0]);
        } else if (month == Calendar.FEBRUARY || month == Calendar.MAY
                || month == Calendar.AUGUST || month == Calendar.NOVEMBER) {// 季度第二个月
            day = getDayOfMonth(seasonDates[0])
                    + getPassDayOfMonth(seasonDates[1]);
        } else if (month == Calendar.MARCH || month == Calendar.JUNE
                || month == Calendar.SEPTEMBER || month == Calendar.DECEMBER) {// 季度第三个月
            day = getDayOfMonth(seasonDates[0]) + getDayOfMonth(seasonDates[1])
                    + getPassDayOfMonth(seasonDates[2]);
        }
        return day;
    }

    /**
     * 取得日期所在的季度剩余天数
     *
     * @param date 日期字符串
     * @return
     */
    public static int getRemainDayOfSeason(String date) {
        return getRemainDayOfSeason(parseToDate(date));
    }

    /**
     * 取得日期所在的季度剩余天数
     *
     * @param date 日期字符串
     * @return
     */
    public static int getRemainDayOfSeason(Date date) {
        return getDaysOfSeason(date) - getPassDayOfSeason(date);
    }

    /**
     * 取得日期的月份。失败返回0。
     * @param date 日期字符串
     * @return 月份
     */
    public static int getMonth(String date) {
        return getMonth(parseToDate(date));
    }

    /**
     * 取得日期的月份。失败返回0。
     * @param date 日期
     * @return 月份
     */
    public static int getMonth(Date date) {
        return getInteger(date, Calendar.MONTH) + 1;
    }

    /**
     * 取得日期的月份。失败返回null。
     *
     * @param date 日期字符串
     * @return 月份
     */
    public static Month getDefinedMonth(String date) {
        return Month.getMonthByNum(getMonth(date));
    }

    /**
     * 取得日期的月份。失败返回null。
     *
     * @param date 日期
     * @return 月份
     */
    public static Month getDefinedMonth(Date date) {
        return Month.getMonthByNum(getMonth(date));
    }

    /**
     * 取得日期所在月的总天数
     *
     * @param date 日期字符串
     * @return
     */
    public static int getDayOfMonth(String date) {
        return getDayOfMonth(parseToDate(date));
    }

    /**
     * 取得日期所在月的总天数
     *
     * @param date 日期
     * @return
     */
    public static int getDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 取得日期所在月已过的天数
     *
     * @param date 日期字符串
     * @return
     */
    public static int getPassDayOfMonth(String date) {
        return getPassDayOfMonth(parseToDate(date));
    }

    /**
     * 取得日期所在月已过的天数
     *
     * @param date 日期
     * @return
     */
    public static int getPassDayOfMonth(Date date) {
        return getInteger(date, Calendar.DAY_OF_MONTH);
    }

    /**
     * 取得日期所在月剩余的天数
     *
     * @param date 日期字符串
     * @return
     */
    public static int getRemainDayOfMonth(String date) {
        return getRemainDayOfMonth(parseToDate(date));
    }

    /**
     * 取得日期所在月剩余的天数
     *
     * @param date 日期
     * @return
     */
    public static int getRemainDayOfMonth(Date date) {
        return getDayOfMonth(date) - getPassDayOfMonth(date);
    }

    /**
     * 取得日期所在月的第一天
     *
     * @param date 日期字符串
     * @return
     */
    public static String getFirstDateOfMonth(String date) {
        DateStyle dateStyle = getDateStyle(date);
        return parseToString(getFirstDateOfMonth(parseToDate(date, dateStyle)), dateStyle);
    }

    /**
     * 取得日期所在月的第一天
     *
     * @param date 日期
     * @return
     */
    public static Date getFirstDateOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * 取得日期所在月的最后一天
     *
     * @param date 日期字符串
     * @return
     */
    public static String getLastDateOfMonth(String date) {
        DateStyle dateStyle = getDateStyle(date);
        return parseToString(getLastDateOfMonth(parseToDate(date, dateStyle)), dateStyle);
    }

    /**
     * 取得日期所在月的最后一天
     *
     * @param date 日期
     * @return
     */
    public static Date getLastDateOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }


    /**
     * 取得日期的星期(星期一为1，星期日为7)。失败返回0。
     *
     * @param date 日期字符串
     * @return 星期
     */
    public static int getWeekDay(String date) {
        return getWeekDay(parseToDate(date));
    }

    /**
     * 取得日期的星期(星期一为1，星期天为7)。失败返回0。
     *
     * @param date 日期
     * @return 星期
     */
    public static int getWeekDay(Date date) {
        int num = getInteger(date, Calendar.DAY_OF_WEEK) - 1;
        if (num == 0) {
            return 7;
        } else if (num > 0 && num < 7) {
            return num;
        } else {
            return 0;
        }
    }

    /**
     * 取得日期所在一年中的第几周
     *
     * @param date 日期字符串
     * @return 一年的第几周
     */
    public static int getWeekOfYear(String date) {
        return getWeekOfYear(parseToDate(date));
    }

    /**
     * 取得日期所在一年中的第几周
     *
     * @param date 日期
     * @return 一年的第几周
     */
    public static int getWeekOfYear(Date date) {
        return getInteger(date, Calendar.WEEK_OF_YEAR);
    }

    /**
     * 取得日期所在年份里的最大周数
     *
     * @param date 日期字符串
     * @return
     */
    public static int getWeeksOfYear(String date) {
        return getWeeksOfYear(parseToDate(date));
    }

    /**
     * 取得日期所在年份里的最大周数
     *
     * @param date 日期
     * @return
     */
    public static int getWeeksOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 取得日期所在月里的第几周
     *
     * @param date 日期字符串
     * @return
     */
    public static int getWeekOfMonth(String date) {
        return getWeeksOfMonth(parseToDate(date));
    }

    /**
     * 取得日期所在月里的第几周
     *
     * @param date 日期
     * @return
     */
    public static int getWeekOfMonth(Date date) {
        return getInteger(date, Calendar.WEEK_OF_MONTH);
    }

    /**
     * 取得日期所在月里的最大周数
     *
     * @param date 日期字符串
     * @return
     */
    public static int getWeeksOfMonth(String date) {
        return getWeeksOfMonth(parseToDate(date));
    }

    /**
     * 取得日期所在月里的最大周数
     *
     * @param date 日期
     * @return
     */
    public static int getWeeksOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 取得日期所在周在当前月的天数
     *
     * @param date 日期字符串
     * @return
     */
    public static int getDayOfWeekInMonth(String date) {
        return getDayOfWeekInMonth(parseToDate(date));
    }

    /**
     * 取得日期所在周在当前月的天数
     *
     * @param date 日期
     * @return
     */
    public static int getDayOfWeekInMonth(Date date) {
        return getInteger(date, Calendar.DAY_OF_WEEK_IN_MONTH);
    }

    /**
     * 取得日期所在周的星期。
     *
     * @param date 日期字符串
     * @return 星期
     */
    public static Week getDefinedWeekOfWeek(String date) {
        return Week.getWeekByNum(getWeekDay(date));
    }

    /**
     * 取得日期所在周的星期。失败返回null。
     *
     * @param date 日期
     * @return 星期
     */
    public static Week getDefinedWeekOfWeek(Date date) {
        return Week.getWeekByNum(getWeekDay(date));
    }

    /**
     * 取得日期所在周的周一日期(周一为一周的第一天)
     *
     * @param date 日期
     * @return
     */
    public static String getMondayOfWeek(String date) {
        DateStyle dateStyle = getDateStyle(date);
        return parseToString(getMondayOfWeek(parseToDate(date, dateStyle)), dateStyle);
    }

    /**
     * 取得日期所在周的周一日期(周一为一周的第一天)
     *
     * @param date 日期
     * @return
     */
    public static Date getMondayOfWeek(Date date) {
        return getOtherDayOfWeek(date, Calendar.MONDAY);
    }

    /**
     * 取得日期所在周的周日日期(周一为一周的第一天)
     *
     * @param date 日期
     * @return
     */
    public static String getSundayOfWeek(String date) {
        DateStyle dateStyle = getDateStyle(date);
        return parseToString(getSundayOfWeek(parseToDate(date, dateStyle)), dateStyle);
    }

    /**
     * 取得日期所在周的周日日期(周一为一周的第一天)
     *
     * @param date 日期
     * @return
     */
    public static Date getSundayOfWeek(Date date) {
        return getOtherDayOfWeek(date, Calendar.SUNDAY);
    }

    /**
     * 取得日期所在周的另外一个指定日期
     *
     * @param date           日期
     * @param firstDayOfWeek 指定当前所在周的第一天
     * @param dayOfWeek      要取得的日期所在当前周的位置
     * @return
     */
    public static String getOtherDayOfWeek(String date, int firstDayOfWeek, int dayOfWeek) {
        DateStyle dateStyle = getDateStyle(date);
        Date p = getOtherDayOfWeek(parseToDate(date, dateStyle), firstDayOfWeek, dayOfWeek);
        return parseToString(p, dateStyle);
    }

    /**
     * 取得日期所在周的另外一个指定日期
     *
     * @param date           日期
     * @param firstDayOfWeek 指定当前所在周的第一天
     * @param dayOfWeek      要取得的日期所在当前周的位置
     * @return
     */
    public static Date getOtherDayOfWeek(Date date, int firstDayOfWeek, int dayOfWeek) {
        Calendar day = Calendar.getInstance();
        day.setTime(date);
        day.setFirstDayOfWeek(firstDayOfWeek);
        day.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        return day.getTime();
    }

    /**
     * 取得日期所在周的另外一个指定日期(周一为一周的第一天)
     *
     * @param date      日期
     * @param dayOfWeek 要取得的日期所在当前周的位置
     * @return
     */
    public static String getOtherDayOfWeek(String date, int dayOfWeek) {
        return getOtherDayOfWeek(date, FIRST_DAY_OF_WEEK, dayOfWeek);
    }

    /**
     * 取得日期所在周的另外一个指定日期(周一为一周的第一天)
     *
     * @param date      日期
     * @param dayOfWeek 要取得的日期所在当前周的位置
     * @return
     */
    public static Date getOtherDayOfWeek(Date date, int dayOfWeek) {
        return getOtherDayOfWeek(date, FIRST_DAY_OF_WEEK, dayOfWeek);
    }

    /**
     * 取得日期的天数。失败返回0。
     * @param date 日期字符串
     * @return 天
     */
    public static int getDay(String date) {
        return getDay(parseToDate(date));
    }

    /**
     * 取得日期的天数。失败返回0。
     * @param date 日期
     * @return 天
     */
    public static int getDay(Date date) {
        return getInteger(date, Calendar.DATE);
    }

    /**
     * 取得日期的小时。失败返回0。
     * @param date 日期字符串
     * @return 小时
     */
    public static int getHour(String date) {
        return getHour(parseToDate(date));
    }

    /**
     * 取得日期的小时。失败返回0。
     * @param date 日期
     * @return 小时
     */
    public static int getHour(Date date) {
        return getInteger(date, Calendar.HOUR_OF_DAY);
    }

    /**
     * 取得日期的分钟。失败返回0。
     * @param date 日期字符串
     * @return 分钟
     */
    public static int getMinute(String date) {
        return getMinute(parseToDate(date));
    }

    /**
     * 取得日期的分钟。失败返回0。
     * @param date 日期
     * @return 分钟
     */
    public static int getMinute(Date date) {
        return getInteger(date, Calendar.MINUTE);
    }

    /**
     * 取得日期的秒钟。失败返回0。
     * @param date 日期字符串
     * @return 秒钟
     */
    public static int getSecond(String date) {
        return getSecond(parseToDate(date));
    }

    /**
     * 取得日期的秒钟。失败返回0。
     * @param date 日期
     * @return 秒钟
     */
    public static int getSecond(Date date) {
        return getInteger(date, Calendar.SECOND);
    }

    /**
     * 取得日期 。默认yyyy-MM-dd格式。失败返回null。
     * @param date 日期字符串
     * @return 日期
     */
    public static String getDate(String date) {
        return parseToString(date, DateStyle.YYYY_MM_DD);
    }

    /**
     * 取得日期。默认yyyy-MM-dd格式。失败返回null。
     * @param date 日期
     * @return 日期
     */
    public static String getDate(Date date) {
        return parseToString(date, DateStyle.YYYY_MM_DD);
    }

    /**
     * 取得日期的时间。默认HH:mm:ss格式。失败返回null。
     * @param date 日期字符串
     * @return 时间
     */
    public static String getTime(String date) {
        return parseToString(date, DateStyle.HH_MM_SS);
    }

    /**
     * 取得日期的时间。默认HH:mm:ss格式。失败返回null。
     * @param date 日期
     * @return 时间
     */
    public static String getTime(Date date) {
        return parseToString(date, DateStyle.HH_MM_SS);
    }

    /**
     * 取得两个日期相差的天数
     * @param date 日期字符串
     * @param otherDate 另一个日期字符串
     * @return 相差天数。如果失败则返回-1
     */
    public static int getIntervalDays(String date, String otherDate) {
        return getIntervalDays(parseToDate(date), parseToDate(otherDate));
    }

    /**
     * @param date 日期
     * @param otherDate 另一个日期
     * @return 相差天数。如果失败则返回-1
     */
    public static int getIntervalDays(Date date, Date otherDate) {
        int num = -1;
        Date dateTmp = DateUtils.parseToDate(DateUtils.getDate(date), DateStyle.YYYY_MM_DD);
        Date otherDateTmp = DateUtils
                .parseToDate(DateUtils.getDate(otherDate), DateStyle.YYYY_MM_DD);
        if (dateTmp != null && otherDateTmp != null) {
            long time = Math.abs(dateTmp.getTime() - otherDateTmp.getTime());
            num = (int) (time / (24 * 60 * 60 * 1000));
        }
        return num;
    }

    /**
     * 判断是否是闰年
     *
     * @param year 年份
     * @return 如果是闰年，则返回true，否则返回false
     */
    public static boolean isLeapYear(int year) {
        if ((year % 400) == 0)
            return true;
        else if ((year % 4) == 0) {
            if ((year % 100) == 0) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * 判断是否是闰年
     *
     * @param date 日期
     * @return 如果是闰年，则返回true，否则返回false
     */
    public static boolean isLeapYear(Date date) {
        return isLeapYear(getYear(date));
    }

    /**
     * 判断是否是闰年
     *
     * @param date 日期字符串
     * @return 如果是闰年，则返回true，否则返回false
     */
    public static boolean isLeapYear(String date) {
        return isLeapYear(parseToDate(date));
    }

    /**
     * 将过去的某个时间点与当前时间做比较，返回时间差,格式如下：<br/>
     * 日期为空或大于当前时间显示【未知】<br/>
     * 相差小于1秒钟显示【刚刚】<br/>
     * 相差小于1分钟显示【几秒前】<br/>
     * 相差等于30分钟显示【半小时前】<br/>
     * 相差小于1小时显示【几分钟前】<br/>
     * 相差等于12小时显示【半天前】<br/>
     * 相差小于24小时显示【几小时前】<br/>
     * 相差小于48小时显示【昨天】<br/>
     * 相差小于30天显示【几天前】<br/>
     * 相差小于10周显示【几星期前】<br/>
     * 相差小于12个月(360天)显示【几月前】<br/>
     * 相差小于365天显示【12月前】<br/>
     * 其他显示【几年前】<br/>
     *
     * @param date 日期
     * @return
     */
    public static String pastTimeFormat(Date date) {
        if (null == date) {
            return ONE_UNKNOWN;
        }
        long delta = differMills(new Date(), date);
        if (delta < 0) {
            return ONE_UNKNOWN;
        }
        if (delta < ONE_SECOND) {
            return JUST_NOW;
        }
        if (delta < ONE_MINUTE) {
            long seconds = millisToSeconds(delta);
            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
        }
        if (delta < 60L * ONE_MINUTE) {
            long minutes = millisToMinutes(delta);
            if (minutes == 30) {
                return PART_HOUR_AGO;
            }
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = millisToHours(delta);
            if (hours == 12) {
                return PART_DAY_AGO;
            }
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
        }
        if (delta < 48L * ONE_HOUR) {
            return YESTERDAY;
        }
        if (delta < 30L * ONE_DAY) {
            long days = millisToDays(delta);
            return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
        }
        if (delta < 10 * ONE_WEEK) {
            long months = millisToWeeks(delta);
            return (months <= 0 ? 1 : months) + ONE_WEEK_AGO;
        }
        if (delta < 12L * ONE_MONTH) {
            long months = millisToMonths(delta);
            return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
        }
        if (delta < ONE_YEAR) {
            return 12 + ONE_MONTH_AGO;
        } else {
            long years = millisToYears(delta);
            return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
        }
    }

    /**
     * 将过去的某个时间点与当前时间做比较，返回时间差,格式如下：<br/>
     * 日期为空或大于当前时间显示【未知】<br/>
     * 相差小于1秒钟显示【刚刚】<br/>
     * 相差小于1分钟显示【几秒前】<br/>
     * 相差等于30分钟显示【半小时前】<br/>
     * 相差小于1小时显示【几分钟前】<br/>
     * 相差等于12小时显示【半天前】<br/>
     * 相差小于24小时显示【几小时前】<br/>
     * 相差小于48小时显示【昨天】<br/>
     * 相差小于30天显示【几天前】<br/>
     * 相差小于10周显示【几星期前】<br/>
     * 相差小于12个月(360天)显示【几月前】<br/>
     * 相差小于365天显示【12月前】<br/>
     * 其他显示【几年前】<br/>
     *
     * @param date 日期字符串
     * @return
     */
    public static String pastTimeFormat(String date) {
        return pastTimeFormat(parseToDate(date));
    }

    /**
     * 将过去的某个时间点与当前时间做比较，返回时间差,格式如下：<br/>
     * 日期为空或大于当前时间显示【未知】<br/>
     * 相差小于1秒钟显示【刚刚】<br/>
     * 相差小于1分钟显示【几秒前】<br/>
     * 相差等于30分钟显示【半小时前】<br/>
     * 相差小于1小时显示【几分钟前】<br/>
     * 相差等于12小时显示【半天前】<br/>
     * 相差小于24小时显示【几小时前】<br/>
     * 相差小于48小时显示【昨天】<br/>
     * 相差小于30天显示【几天前】<br/>
     * 相差小于10周显示【几星期前】<br/>
     * 相差小于12个月(360天)显示【几月前】<br/>
     * 相差小于365天显示【12月前】<br/>
     * 其他显示【几年前】<br/>
     *
     * @param millis 时间戳
     * @return
     */
    public static String pastTimeFormat(Long millis) {
        return pastTimeFormat(new Date(millis));
    }

    /**
     * 毫秒数转秒数
     *
     * @param millis 毫秒数
     * @return
     */
    public static long millisToSeconds(long millis) {
        return millis / ONE_SECOND;
    }

    /**
     * 毫秒数转分钟数
     *
     * @param millis 毫秒数
     * @return
     */
    public static long millisToMinutes(long millis) {
        return millis / ONE_MINUTE;
    }

    /**
     * 毫秒数转分钟
     *
     * @param millis 毫秒数
     * @return
     */
    public static long millisToHours(long millis) {
        return millis / ONE_HOUR;
    }

    /**
     * 毫秒数转星期数
     *
     * @param millis 毫秒数
     * @return
     */
    public static long millisToWeeks(long millis) {
        return millis / ONE_WEEK;
    }

    /**
     * 毫秒数转天数
     *
     * @param millis 毫秒数
     * @return
     */
    public static long millisToDays(long millis) {
        return millis / ONE_DAY;
    }

    /**
     * 毫秒数转月数(30天/月)
     *
     * @param millis 毫秒数
     * @return
     */
    public static long millisToMonths(long millis) {
        return millis / ONE_MONTH;
    }

    /**
     * 毫秒数转年数(365天/年)
     *
     * @param millis 毫秒数
     * @return
     */
    public static long millisToYears(long millis) {
        return millis / ONE_YEAR;
    }

    /**
     * 两个日期之间相差的毫秒数
     *
     * @param date
     * @param otherDate
     * @return
     */
    public static Long differMills(Date date, Date otherDate) {
        return parseToLong(date) - parseToLong(otherDate);
    }

    /**
     * 两个日期之间相差的秒数
     *
     * @param date
     * @param otherDate
     * @return
     */
    public static Long differSeconds(Date date, Date otherDate) {
        return differMills(date, otherDate) / ONE_SECOND;
    }

    /**
     * 两个日期之间相差的分钟数
     *
     * @param date
     * @param otherDate
     * @return
     */
    public static Long differMinuts(Date date, Date otherDate) {
        return differMills(date, otherDate) / ONE_MINUTE;
    }

    /**
     * 两个日期之间相差的分钟数
     *
     * @param date
     * @param otherDate
     * @return
     */
    public static Long differHours(Date date, Date otherDate) {
        return differMills(date, otherDate) / ONE_HOUR;
    }

    /**
     * 两个日期之间相差的天数
     *
     * @param date
     * @param otherDate
     * @return
     */
    public static Long differDays(Date date, Date otherDate) {
        return differMills(date, otherDate) / ONE_DAY;
    }

    /**
     * 两个日期之间相差的周数
     *
     * @param date
     * @param otherDate
     * @return
     */
    public static Long differWeeks(Date date, Date otherDate) {
        return differMills(date, otherDate) / ONE_WEEK;
    }

    /**
     * 判断两时间段是否有交集
     *
     * @param start1 第一段开始时间
     * @param end1   第一段结束时间
     * @param start2 第二段开始时间
     * @param end2   第二段结束时间
     * @return 如果有交集，则返回true，否则返回false
     */
    public boolean hasCommon(Date start1, Date end1, Date start2, Date end2) {
        if (end1.before(start2) || end2.before(start1))
            return false;
        else
            return true;
    }

    /**
     * 获取某天的开始时刻(0:00:00)
     *
     * @param date 日期
     * @return
     */
    public static Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取某天的开始时刻(0:00:00)
     *
     * @param date 日期字符串
     * @return
     */
    public static String getStartOfDay(String date) {
        DateStyle dateStyle = getDateStyle(date);
        return parseToString(getStartOfDay(parseToDate(date, dateStyle)), dateStyle);
    }

    /**
     * 获取某天的下一天的开始时刻(0:00:00)
     *
     * @param date 日期
     * @return
     */
    public static Date getStartOfNextDay(Date date) {
        return addDay(getStartOfDay(date), 1);
    }

    /**
     * 获取某天的下一天的开始时刻(0:00:00)
     *
     * @param date 日期字符串
     * @return
     */
    public static String getStartOfNextDay(String date) {
        DateStyle dateStyle = getDateStyle(date);
        return parseToString(getStartOfNextDay(parseToDate(date, dateStyle)), dateStyle);
    }

    /**
     * 获取某天的最后时刻(23:59:59)
     *
     * @param date 日期
     * @return
     */
    public static Date getEndOfDay(Date date) {
        return new Date(getStartOfNextDay(date).getTime() - ONE_MILLIS);
    }

    /**
     * 获取某天的最后时刻(23:59:59)
     *
     * @param date 日期字符串
     * @return
     */
    public static String getEndOfDay(String date) {
        DateStyle dateStyle = getDateStyle(date);
        return parseToString(getEndOfDay(parseToDate(date, dateStyle)), dateStyle);
    }

    /**
     * 获取某天的前一天的最后时刻(23:59:59)
     *
     * @param date 日期
     * @return
     */
    public static Date getEndOfPrevDay(Date date) {
        return new Date(getStartOfDay(date).getTime() - ONE_MILLIS);
    }

    /**
     * 获取某天的前一天的最后时刻(23:59:59)
     *
     * @param date 日期字符串
     * @return
     */
    public static String getEndOfPrevDay(String date) {
        DateStyle dateStyle = getDateStyle(date);
        return parseToString(getEndOfPrevDay(parseToDate(date, dateStyle)), dateStyle);
    }

    enum DateScope{
        
    }

}