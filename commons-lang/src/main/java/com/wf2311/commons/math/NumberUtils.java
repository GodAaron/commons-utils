package com.wf2311.commons.math;


import com.wf2311.commons.exception.WfException;
import com.wf2311.commons.lang.ArrayUtils;
import com.wf2311.commons.lang.ChineseConsts;

/**
 * @author wf2311
 * @date 2016/01/13 10:35
 */
public class NumberUtils {
    /**
     * 二进制转八进制
     *
     * @param s 二进制字符串
     * @return 八进制字符串
     */
    public static String i2To8(String s) {
        return Long.toOctalString(Long.parseLong(s, 2));
    }

    /**
     * 二进制转十进制
     *
     * @param s 二进制字符串
     * @return 十进制长整型数字
     */
    public static long i2To10(String s) {
        return Long.parseLong(s, 2);
    }

    /**
     * 二进制转十六进制
     *
     * @param s 二进制字符串
     * @return 十六进制字符串
     */
    public static String i2To16(String s) {
        return Long.toHexString(Long.parseLong(s, 2));
    }

    /**
     * 二进制转六十四进制
     *
     * @param s 二进制字符串
     * @return 六十四进制字符串
     */
    public static String i2To64(String s) {
        int j = s.length() % 6;
        if (j != 0) {
            for (int i = 0; i < 6 - j; i++) {
                s = "0" + s;
            }
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < s.length() / 6; i++) {
            String str = s.substring(i * 6, i * 6 + 6);
            long x = i2To10(str);
            if (x > 63 || x < 0) {
                throw new WfException("进制转换出错");
            }
            buffer.append(MathConsts.chars64[(int) x]);
        }

        return buffer.toString();
    }

    /**
     * 八进制转二进制
     *
     * @param s 八进制字符串
     * @return 二进制字符串
     */
    public static String i8To2(String s) {
        return Long.toBinaryString(Long.parseLong(s, 8));
    }

    /**
     * 八进制转十进制
     *
     * @param s 八进制字符串
     * @return 十进制长整型数字
     */
    public static long i8To10(String s) {
        return Long.parseLong(s, 8);
    }


    /**
     * 八进制转十六进制
     *
     * @param s 八进制字符串
     * @return 十六进制字符串
     */
    public static String i8To16(String s) {
        return Long.toHexString(Long.parseLong(s, 8));
    }

    /**
     * 八进制转六十四进制
     *
     * @param s 八进制字符串
     * @return 六十四进制字符串
     */
    public static String i8To64(String s) {
        int j = s.length() % 2;
        if (j != 0) {
            for (int i = 0; i < 2 - j; i++) {
                s = "0" + s;
            }
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < s.length() / 2; i++) {
            String str = s.substring(i * 2, i * 2 + 2);
            long x = i8To10(str);
            if (x > 63 || x < 0) {
                throw new WfException("进制转换出错");
            }
            buffer.append(MathConsts.chars64[(int) x]);
        }
        return buffer.toString();
    }

    /**
     * 十进制转二进制
     *
     * @param i 十进制长整型数字
     * @return 二进制字符串
     */
    public static String i10To2(long i) {
        return Long.toBinaryString(i);
    }

    /**
     * 十进制转二进制
     *
     * @param i 十进制长整型数字
     * @return 二进制字符串
     */
    public static String i10To8(long i) {
        return Long.toOctalString(i);
    }


    /**
     * 十进制转十六进制
     *
     * @param i 十进制长整型数字
     * @return 十六进制字符串
     */
    public static String i10To16(long i) {
        return Long.toHexString(i);
    }

    /**
     * 十进制转六十四进制
     *
     * @param i 十进制长整型数字
     * @return 六十四进制字符串
     */
    public static String i10To64(long i) {
        return i8To64(i10To8(i));
    }

    /**
     * 十六进制转二进制
     *
     * @param s 十六进制字符串
     * @return 二进制字符串
     */
    public static String i16To2(String s) {
        return Long.toBinaryString(Long.parseLong(s, 16));
    }

    /**
     * 十六进制转八进制
     *
     * @param s 十六进制字符串
     * @return 八进制字符串
     */
    public static String i16To8(String s) {
        return Long.toOctalString(Long.parseLong(s, 16));
    }

    /**
     * 十六进制转十进制
     *
     * @param s 十六进制字符串
     * @return 十进制长整型数字
     */
    public static long i16To10(String s) {
        return Long.valueOf(s, 16);
    }

    /**
     * 十六进制转六十四进制
     *
     * @param s 十六进制字符串
     * @return 六十四进制字符串
     */
    public static String i16To64(String s) {
        return i10To64(i16To10(s));
    }


    /**
     * 六十四进制字符转二进制
     *
     * @param c 六十四进制字符
     * @return 二进制字符串
     */
    public static String i64To2(char c) {
        return i10To2(i64To10(c));
    }

    /**
     * 六十四进制字符转八进制
     *
     * @param c 六十四进制字符
     * @return 八进制字符串
     */
    public static String i64To8(char c) {
        return i10To8(i64To10(c));
    }

    /**
     * 六十四进制字符转十进制
     *
     * @param c 六十四进制字符
     * @return 十进制长整型数字
     */
    public static long i64To10(char c) {
        int i = ArrayUtils.indexOf(MathConsts.chars64, String.valueOf(c));
        if (i < 0) {
            throw new WfException("参数错误：不是本系统中规定的64进制字符");
        }
        return i;
    }

