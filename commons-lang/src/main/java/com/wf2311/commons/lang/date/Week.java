package com.wf2311.commons.lang.date;

/**
 * 星期周枚举
 * @author wf2311
 * @time 2016/05/13 13:57.
 */
public enum Week {

    MONDAY("星期一", "Monday", "Mon.", 1),

    TUESDAY("星期二", "Tuesday", "Tues.", 2),

    WEDNESDAY("星期三", "Wednesday", "Wed.", 3),

    THURSDAY("星期四", "Thursday", "Thur.", 4),

    FRIDAY("星期五", "Friday", "Fri.", 5),

    SATURDAY("星期六", "Saturday", "Sat.", 6),

    SUNDAY("星期日", "Sunday", "Sun.", 7);

    String cn;
    String en;
    String enShort;
    int    number;

    /**
     * @param cn      中文名
     * @param en      英文名
     * @param enShort 英文缩写
     * @param number  数字
     */
    Week(String cn, String en, String enShort, int number) {
        this.cn = cn;
        this.en = en;
        this.enShort = enShort;
        this.number = number;
    }

    /**
     * 根据星期中文名得到星期,无匹配数据返回null
     *
     * @param cn 星期中文名
     * @return 指定的星期
     */
    public static Week getWeekByCn(String cn) {
        for (Week week : Week.values()) {
            if (week.getChineseName().equals(cn)) {
                return week;
            }
        }
        return null;
    }

    /**
     * 根据星期英文名(忽略大小写)得到星期,无匹配数据返回null
     *
     * @param en 星期英文名
     * @return 指定的星期
     */
    public static Week getWeekByEn(String en) {
        for (Week week : Week.values()) {
            if (week.getName().equalsIgnoreCase(en)) {
                return week;
            }
        }
        return null;
    }

    /**
     * 根据星期英文缩写名(忽略大小写)得到星期,无匹配数据返回null
     *
     * @param enShort 星期英文缩写名
     * @return 指定的星期
     */
    public static Week getWeekByShort(String enShort) {
        for (Week week : Week.values()) {
            if (week.getShortName().equalsIgnoreCase(enShort)) {
                return week;
            }
        }
        return null;
    }

    /**
     * 根据星期数字得到星期,无匹配数据返回null
     *
     * @param number 星期数字
     * @return 指定的星期
     */
    public static Week getWeekByNum(int number) {
        for (Week week : Week.values()) {
            if (week.getNumber() == number) {
                return week;
            }
        }
        return null;
    }

    /**
     * 中文名
     * @return
     */
    public String getChineseName() {
        return cn;
    }

    /**
     * 英文名
     * @return
     */
    public String getName() {
        return en;
    }

    /**
     * 英文缩写
     * @return
     */
    public String getShortName() {
        return enShort;
    }

    /**
     * 数字
     * @return
     */
    public int getNumber() {
        return number;
    }
}