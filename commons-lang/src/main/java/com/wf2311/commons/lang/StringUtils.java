package com.wf2311.commons.lang;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author: wf2311
 * @date: 2015/10/20 16:58
 */
public class StringUtils {
    /**
     * 判断对象是否为空或长度为0或由空白符组成
     *
     * @param o object对象
     * @return 如果为空，则返回true，否则返回false
     */
    public static boolean isBlank(Object o) {
        return (o == null || o.toString().trim().equals("") || o.toString().length() < 0);
    }

    /**
     * 判断对象是否不为空并且长度不为0并且不由空白符组成
     *
     * @param o object对象
     * @return 如果为空，则返回false，否则返回true
     */
    public static boolean isNotBlank(Object o) {
        return !isBlank(o);
    }

    /**
     * 判断字段串是否为空或长度为0或由空白符组成
     *
     * @param str 要判断是否为空的字符串
     * @return 如果为空，则返回true，否则返回false
     */
    public static boolean isBlank(String str) {
        return (str == null || str.trim().equals("") || str.length() < 0);
    }


    /**
     * 判断字符串是否不为空并且长度不为0并且不由空白符组成
     *
     * @param str 要判断是否为空的字符串
     * @return 如果为空，则返回false，否则返回true
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 判断数组是否为空
     *
     * @param args
     * @return 如果是数组，则返回true，否则返回false
     */
    public static boolean isBlank(String[] args) {
        return args == null || args.length == 0;
    }

    /**
     *判断对象是否为空
     */
    public static boolean isEmpty(Object str) {
        return (str == null || "".equals(str));
    }

    /**
     * Check that the given CharSequence is neither {@code null} nor of length 0.
     * Note: Will return {@code true} for a CharSequence that purely consists of whitespace.
     * <p><pre class="code">
     * StringUtils.hasLength(null) = false
     * StringUtils.hasLength("") = false
     * StringUtils.hasLength(" ") = true
     * StringUtils.hasLength("Hello") = true
     * </pre>
     *
     * @param str the CharSequence to check (may be {@code null})
     * @return {@code true} if the CharSequence is not null and has length
     * @see #hasText(String)
     */
    public static boolean hasLength(CharSequence str) {
        return (str != null && str.length() > 0);
    }

    /**
     * Check that the given String is neither {@code null} nor of length 0.
     * Note: Will return {@code true} for a String that purely consists of whitespace.
     *
     * @param str the String to check (may be {@code null})
     * @return {@code true} if the String is not null and has length
     * @see #hasLength(CharSequence)
     */
    public static boolean hasLength(String str) {
        return hasLength((CharSequence) str);
    }

