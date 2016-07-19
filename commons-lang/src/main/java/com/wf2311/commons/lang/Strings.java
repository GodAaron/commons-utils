package com.wf2311.commons.lang;

/**
 * @author wf2311
 * @date 2016/01/08 16:18
 */
public class Strings {
    public static String toString(Object... strings) {
        String s = "[";
        for (int i = 0; i < strings.length; i++) {
            s += strings[i];
            if (i < strings.length - 1) {
                s += " , ";
            }
        }
        s += "]";
        return s;
    }

    public static void print(String... strings) {
        System.out.println(toString(strings));
    }

    public static void print(Object ... objects){
        System.out.println(toString(objects));
    }
}