    /**
     * 六十四进制转二进制
     *
     * @param s 六十四进制字符串
     * @return 十进制长整型数字
     */
    public static String i64To2(String s) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            if (i > 0) {
                buffer.append(preFillToSize(i64To2(s.charAt(i)), 6, '0'));
            } else {
                buffer.append(i64To2(s.charAt(i)));
            }
        }
        return buffer.toString();
    }

    /**
     * 六十四进制转八进制
     *
     * @param s 六十四进制字符串
     * @return 八进制字符串
     */
    public static String i64To8(String s) {
        return i2To8(i64To2(s));
    }

    /**
     * 六十四进制转十进制
     *
     * @param s 六十四进制字符串
     * @return 十进制长整型数字
     */
    public static long i64To10(String s) {
        return i2To10(i64To2(s));
    }


    /**
     * 六十四进制转十六进制
     *
     * @param s 六十四进制字符串
     * @return 十六进制字符串
     */
    public static String i64To16(String s) {
        return i2To16(i64To2(s));
    }

    /**
     * 填充指定字符使之原字符串变为指定长度
     *
     * @param s      字符串
     * @param length 指定的长度
     * @param fill   要填充的字符
     * @return 填充完后新的字符串
     */
    private static String preFillToSize(String s, int length, char fill) {
        int c = length - s.length();
        if (c == 0) {
            return s;
        }
        if (c < 0) {
            throw new WfException("字符串长度大于指定长度");
        } else {
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < c; i++) {
                buffer.append(fill);
            }
            buffer.append(s);
            return buffer.toString();
        }
    }

    /**
     * 如果整数 num &lt 1，则 num=1
     *
     * @param num
     * @return
     */
    public static Integer integer1(Integer num) {
        return integerN(num, 1);
    }

    /**
     * 如果整数 num &lt 0，则 num=0
     *
     * @param num
     * @return
     */
    public static Integer integer0(Integer num) {
        return integerN(num, 0);
    }

    /**
     * 如果整数 num &lt n,则 num = n
     *
     * @param num
     * @param n   待比较数
     * @return
     */
    public static Integer integerN(Integer num, int n) {
        if (num == null || num < n) {
            num = n;
        }
        return num;
    }

    /**
     * 如果整数 num &lt 1,则 num = 1
     *
     * @param num
     * @return
     */
    public static int int1(int num) {
        return intN(num, 1);
    }

    /**
     * 如果整数 num &lt 0,则 num = 0
     *
     * @param num
     * @return
     */
    public static int int0(int num) {
        return intN(num, 0);
    }

    /**
     * 如果整数 num &lt n,则 num = n
     *
     * @param num
     * @param n   待比较数
     * @return
     */
    public static int intN(int num, int n) {
        if (num < n) {
            num = n;
        }
        return num;
    }

    /**
     * @param s
     * @return
     */
    public static String numberToChinese(String s) {
        s = s.replaceAll(" ", "").replaceAll(",", "");
        String[] array = s.split("\\.");
        if (array.length > 2) {
            throw new WfException("参数格式错误");
        }
        String main = array[0], deci = null;
        if (array.length == 2) {
            deci = array[1];
        }
        boolean sign = true;
        if (main.startsWith("-")) {
            sign = false;
            main = main.substring(1);
        }
        int l = main.length() / 4 + (main.length() % 4 != 0 ? 1 : 0);
        String[] mains = new String[l];
        String rs = "";
        for (int i = 0; i < mains.length; i++) {
            int j = main.length() - 4 * i;
            mains[i] = main.substring((j - 4) > 0 ? (j - 4) : 0, j);
        }
        for (int i = 0; i < mains.length; i++) {
            String a = numToCN(mains[i]);
            if (a.endsWith(ChineseConsts.NUM_C1[0])) {
                a = a.substring(0, a.length() - 1);
                rs = a + ChineseConsts.NUM_C3[i] + ChineseConsts.NUM_C1[0] + rs;
            } else {
                rs = a + ChineseConsts.NUM_C3[i] + rs;
            }
        }
        if (!sign) {
            rs = ChineseConsts.NUM_SIGN[1] + rs;
        }

        while (rs.contains(ChineseConsts.NUM_C1[0] + ChineseConsts.NUM_C1[0])) {
            rs = rs.replaceAll(ChineseConsts.NUM_C1[0] + ChineseConsts.NUM_C1[0], ChineseConsts.NUM_C1[0]);
        }
        while (rs.endsWith(ChineseConsts.NUM_C1[0])) {
            rs = rs.substring(0, rs.length() - 1);
        }

        if (deci != null) {
            String de = "";
            for (int i = 0; i < deci.length(); i++) {
                int j = Integer.parseInt(String.valueOf(deci.charAt(i)));
                de += ChineseConsts.NUM_C1[j];
            }
            rs += ChineseConsts.NUM_SIGN[2] + de;
        }
        return rs;
    }

    private static String numToCN(String num) {
        if (num.length() > 4) {
            throw new WfException("参数格式错误");
        }
        String s = "";
        for (int i = 0; i < num.length(); i++) {
            int j = Integer.parseInt(String.valueOf(num.charAt(i)));
            if (j != 0) {
                s += ChineseConsts.NUM_C1[j] + ChineseConsts.NUM_C2[num.length() - i - 1];
            } else {
                s += ChineseConsts.NUM_C1[0];
            }
        }
        while (s.contains(ChineseConsts.NUM_C1[0] + ChineseConsts.NUM_C1[0])) {
            s = s.replaceAll(ChineseConsts.NUM_C1[0] + ChineseConsts.NUM_C1[0], ChineseConsts.NUM_C1[0]);
        }
        return s;
    }
}
