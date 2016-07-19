package com.wf2311.commons.security;

import java.security.MessageDigest;

/**
 * SHA 单向加密
 *
 * @author wf2311
 * @date 2016/01/22 15:25
 */
public class SHACodec extends BasicCodec {
    private static final String ALGORITHM = "SHA";

    @Override
    public byte[] encrypt(byte[] data) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
        return messageDigest.digest(data);
    }

    @Override
    public byte[] decrypt(byte[] data) throws Exception {
        return null;
    }

    /**
     * 返回SHA单向加密后的十六进制字符串
     *
     * @param data
     * @return
     * @throws Exception
     */
    public String getEncryptForHex(byte[] data) throws Exception {
        byte[] digestData = encrypt(data);
        StringBuffer hex = new StringBuffer();
        for (int i = 0; i < digestData.length; i++) {
            int h = ((int) digestData[i]) & 0XFF;
            if (h < 16) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(h));
        }

        return hex.toString();
    }
}