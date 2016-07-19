package com.wf2311.commons.security;

import java.security.NoSuchAlgorithmException;

/**
 * @author wf2311
 * @date 2016/01/22 15:28
 */
public class SecureFactory {
    public static BasicCodec getCodec(SecureType type, String key) throws NoSuchAlgorithmException {
        BasicCodec codec = null;
        switch (type) {
            case MD5:
                codec = new MD5Codec();
                break;
            case SHA:
                codec = new SHACodec();
                break;
            case DES:
                if (key != null && !"".equals(key)) {
                    codec = new DESCodec(key);
                } else {
                    codec = new DESCodec();
                }
                break;
            case AES:
                if (key != null && !"".equals(key)) {
                    codec = new AESCodec(key);
                } else {
                    codec = new AESCodec();
                }
                break;
            case RSA_PRIVATE:
                codec = new RSAForPrivateCodec();
                break;
            case RSA_PUBLIC:
                codec = new RSAForPublicCodec(key);
                break;
            default:
                codec = new NoSecureCodec();
        }
        return codec;
    }
}