package com.wf2311.commons.lang;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wf2311
 * @time 2016/06/27 14:27.
 */
public class ValidateUtils {
    public static final String EMAIL_REGEX = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    public static final String MOBILE_REGEX = "^[1][1-8][0-9]{9}";

    /**
     * 验证邮箱格式是否正确
     *
     * @param email 邮箱地址
     * @return 如果正确，则返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        String regex = EMAIL_REGEX;
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(email);
        return matcher.matches();
    }

    /**
     * 验证长度
     *
     * @param str       需验证的字符串
     * @param minLength 字符串的最小长度
     * @param maxLength 字符串的最大长度
     * @return 如果验证通过，则返回true，否则返回false
     */
    public static boolean checkLength(String str, int minLength, int maxLength) {
        if (str != null) {
            int len = str.length();
            if (minLength == 0)
                return len <= maxLength;
            else if (maxLength == 0)
                return len >= minLength;
            else
                return (len >= minLength && len <= maxLength);
        }
        return false;
    }

    /**
     * 验证手机号码
     *
     * @param phoneNumber 手机号码
     * @return 如果是手机号，则返回true，否则返回false
     */
    public static boolean isMobile(String phoneNumber) {
        phoneNumber = phoneNumber.trim();
        String pattern = MOBILE_REGEX;
        return phoneNumber.matches(pattern);
    }
}
