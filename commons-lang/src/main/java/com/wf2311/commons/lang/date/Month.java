package com.wf2311.commons.lang.date;

/**
 * 月份枚举
 * @author wf2311
 * @time 2016/05/13 13:57.
 */
public enum Month {
    JANUARY("一月", "January", "Jan.", 1),

    FEBRUARY("二月", "February", "Feb.", 2),

    MARCH("三月", "March", "Mar.", 3),

    APRIL("四月", "April", "Apr.", 4),

    MAY("五月", "May", "May.", 5),

    JUNE("六月", "June", "Jun.", 6),

    JULY("七月", "July", "Jul.", 7),

    AUGUST("八月", "August", "Aug.", 8),

    SEPTEMBER("九月", "September", "Sep.", 9),

    OCTOBER("十月", "October", "Oct.", 10),

    NOVEMBER("十一月", "November", "Nov.", 11),

    DECEMBER("十二月", "December", "Dec.", 12);

    String cn;
    String en;
    String enShort;
    int number;

    /**
     * 根据月份中文名得到月份,无匹配数据返回null
     *
     * @param cn 月份中文名
     * @return 指定的月份
     */
    public static Month getMonthByCn(String cn) {
        for (Month month : Month.values()) {
            if (month.getChineseName().equals(cn)) {
                return month;
            }
        }
        return null;
    }

    /**
     * 根据月份英文名(忽略大小写)得到月份,无匹配数据返回null
     *
     * @param en 月份英文文名
     * @return 指定的月份
     */
    public static Month getMonthByEn(String en) {
        for (Month month : Month.values()) {
            if (month.getName().equalsIgnoreCase(en)) {
                return month;
            }
        }
        return null;
    }

    /**
     * 根据月份英文缩写名(忽略大小写)得到月份,无匹配数据返回null
     *
     * @param enShort 月份英文缩写文名
     * @return 指定的月份
     */
    public static Month getMonthByShort(String enShort) {
        for (Month month : Month.values()) {
            if (month.getShortName().equalsIgnoreCase(enShort)) {
                return month;
            }
        }
        return null;
    }

    /**
     * 根据月份数字得到月份
     *
     * @param number 月份数字,无匹配数据返回null
     * @return 指定的月份
     */
    public static Month getMonthByNum(int number) {
        for (Month month : Month.values()) {
            if (month.getNumber() == number) {
                return month;
            }
        }
        return null;
    }

    /**
     * @param cn      中文名
     * @param en      英文名
     * @param enShort 英文缩写
     * @param number  数字
     */
    Month(String cn, String en, String enShort, int number) {
        this.cn = cn;
        this.en = en;
        this.enShort = enShort;
        this.number = number;
    }

    /**
     * 中文名
     *
     * @return
     */
    public String getChineseName() {
        return cn;
    }

    /**
     * 英文名
     *
     * @return
     */
    public String getName() {
        return en;
    }

    /**
     * 英文缩写
     *
     * @return
     */
    public String getShortName() {
        return enShort;
    }

    /**
     * 数字
     *
     * @return
     */
    public int getNumber() {
        return number;
    }
}
