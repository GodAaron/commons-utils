package com.wf2311.commons.math;

import java.util.UUID;

/**
 * @author: wf2311
 * @date: 2015/9/19 9:30
 */
public class UUIDUtils {

    /**
     * 将uuid缩短为8位字符
     *
     * @param uuid uuid字符串
     * @return 8位由 0-9a-zA-Z$_ 组成的字符串
     */
    public static String toShortUuid(String uuid) {
        StringBuffer shortBuffer = new StringBuffer();
        uuid = uuid.replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(MathConsts.chars64[x % 64]);
        }
        return shortBuffer.toString();
    }

    /**
     * 得到一个8位字符的短uuid
     *
     * @return 8位由 0-9a-zA-Z$_ 组成的字符串
     */
    public static String getShortUuid() {
        return toShortUuid(UUID.randomUUID().toString());
    }
}
