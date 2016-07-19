package com.wf2311.commons.security;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 编解码基类
 *
 * @author wf2311
 * @date 2016/01/22 15:09
 */
public abstract class BasicCodec {
    private Base64Utils base64Utils;

    /**
     * 对称加密的密钥，经过base64编程的密钥字符串
     */
    protected String secretKey;

    /**
     * 非对称加密的公钥，经过base64编码的公钥字符串
     */
    protected String publicKey;

    /**
     * 非对称加密的私钥，经过base64编码的私钥字符串
     */
    protected String privateKey;

    public BasicCodec() {
        base64Utils = Base64Utils.getInstance();
    }

    public static final Charset charset = Charset.forName("UTF-8");

    /**
     * 加密
     *
     * @param data 需加密的内容
     * @return
     * @throws Exception
     */
    public abstract byte[] encrypt(byte[] data) throws Exception;

    /**
     * 解密
     *
     * @param data 需解密的内容
     * @return
     * @throws Exception
     */
    public abstract byte[] decrypt(byte[] data) throws Exception;

    /**
     * base64 编码
     *
     * @param data
     * @return
     */
    protected String encoder(byte[] data) {
        return base64Utils.encoder(data);
    }

    /**
     * base64解码
     *
     * @param data
     * @return
     * @throws IOException
     */
    protected byte[] decoder(String data) throws IOException {
        return base64Utils.decoder(data);
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

}