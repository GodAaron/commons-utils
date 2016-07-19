package com.wf2311.commons.lang;

/**
 * @author wf2311
 * @date 2016/01/11 16:58
 */
public class Classes {
    /**
     * 将java的基本类型转为对应的包装类型
     *
     * @param c 类型
     * @return
     */
    public static Class baseToPackage(Class c) {
        int i = 0;
        for (Class cl : Consts.BASE_TYPE) {
            if (c.equals(cl)) {
                return Consts.PACKAGE_TYPE[i];
            }
            i++;
        }
        return c;
    }

    /**
     * 将java的基本类型转为对应的包装类型
     *
     * @param cls 类型数组
     * @return
     */
    public static Class[] baseToPackage(Class[] cls) {
        for (int i = 0; i < cls.length; i++) {
            cls[i] = baseToPackage(cls[i]);
        }
        return cls;
    }
}
