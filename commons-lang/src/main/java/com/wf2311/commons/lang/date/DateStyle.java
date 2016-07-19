package com.wf2311.commons.lang.date;

/**
 * 日期格式
 * @author wf2311
 * @time 2016/05/13 13:57.
 */
public enum DateStyle {

    YYYY_MM("yyyy-MM", false),

    YYYY_MM_DD("yyyy-MM-dd", false),

    YYYY_MM_DD_HH("yyyy-MM-dd HH", false),

    YYYY_MM_DD_HH_MM("yyyy-MM-dd HH:mm", false),

    YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss", false),

    YYYYMM("yyyyMM", false),

    YYYYMMDD("yyyyMMdd", false),

    YYYYMMDDHH("yyyyMMddHH", false),

    YYYYMMDDHHMM("yyyyMMddHHmm", false),

    YYYYMMDDHHMMSS("yyyyMMddHHmmss", false),

    YYYYMMDDHHMMSSS("yyyyMMddHHmmssS", false),

    YYYY_MM_EN("yyyy/MM", false),

    YYYY_MM_DD_EN("yyyy/MM/dd", false),

    YYYY_MM_DD_HH_EN("yyyy/MM/dd HH", false),

    YYYY_MM_DD_HH_MM_EN("yyyy/MM/dd HH:mm", false),

    YYYY_MM_DD_HH_MM_SS_EN("yyyy/MM/dd HH:mm:ss", false),

    YYYY_MM_CN("yyyy年MM月", false),

    YYYY_MM_DD_CN("yyyy年MM月dd日", false),

    YYYY_MM_DD_CN2("yyyy年MM月dd号", false),

    YYYY_MM_DD_HH_CN("yyyy年MM月dd日 HH点", false),
//    YYYY_MM_DDHH_CN("yyyy年MM月dd日HH点", false),

    YYYY_MM_DD_HH_CN_2("yyyy年MM月dd日 HH时", false),
//    YYYY_MM_DDHH_CN_2("yyyy年MM月dd日HH时", false),

    YYYY_MM_DD_HH_CN2("yyyy年MM月dd号 HH点", false),
//    YYYY_MM_DDHH_CN2("yyyy年MM月dd号HH点", false),

    YYYY_MM_DD_HH_CN2_2("yyyy年MM月dd号 HH时", false),
//    YYYY_MM_DDHH_CN2_2("yyyy年MM月dd号HH时", false),

    YYYY_MM_DD_HH_MM_CN("yyyy年MM月dd日 HH:mm", false),
//    YYYY_MM_DDHH_MM_CN("yyyy年MM月dd日HH:mm", false),

    YYYY_MM_DD_HH_MM_CN_2("yyyy年MM月dd日 HH点mm分", false),
//    YYYY_MM_DDHH_MM_CN_2("yyyy年MM月dd日HH点mm分", false),

    YYYY_MM_DD_HH_MM_CN_3("yyyy年MM月dd日 HH时mm分", false),
//    YYYY_MM_DDHH_MM_CN_3("yyyy年MM月dd日HH时mm分", false),

    YYYY_MM_DD_HH_MM_CN2("yyyy年MM月dd号 HH:mm", false),
//    YYYY_MM_DDHH_MM_CN2("yyyy年MM月dd号HH:mm", false),

    YYYY_MM_DD_HH_MM_CN2_2("yyyy年MM月dd号 HH点mm分", false),
//    YYYY_MM_DDHH_MM_CN2_2("yyyy年MM月dd号HH点mm分", false),

    YYYY_MM_DD_HH_MM_CN2_3("yyyy年MM月dd号 HH时mm分", false),
//    YYYY_MM_DDHH_MM_CN2_3("yyyy年MM月dd号HH时mm分", false),

    YYYY_MM_DD_HH_MM_SS_CN("yyyy年MM月dd日 HH:mm:ss", false),
//    YYYY_MM_DDHH_MM_SS_CN("yyyy年MM月dd日HH:mm:ss", false),

    YYYY_MM_DD_HH_MM_SS_CN_2("yyyy年MM月dd日 HH点mm分ss秒", false),
//    YYYY_MM_DDHH_MM_SS_CN_2("yyyy年MM月dd日HH点mm分ss秒", false),

    YYYY_MM_DD_HH_MM_SS_CN_3("yyyy年MM月dd日 HH时mm分ss秒", false),
//    YYYY_MM_DDHH_MM_SS_CN_3("yyyy年MM月dd日HH时mm分ss秒", false),


    YYYY_MM_DD_HH_MM_SS_CN_3_2("yyyy年MM月dd日 HH:mm:ss", false),
//    YYYY_MM_DDHH_MM_SS_CN_3_2("yyyy年MM月dd日HH:mm:ss", false),

    YYYY_MM_DD_HH_MM_SS_CN2("yyyy年MM月dd号 HH:mm:ss", false),
//    YYYY_MM_DDHH_MM_SS_CN2("yyyy年MM月dd号HH:mm:ss", false),

    YYYY_MM_DD_HH_MM_SS_CN2_2("yyyy年MM月dd号 HH点mm分ss秒", false),
//    YYYY_MM_DDHH_MM_SS_CN2_2("yyyy年MM月dd号HH点mm分ss秒", false),

    YYYY_MM_DD_HH_MM_SS_CN2_3("yyyy年MM月dd号 HH时mm分ss秒", false),
//    YYYY_MM_DDHH_MM_SS_CN2_3("yyyy年MM月dd号HH时mm分ss秒", false),

    YY_MM("yy-MM", true),

    MM_DD("MM-dd", true),

    MM_DD_HH_MM("MM-dd HH:mm", true),

    MM_DD_HH_MM_SS("MM-dd HH:mm:ss", true),

    HH_MM("HH:mm", true),

    HH_MM_SS("HH:mm:ss", true),

    MM_DD_EN("MM/dd", true),

    MM_DD_HH_MM_EN("MM/dd HH:mm", true),

    MM_DD_HH_MM_SS_EN("MM/dd HH:mm:ss", true),

    MM_DD_CN("MM月dd日", true),

    MM_DD_CN2("MM月dd号", true),

    MM_DD_HH_MM_CN("MM月dd日 HH:mm", true),

    MM_DD_HH_MM_CN_2("MM月dd日 HH点mm分", true),

    MM_DD_HH_MM_CN_3("MM月dd日 HH时mm分", true),

    MM_DD_HH_MM_CN2("MM月dd号 HH:mm", true),

    MM_DD_HH_MM_CN2_2("MM月dd号 HH点mm分", true),

    MM_DD_HH_MM_CN2_3("MM月dd号 HH时mm分", true),

    MM_DD_HH_MM_SS_CN("MM月dd日 HH:mm:ss", true),

    MM_DD_HH_MM_SS_CN_2("MM月dd日 HH点mm分ss秒", true),

    MM_DD_HH_MM_SS_CN_3("MM月dd日 HH时mm分ss秒", true),

    MM_DD_HH_MM_SS_CN2("MM月dd号 HH:mm:ss", true),

    MM_DD_HH_MM_SS_CN2_2("MM月dd号 HH点mm分ss秒", true),

    MM_DD_HH_MM_SS_CN2_3("MM月dd号 HH时mm分ss秒", true);

    private String value;

    private boolean isShowOnly;

    DateStyle(String value, boolean isShowOnly) {
        this.value = value;
        this.isShowOnly = isShowOnly;
    }

    public String getValue() {
        return value;
    }

    public boolean isShowOnly() {
        return isShowOnly;
    }
}