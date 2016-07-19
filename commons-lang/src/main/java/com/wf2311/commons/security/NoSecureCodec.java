package com.wf2311.commons.security;

/**
 * 不适用任何安全加密
 *
 * @author wf2311
 * @date 2016/01/22 15:18
 */
public class NoSecureCodec extends BasicCodec {

    @Override
    public byte[] encrypt(byte[] data) throws Exception {
        return data;
    }

    @Override
    public byte[] decrypt(byte[] data) throws Exception {
        return data;
    }

}
