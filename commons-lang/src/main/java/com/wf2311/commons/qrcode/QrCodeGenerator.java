package com.wf2311.commons.qrcode;

import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author wf2311
 * @time 2016/07/19 10:59.
 */
public class QrCodeGenerator {
    private Config config;

    private QrCodeGenerator() {

    }


    public static QrCodeGenerator create() {
        QrCodeGenerator qrCodeGenerator = new QrCodeGenerator();
        qrCodeGenerator.config = new Config();
        qrCodeGenerator.characterSet("UTF-8")
                .errorCorrectionLevel(ErrorCorrectionLevel.H)
                .logoFlag(false)
                .format("png")
                .margin(0)
                .width(300)
                .height(300)
                .path(System.getProperty("java.io.tmpdir"));
        return qrCodeGenerator;
    }

    /**
     * 二维码内容
     */
    public QrCodeGenerator content(String content) {
        this.config.setContent(content);
        return this;
    }

    /**
     * 图片的宽度
     */
    public QrCodeGenerator width(int width) {
        this.config.setWidth(width);
        return this;
    }

    /**
     * 图片的高度
     */
    public QrCodeGenerator height(int height) {
        this.config.setHeight(height);
        return this;
    }

    /**
     * 生成图片的地址（不包含图片名称）
     *
     * @param path
     * @return
     */
    public QrCodeGenerator path(String path) {
        this.config.setPath(path);
        return this;
    }

    /**
     * logo文件路径
     */
    public QrCodeGenerator logoPath(String logoPath) {
        if (StringUtils.isNotBlank(logoPath)) {
            this.config.setLogFlag(true);
        }
        this.config.setLogoPath(logoPath);
        return this;
    }

    /**
     * 生成图片的格式，例如:jpg，png
     */
    public QrCodeGenerator format(String format) {
        this.config.setFormat(format);
        return this;
    }

    /**
     * 纠错级别
     */
    public QrCodeGenerator errorCorrectionLevel(ErrorCorrectionLevel errorCorrectionLevel) {
        this.config.setErrorCorrectionLevel(errorCorrectionLevel);
        return this;
    }

    /**
     * 编码格式
     */
    public QrCodeGenerator characterSet(String characterSet) {
        this.config.setCharacterSet(characterSet);
        return this;
    }

    /**
     * 二维码边缘留白
     */
    public QrCodeGenerator margin(int margin) {
        this.config.setWidth(margin);
        return this;
    }

    /**
     * 二维码边缘留白
     */
    public QrCodeGenerator name(String name) {
        this.config.setName(name);
        return this;
    }

    /**
     * 是否中间贴logo图，如果设置了该值为true，则必须设置logoPath
     *
     * @return
     */
    public QrCodeGenerator logoFlag(boolean logoFlag) {
        this.config.setLogoFlag(logoFlag);
        return this;
    }

    /**
     * 生成文件
     *
     * @return 返回二维码文件
     */
    public File generate() {
        QrCode qrCode = new QrCode();
        File file = qrCode.encode(config);
        return file;
    }

    public OutputStream generateOutputStream() throws IOException, WriterException {
        QrCode qrCode = new QrCode();
        OutputStream outputStream = qrCode.encodeToOutputStream(config);
        return outputStream;
    }
}
