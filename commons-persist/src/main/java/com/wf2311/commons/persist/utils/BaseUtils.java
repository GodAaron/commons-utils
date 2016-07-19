package com.wf2311.commons.persist.utils;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 */
public class BaseUtils {
    private static final Logger logger = Logger.getLogger(BaseUtils.class);

    /**
     * 集合非空校验
     */
    private static <T> boolean checkCollection(Collection<T> collections) {
        return collections != null && collections.size() > 0;
    }

    @SuppressWarnings("rawtypes")
    private static boolean checkMap(Map t) {
        return t != null && !t.isEmpty() && t.size() > 0;
    }

    /**
     * 对象(包括String)非空校验
     * 自定义对象  重写toString方法
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> boolean checkNotNull(T t) {
        if (t == null)
            return false;
        if (t instanceof Collection)
            return checkCollection((Collection<T>) t);
        if (t instanceof Map)
            return checkMap((Map) t);
        return t.toString() != null && !t.toString().trim().equals("");
    }

    public static boolean isNotEmpty(String str) {
        return !(str == null || str.isEmpty());
    }

    /**
     * 将Set集合转为List集合
     *
     * @param sets
     * @return
     */
    public static <T> List<T> convertSetToList(Set<T> sets) {
        if (checkCollection(sets)) {
            List<T> list = new ArrayList<T>();
            Iterator<T> it = sets.iterator();
            while (it.hasNext())
                list.add(it.next());
            return list;
        } else
            return null;
    }

    public static Date getNowDate() {
        return new Date();
    }

    /**
     * Date:2013-10-18
     *
     * @return yyyy-MM-dd
     * @author gr
     */
    public static String getNowShortDate() {
        return getToday("yyyy-MM-dd");
    }

    /**
     * Date:2013-10-18
     *
     * @return yyyy-MM-dd HH:mm:ss
     * @author gr
     */
    public static String getNowLongDate() {
        return getToday("yyyy-MM-dd HH:mm:ss");
    }

    private static String getToday(String format) {
        String time = "";
        SimpleDateFormat df = null;
        Calendar cal = new GregorianCalendar();
        df = new SimpleDateFormat(format);
        time = df.format(cal.getTime());
        return time;
    }

    public static boolean hasInArray(String[] strs, String str) {
        for (String tmp : strs) {
            if (tmp.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static String getRoot(HttpServletRequest request) {
        return request.getSession().getServletContext().getRealPath("/");
    }

    public static Date convertDateStr(String dateStr) {
        SimpleDateFormat sf = null;
        if (dateStr.length() <= 7) {//2012-12
            sf = new SimpleDateFormat("yyyy-MM");
        } else if (dateStr.length() <= 10) {
            sf = new SimpleDateFormat("yyyy-MM-dd");
            if (dateStr.indexOf("-") < 0) {
                String year = dateStr.substring(0, 4);
                String month = dateStr.substring(4, 6);
                String day = dateStr.substring(6, 8);
                dateStr = year + "-" + month + "-" + day;
            }
        } else {
            sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            if (dateStr.indexOf("-") < 0 && dateStr.indexOf(":") < 0) {
                String year = dateStr.substring(0, 4);
                String month = dateStr.substring(4, 6);
                String day = dateStr.substring(6, 8);
                String hour = dateStr.substring(8, 10);
                String minute = dateStr.substring(10, 12);
                String second = dateStr.substring(12, 14);
                dateStr = year + "-" + month + "-" + day + "-" + hour + "-" + minute + "-" + second;
            }
        }
        try {
            return sf.parse(dateStr);
        } catch (ParseException e) {
            logger.error(dateStr + "时间字符串格式错误！转换错误..(时间格式为yyyyMMdd或yyyy-MM-dd)");
        }
        return null;
    }

    public static boolean isInteger(Object obj) {
        try {
            Integer.valueOf(obj.toString());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public static String wrapstr(String prev, String self, String next) {
        return prev + self + next;
    }
}
