package com.wf2311.commons.lang;

import java.util.Random;

/**
 * 随机数、随机字符串生成工具
 * @author wf2311
 * @time 2016/06/27 13:25.
 */
public class RandomUtils {
    public static final String ALL_CHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String LETTER = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String NUMBER = "0123456789";

    /**
     * 返回一个定长的随机数字(数字)
     *
     * @param length 随机字符串长度
     * @return 返回随机字符串
     */
    public static String generateNumber(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(NUMBER.charAt(random.nextInt(NUMBER.length())));
        }
        return sb.toString();
    }

    /**
     * 返回一个定长的随机字符串(只包含大小写字母、数字)
     *
     * @param length 随机字符串长度
     * @return 返回随机字符串
     */
    public static String generateString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALL_CHAR.charAt(random.nextInt(ALL_CHAR.length())));
        }
        return sb.toString();
    }

    /**
     * 返回一个定长的随机纯字母字符串(只包含大小写字母)
     *
     * @param length 随机字符串长度
     * @return 返回随机字符串
     */
    public static String generateMixString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(LETTER.charAt(random.nextInt(LETTER.length())));
        }
        return sb.toString();
    }

    /**
     * 返回一个定长的随机纯小写字母字符串
     *
     * @param length 随机字符串长度
     * @return 返回随机字符串
     */
    public static String generateLowerString(int length) {
        return generateMixString(length).toLowerCase();
    }

    /**
     * 返回一个定长的随机纯大写字母字符串
     *
     * @param length 随机字符串长度
     * @return 返回随机字符串
     */
    public static String generateUpperString(int length) {
        return generateMixString(length).toUpperCase();
    }

    /**
     * 生成一个定长的纯0字符串
     *
     * @param length 字符串长度
     * @return 返回纯0字符串
     */
    public static String generateZeroString(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append('0');
        }
        return sb.toString();
    }

    /**
     * 根据数字生成一个定长的字符串，长度不够前面补0
     *
     * @param num       数字
     * @param fixdlenth 字符串长度
     * @return 返回定长的字符串
     */
    public static String toFixdLengthString(long num, int fixdlenth) {
        StringBuffer sb = new StringBuffer();
        String strNum = String.valueOf(num);
        if (fixdlenth - strNum.length() >= 0) {
            sb.append(generateZeroString(fixdlenth - strNum.length()));
        } else {
            throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth + "的字符串发生异常！");
        }
        sb.append(strNum);
        return sb.toString();
    }

    /**
     * 根据数字生成一个定长的字符串，长度不够前面补0
     *
     * @param num       数字
     * @param fixdlenth 字符串长度
     * @return 返回定长的字符串
     */
    public static String toFixdLengthString(int num, int fixdlenth) {
        return toFixdLengthString(Long.valueOf(num), fixdlenth);
    }

    /**
     * 随机生成一个大于零的整数
     *
     * @param numLength 这个小于多少，如:0~100之间填写100即可
     * @return 返回生成的随机数
     */
    public static int rondomOneNum(int numLength) {
        Random random = new Random();
        return random.nextInt(numLength);
    }
}
