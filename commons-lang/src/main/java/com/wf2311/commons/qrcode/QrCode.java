package com.wf2311.commons.qrcode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import org.apache.commons.lang.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Hashtable;
import java.util.UUID;

/**
 * @author wf2311
 * @time 2016/07/19 11:03.
 */
public class QrCode {
    private static final int BLACK = 0xff000000;
    private static final int WHITE = 0xFFFFFFFF;

    /**
     * 生成QRCode二维码
     */
    public File encode(Config config) {
        try {
            File isFile = new File(config.getPath());
            if (!isFile.exists()) {
                isFile.mkdirs();
            }
            String fileName;
            if (StringUtils.isBlank(config.getName())) {
                fileName = UUID.randomUUID().toString();
            } else {
                fileName = config.getName();
            }
            fileName = fileName + "." + config.getFormat();
            File file = new File(config.getPath(), fileName);
            BufferedImage bufferedImage = encodeToBufferedImage(config);
            ImageIO.write(bufferedImage, config.getFormat(), file);
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public OutputStream encodeToOutputStream(Config profile) throws IOException, WriterException {
        OutputStream outputStream = new ByteArrayOutputStream();
        BufferedImage bufferedImage = encodeToBufferedImage(profile);
        ImageIO.write(bufferedImage, profile.getFormat(), outputStream);
        return outputStream;
    }

    private BufferedImage encodeToBufferedImage(Config profile) throws WriterException, IOException {
        BitMatrix bitMatrix = encodeToBitMatrix(profile);
        return getBufferedImage(bitMatrix, profile.isLogoFlag(), profile.getLogoPath());
    }


    private BitMatrix encodeToBitMatrix(Config profile) throws WriterException {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
            /*设置纠错级别(L 7%~M 15%~Q 25%~H 30%),纠错级别越高存储的信息越少*/
        hints.put(EncodeHintType.ERROR_CORRECTION, profile.getErrorCorrectionLevel());
            /*设置编码格式*/
        hints.put(EncodeHintType.CHARACTER_SET, profile.getCharacterSet());
            /*设置边缘空白*/
        hints.put(EncodeHintType.MARGIN, profile.getMargin());

        BitMatrix bitMatrix = new MultiFormatWriter().encode(profile.getContent(), BarcodeFormat.QR_CODE, profile.getWidth(), profile.getHeight(), hints);
        return bitMatrix;
    }

    public BufferedImage getBufferedImage(BitMatrix bitMatrix, boolean isLogo, String logoPath) throws IOException {
        BufferedImage bi = toBufferedImageContents(bitMatrix);
        if (isLogo && logoPath != null && !logoPath.isEmpty() && new File(logoPath).exists()) {
            int width_4 = bitMatrix.getWidth() / 4;
            int width_8 = width_4 / 2;
            int height_4 = bitMatrix.getHeight() / 4;
            int height_8 = height_4 / 2;
            /*返回由指定矩形区域定义的子图像*/
            BufferedImage bi2 = bi.getSubimage(width_4 + width_8, height_4 + height_8, width_4, height_4);
            /*获取一个绘图工具笔*/
            Graphics2D g2 = bi2.createGraphics();
            /*读取logo图片信息*/
            Image img = ImageIO.read(new File(logoPath));//实例化一个Image对象。
            /*当前图片的宽与高*/
            int currentImgWidth = img.getWidth(null);
            int currentImgHeight = img.getHeight(null);
            /*处理图片的宽与高*/
            int resultImgWidth = 0;
            int resultImgHeight = 0;
            if (currentImgWidth != width_4) {
                resultImgWidth = width_4;
            }
            if (currentImgHeight != width_4) {
                resultImgHeight = width_4;
            }
            /*绘制图片*/
            g2.drawImage(img, 0, 0, resultImgWidth, resultImgHeight, null);
            g2.dispose();
            bi.flush();
        }
        return bi;
    }


    /**
     * 生成二维码图片
     *
     * @param bitMatrix
     * @param format    图片格式
     * @param file      生成二维码图片位置
     * @param isLogo    是否要加logo图
     * @param logoPath  logo图片地址
     * @throws IOException
     */
//    public void writeToFile(BitMatrix bitMatrix, String format, File file, boolean isLogo, String logoPath) throws IOException {
//        BufferedImage bi = toBufferedImageContents(bitMatrix);
//        if (isLogo && logoPath != null && !logoPath.isEmpty() && new File(logoPath).exists()) {
//            int width_4 = bitMatrix.getWidth() / 4;
//            int width_8 = width_4 / 2;
//            int height_4 = bitMatrix.getHeight() / 4;
//            int height_8 = height_4 / 2;
//            /*返回由指定矩形区域定义的子图像*/
//            BufferedImage bi2 = bi.getSubimage(width_4 + width_8, height_4 + height_8, width_4, height_4);
//            /*获取一个绘图工具笔*/
//            Graphics2D g2 = bi2.createGraphics();
//            /*读取logo图片信息*/
//            Image img = ImageIO.read(new File(logoPath));//实例化一个Image对象。
//            /*当前图片的宽与高*/
//            int currentImgWidth = img.getWidth(null);
//            int currentImgHeight = img.getHeight(null);
//            /*处理图片的宽与高*/
//            int resultImgWidth = 0;
//            int resultImgHeight = 0;
//            if (currentImgWidth != width_4) {
//                resultImgWidth = width_4;
//            }
//            if (currentImgHeight != width_4) {
//                resultImgHeight = width_4;
//            }
//            /*绘制图片*/
//            g2.drawImage(img, 0, 0, resultImgWidth, resultImgHeight, null);
//            g2.dispose();
//            bi.flush();
//        }
//        ImageIO.write(bi, format, file);
//    }

    /**
     * 生成二维码内容
     *
     * @param bitMatrix
     * @return
     */
    public BufferedImage toBufferedImageContents(BitMatrix bitMatrix) {
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) == true ? BLACK : WHITE);
            }
        }
        return image;
    }

    public String decode(InputStream inputStream) throws IOException, NotFoundException {
        return decode(ImageIO.read(inputStream));
    }

    public String decode(BufferedImage image) throws NotFoundException {
        /*判断是否是图片*/
        if (image == null) {
            System.out.println("Could not decode image");
        }
            /*解析二维码用到的辅助类*/
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Hashtable<DecodeHintType, Object> hints = new Hashtable<>();
            /*解码设置编码方式为：UTF-8*/
        hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");

        Result result = new MultiFormatReader().decode(bitmap, hints);
        String resultStr = result.getText();
        return resultStr;
    }


    /**
     * 解析二维码
     *
     * @param path 图片的绝对路径
     */
    public String decode(String path) throws IOException, NotFoundException {
        if (path == null || path.equals("")) {
            System.out.println("文件路径不能为空!");
        }
        File file = new File(path);
        BufferedImage image = ImageIO.read(file);
            /*判断是否是图片*/
        if (image != null) {
            return decode(image);
        }
        return null;
    }
}