    /**
     * Check whether the given CharSequence has actual text.
     * More specifically, returns {@code true} if the string not {@code null},
     * its length is greater than 0, and it contains at least one non-whitespace character.
     * <p><pre class="code">
     * StringUtils.hasText(null) = false
     * StringUtils.hasText("") = false
     * StringUtils.hasText(" ") = false
     * StringUtils.hasText("12345") = true
     * StringUtils.hasText(" 12345 ") = true
     * </pre>
     *
     * @param str the CharSequence to check (may be {@code null})
     * @return {@code true} if the CharSequence is not {@code null},
     * its length is greater than 0, and it does not contain whitespace only
     * @see Character#isWhitespace
     */
    public static boolean hasText(CharSequence str) {
        if (!hasLength(str)) {
            return false;
        }
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check whether the given String has actual text.
     * More specifically, returns {@code true} if the string not {@code null},
     * its length is greater than 0, and it contains at least one non-whitespace character.
     *
     * @param str the String to check (may be {@code null})
     * @return {@code true} if the String is not {@code null}, its length is
     * greater than 0, and it does not contain whitespace only
     * @see #hasText(CharSequence)
     */
    public static boolean hasText(String str) {
        return hasText((CharSequence) str);
    }

    /**
     * Check whether the given CharSequence contains any whitespace characters.
     *
     * @param str the CharSequence to check (may be {@code null})
     * @return {@code true} if the CharSequence is not empty and
     * contains at least 1 whitespace character
     * @see Character#isWhitespace
     */
    public static boolean containsWhitespace(CharSequence str) {
        if (!hasLength(str)) {
            return false;
        }
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check whether the given String contains any whitespace characters.
     *
     * @param str the String to check (may be {@code null})
     * @return {@code true} if the String is not empty and
     * contains at least 1 whitespace character
     * @see #containsWhitespace(CharSequence)
     */
    public static boolean containsWhitespace(String str) {
        return containsWhitespace((CharSequence) str);
    }

    /**
     * Trim leading and trailing whitespace from the given String.
     *
     * @param str the String to check
     * @return the trimmed String
     * @see Character#isWhitespace
     */
    public static String trimWhitespace(String str) {
        if (!hasLength(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        while (sb.length() > 0 && Character.isWhitespace(sb.charAt(0))) {
            sb.deleteCharAt(0);
        }
        while (sb.length() > 0 && Character.isWhitespace(sb.charAt(sb.length() - 1))) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * Trim <i>all</i> whitespace from the given String:
     * leading, trailing, and in between characters.
     *
     * @param str the String to check
     * @return the trimmed String
     * @see Character#isWhitespace
     */
    public static String trimAllWhitespace(String str) {
        if (!hasLength(str)) {
            return str;
        }
        int len = str.length();
        StringBuilder sb = new StringBuilder(str.length());
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if (!Character.isWhitespace(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * Trim leading whitespace from the given String.
     *
     * @param str the String to check
     * @return the trimmed String
     * @see Character#isWhitespace
     */
    public static String trimLeadingWhitespace(String str) {
        if (!hasLength(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        while (sb.length() > 0 && Character.isWhitespace(sb.charAt(0))) {
            sb.deleteCharAt(0);
        }
        return sb.toString();
    }

    /**
     * Trim trailing whitespace from the given String.
     *
     * @param str the String to check
     * @return the trimmed String
     * @see Character#isWhitespace
     */
    public static String trimTrailingWhitespace(String str) {
        if (!hasLength(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        while (sb.length() > 0 && Character.isWhitespace(sb.charAt(sb.length() - 1))) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * Trim all occurrences of the supplied leading character from the given String.
     *
     * @param str              the String to check
     * @param leadingCharacter the leading character to be trimmed
     * @return the trimmed String
     */
    public static String trimLeadingCharacter(String str, char leadingCharacter) {
        if (!hasLength(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        while (sb.length() > 0 && sb.charAt(0) == leadingCharacter) {
            sb.deleteCharAt(0);
        }
        return sb.toString();
    }

    /**
     * Trim all occurrences of the supplied trailing character from the given String.
     *
     * @param str               the String to check
     * @param trailingCharacter the trailing character to be trimmed
     * @return the trimmed String
     */
    public static String trimTrailingCharacter(String str, char trailingCharacter) {
        if (!hasLength(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        while (sb.length() > 0 && sb.charAt(sb.length() - 1) == trailingCharacter) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }


    /**
     * Test if the given String starts with the specified prefix,
     * ignoring upper/lower case.
     *
     * @param str    the String to check
     * @param prefix the prefix to look for
     * @see String#startsWith
     */
    public static boolean startsWithIgnoreCase(String str, String prefix) {
        if (str == null || prefix == null) {
            return false;
        }
        if (str.startsWith(prefix)) {
            return true;
        }
        if (str.length() < prefix.length()) {
            return false;
        }
        String lcStr = str.substring(0, prefix.length()).toLowerCase();
        String lcPrefix = prefix.toLowerCase();
        return lcStr.equals(lcPrefix);
    }

    /**
     * Test if the given String ends with the specified suffix,
     * ignoring upper/lower case.
     *
     * @param str    the String to check
     * @param suffix the suffix to look for
     * @see String#endsWith
     */
    public static boolean endsWithIgnoreCase(String str, String suffix) {
        if (str == null || suffix == null) {
            return false;
        }
        if (str.endsWith(suffix)) {
            return true;
        }
        if (str.length() < suffix.length()) {
            return false;
        }

        String lcStr = str.substring(str.length() - suffix.length()).toLowerCase();
        String lcSuffix = suffix.toLowerCase();
        return lcStr.equals(lcSuffix);
    }

    /**
     * Test whether the given string matches the given substring
     * at the given index.
     *
     * @param str       the original string (or StringBuilder)
     * @param index     the index in the original string to start matching against
     * @param substring the substring to match at the given index
     */
    public static boolean substringMatch(CharSequence str, int index, CharSequence substring) {
        for (int j = 0; j < substring.length(); j++) {
            int i = index + j;
            if (i >= str.length() || str.charAt(i) != substring.charAt(j)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Count the occurrences of the substring in string s.
     *
     * @param str string to search in. Return 0 if this is null.
     * @param sub string to search for. Return 0 if this is null.
     */
    public static int countOccurrencesOf(String str, String sub) {
        if (str == null || sub == null || str.length() == 0 || sub.length() == 0) {
            return 0;
        }
        int count = 0;
        int pos = 0;
        int idx;
        while ((idx = str.indexOf(sub, pos)) != -1) {
            ++count;
            pos = idx + sub.length();
        }
        return count;
    }

    /**
     * Replace all occurrences of a substring within a string with
     * another string.
     *
     * @param inString   String to examine
     * @param oldPattern String to replace
     * @param newPattern String to insert
     * @return a String with the replacements
     */
    public static String replace(String inString, String oldPattern, String newPattern) {
        if (!hasLength(inString) || !hasLength(oldPattern) || newPattern == null) {
            return inString;
        }
        StringBuilder sb = new StringBuilder();
        int pos = 0; // our position in the old string
        int index = inString.indexOf(oldPattern);
        // the index of an occurrence we've found, or -1
        int patLen = oldPattern.length();
        while (index >= 0) {
            sb.append(inString.substring(pos, index));
            sb.append(newPattern);
            pos = index + patLen;
            index = inString.indexOf(oldPattern, pos);
        }
        sb.append(inString.substring(pos));
        // remember to append any characters to the right of a match
        return sb.toString();
    }

    /**
     * Delete all occurrences of the given substring.
     *
     * @param inString the original String
     * @param pattern  the pattern to delete all occurrences of
     * @return the resulting String
     */
    public static String delete(String inString, String pattern) {
        return replace(inString, pattern, "");
    }

    /**
     * Delete any character in a given String.
     *
     * @param inString      the original String
     * @param charsToDelete a set of characters to delete.
     *                      E.g. "az\n" will delete 'a's, 'z's and new lines.
     * @return the resulting String
     */
    public static String deleteAny(String inString, String charsToDelete) {
        if (!hasLength(inString) || !hasLength(charsToDelete)) {
            return inString;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < inString.length(); i++) {
            char c = inString.charAt(i);
            if (charsToDelete.indexOf(c) == -1) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 过滤html，将一些字符进行转义
     *
     * @param html 网页源码
     * @return 返回过滤后的html
     */
    public static String formatHTMLIn(String html) {
        html = html.replaceAll("&", "&amp;");
        html = html.replaceAll("<", "&lt;");
        html = html.replaceAll(">", "&gt;");
        html = html.replaceAll("\"", "&quot;");
        return html;
    }

    /**
     * 解析html
     *
     * @param html 网页源码
     * @return 返回解析过的html
     */
    public static String formatHTMLOut(String html) {
        html = html.replaceAll("&amp;", "&");
        html = html.replaceAll("&lt;", "<");
        html = html.replaceAll("&gt;", ">");
        html = html.replaceAll("&quot;", "\"");
        return html;
    }

    /**
     * 解码，将字符串解码成UTF-8编码
     *
     * @param str 需要解码的字符串
     * @return 返回解码后的字符串
     */
    public static String decodeStringByUTF8(String str) {
        if (isBlank(str))
            return "";
        try {
            return URLDecoder.decode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
        }
        return "";
    }

    /**
     * 转码，将字符串转码成UTF-8编码
     *
     * @param str 需要转码的字符串
     * @return 返回转码后的字符串
     */
    public static String encodeStringByUTF8(String str) {
        if (isBlank(str))
            return "";
        try {
            return URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
        }
        return "";
    }

    /**
     * 程序内部字符串转码，将ISO-8859-1转换成utf-8
     *
     * @param str 需要转码的字符串
     * @return 返回utf8编码字符串
     */
    public static String isoToUTF8(String str) {
        if (isBlank(str))
            return "";
        try {
            return new String(str.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        return "";
    }

    /**
     * 程序内部字符串转码，将utf-8转换成ISO-8859-1
     *
     * @param str 需要转码的字符串
     * @return 返回转码后的字符串
     */
    public static String utf8ToISO(String str) {
        if (isBlank(str))
            return "";
        try {
            return new String(str.getBytes("UTF-8"), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
        }
        return "";
    }

    /**
     * 程序内部字符串转码，将utf-8转换成gb2312
     *
     * @param str 需要转码的字符串
     * @return 返回转码后的字符串
     */
    public static String utf8Togb2312(String str) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            switch (c) {
                case '+':
                    sb.append(' ');
                    break;
                case '%':
                    try {
                        sb.append((char) Integer.parseInt(str.substring(i + 1, i + 3), 16));
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException();
                    }
                    i += 2;
                    break;
                default:
                    sb.append(c);
                    break;
            }
        }
        // Undo conversion to external encoding
        String result = sb.toString();
        String res = null;
        try {
            byte[] inputBytes = result.getBytes("8859_1");
            res = new String(inputBytes, "UTF-8");
        } catch (Exception e) {
        }
        return res;
    }

    /**
     * 判断字符串是否是布尔类型
     *
     * @param str 字符串
     * @return 如果是数字类型，则返回true，否则返回false
     */
    public static boolean isBoolean(String str) {
        if (isBlank(str))
            return false;
        try {
            Boolean.parseBoolean(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是布尔类型
     *
     * @param str 字符串
     * @return 如果是数字类型，则返回true，否则返回false
     */
    public static boolean isByte(String str) {
        if (isBlank(str))
            return false;
        try {
            Byte.parseByte(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是数字类型
     *
     * @param str 字符串
     * @return 如果是数字类型，则返回true，否则返回false
     */
    public static boolean isInteger(String str) {
        if (isBlank(str))
            return false;
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是单精度数字类型
     *
     * @param str 字符串
     * @return 如果是数字类型，则返回true，否则返回false
     */
    public static boolean isFloat(String str) {
        if (isBlank(str))
            return false;
        try {
            Float.parseFloat(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是双精度数字类型
     *
     * @param str 字符串
     * @return 如果是数字类型，则返回true，否则返回false
     */
    public static boolean isDouble(String str) {
        if (isBlank(str))
            return false;
        try {
            Double.parseDouble(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 判断对象是否是数字类型
     *
     * @param str 对象
     * @return 如果是数字类型，则返回true，否则返回false
     */
    public static boolean isInteger(Object str) {
        String temp = str + "";
        if (isBlank(str))
            return false;
        try {
            Integer.parseInt(temp);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 当传入的值为NULL是将其转化为String
     *
     * @param str 字符串
     * @return 返回字符串
     */
    public static String null2String(String str) {
        if (str == null || str.equals("") || str.trim().length() == 0) {
            return str = "";
        }
        return str;
    }

    /**
     * 将String型转换成Int型并判断String是否是NULL
     *
     * @param str 字符串
     * @return 返回数字
     */
    public static int string2Int(String str) {
        int valueInt = 0;
        if (isBlank(str)) {
            valueInt = Integer.parseInt(str);
        }
        return valueInt;
    }

    /**
     * 变量形态转换 int型转为String型
     *
     * @param comment 整型数字
     * @return 返回字符串
     */
    public static String int2String(int comment) {
        String srt = "";
        srt = Integer.toString(comment);
        return srt;
    }

    /**
     * 判断是否是大于0的参数
     *
     * @param str 字符串参数
     * @return 如果是大于，则返回true，否则返回false
     */
    public static boolean isMaxZeroInteger(Object str) {
        if (isBlank(str))
            return false;
        try {
            int temp = Integer.parseInt(str.toString());
            return temp > 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断长整型
     *
     * @param str 字符串
     * @return 如果是长整型，则返回true，否则返回false
     */
    public static boolean isLong(String str) {
        if (isBlank(str))
            return false;
        try {
            Long.parseLong(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断长整型数组
     *
     * @param str String数组
     * @return 如果是长整型，则返回true，否则返回false
     */
    public static boolean isLongs(String str[]) {
        try {
            for (int i = 0; i < str.length; i++)
                Long.parseLong(str[i]);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断数字数组
     *
     * @param str String数组
     * @return 如果是数字，则返回true，否则返回false
     */
    public static boolean isIntegers(String str[]) {
        try {
            for (int i = 0; i < str.length; i++)
                Integer.parseInt(str[i]);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断数字数组
     *
     * @param str String数组
     * @return 如果是数组，则返回true，否则返回false
     */
    public static boolean isDoubles(String str[]) {
        try {
            for (int i = 0; i < str.length; i++)
                Double.parseDouble(str[i]);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断是否是中文字符
     *
     * @param chChar 字符
     * @return 如果中文字符，则返回true，否则返回false
     */
    public static boolean isChinese(char chChar) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(chChar);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }
}
