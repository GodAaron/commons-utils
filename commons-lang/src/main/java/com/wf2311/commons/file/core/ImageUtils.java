package com.wf2311.commons.file.core;

import com.mortennobel.imagescaling.ResampleOp;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.apache.log4j.Logger;
import org.apache.velocity.texen.util.FileUtil;
import org.jsoup.helper.StringUtil;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.MemoryCacheImageInputStream;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.*;
import java.util.Iterator;

/**
 * 通用批量处理图片类
 *
 * @author wf2311
 * @time 2016/06/27 09:13.
 */
public class ImageUtils {
    private static final Logger logger = Logger.getLogger(ImageUtils.class);

    /**
     * 批处理图片
     *
     * @param directoryPath 批处理文件目录
     * @param sSize         源尺寸
     * @param tSize         处理后尺寸
     */
    public static void batchImageWidthHeight(String directoryPath, int sSize, int tSize) {
        // 设置批处理文件目录
        File dir = new File(directoryPath);
        // 获取目录文件列表
        File[] files = dir.listFiles();

        // 判断是否是空目录
        if (files == null) {
            return;
        } else {
            // 遍历文件列表
            for (int i = 0; i < files.length; i++) {
                // 如果是目录，进行递归
                if (files[i].isDirectory()) {
                    batchImageWidthHeight(files[i].getAbsolutePath(), sSize, tSize);
                } else {

                    BufferedImage srcImage;
                    try {
                        String filePath = files[i].getPath();
                        if (isPic(filePath)) {
                            // 读取图片文件
                            srcImage = ImageIO.read(files[i]);
                            int[] wh = getImageWidthHeight(srcImage, sSize, tSize);
                            resizeImage(files[i],
                                    FileUtils.appendFileName(files[i].getAbsolutePath(), "_" + sSize + "_" + tSize),
                                    wh[0], wh[1],
                                    FileUtils.getSuffix(files[i]));
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }


    /**
     * 缩放图像透明背景，捕捉到IOException异常后会抛出。
     *
     * @param root  文件路径
     * @param scale 规模
     * @throws IOException IO流异常
     */
    public static void changeImage(String root, double scale) throws IOException {
        File file = new File(root);
        File[] subFile = file.listFiles();
        for (int i = 0; i < subFile.length; i++) {
            //文件或目录名称
            String name = subFile[i].getName();
            //如果该文件是目录
            if (subFile[i].isDirectory()) {
                changeImage(subFile[i].getAbsolutePath() + "\\", scale);
            }
            String[] names = name.split("//.");
            if (StringUtil.isBlank(names[0]))
                break;
            scaleHyaline(root + subFile[i].getName(), root + subFile[i].getName(), scale, true);
        }
    }

    /**
     * 图像类型转换 GIF->JPG GIF->PNG PNG->JPG PNG->GIF(X)
     *
     * @param source 源图片路径<br>
     * @param result 输出图片路径
     */
    public static void convert(String source, String result) {
        try {
            File f = new File(source);
            f.canRead();
            f.canWrite();
            BufferedImage src = ImageIO.read(f);
            ImageIO.write(src, "JPG", new File(result));
        } catch (Exception e) {
            logger.error(e);
        }
    }

    /**
     * 创建图片,根据BufferedImage流
     *
     * @param path 图片路径 <br>
     * @param bi   图片对象流
     */
    public static void createImage(String path, BufferedImage bi) {
        try {
            ImageIO.write(bi, path.substring(path.lastIndexOf("."), path.length()).replace(".", ""), new File(path));
        } catch (IOException e) {
            logger.error(e);
        }
    }

    /**
     * 创建图片,根据字节数组
     *
     * @param path 图片路径 <br>
     * @param bt   图片字节数
     */
    public static void createImage(String path, byte[] bt) {
        // FileUtil.creaetFileByBytes(bt, path);
    }

    /**
     * 图像切割
     *
     * @param sourceImagePath 源图像地址<br>
     * @param descDir         输出图像地址<br>
     * @param width           目标切片宽度<br>
     * @param height          目标切片高度<br>
     */
    public static void cut(String sourceImagePath, String descDir, int width, int height) {
        try {
            Image img;
            ImageFilter cropFilter;
            // 读取源图像
            BufferedImage bi = ImageIO.read(new File(sourceImagePath));
            int srcWidth = bi.getHeight(); // 源图宽度
            int srcHeight = bi.getWidth(); // 源图高度
            if (srcWidth > width && srcHeight > height) {
                Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
                width = 200; // 切片宽度
                height = 150; // 切片高度
                int cols = 0; // 切片横向数量
                int rows = 0; // 切片纵向数量
                // 计算切片的横向和纵向数量
                if (srcWidth % width == 0) {
                    cols = srcWidth / width;
                } else {
                    cols = (int) Math.floor(srcWidth / width) + 1;
                }
                if (srcHeight % height == 0) {
                    rows = srcHeight / height;
                } else {
                    rows = (int) Math.floor(srcHeight / height) + 1;
                }
                // 循环建立切片
                // 改进的想法:是否可用多线程加快切割速度
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        // 四个参数分别为图像起点坐标和宽高
                        // 即: CropImageFilter(int x,int y,int width,int height)
                        cropFilter = new CropImageFilter(j * 200, i * 150, width, height);
                        img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
                        BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                        Graphics g = tag.getGraphics();
                        g.drawImage(img, 0, 0, null); // 绘制缩小后的图
                        g.dispose();
                        // 输出为文件
                        ImageIO.write(tag, "JPEG", new File(descDir + "pre_map_" + i + "_" + j + ".jpg"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 图片解密
     *
     * @param sourceImagePath 图片路径
     */
    public static void decodeImage(String sourceImagePath) {
        File file = new File(sourceImagePath);
        if (file.exists()) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length = -1;
            InputStream is;
            try {
                is = new FileInputStream(file);
                try {
                    while ((length = is.read(buffer)) != -1) {
                        baos.write(buffer, 0, length);
                    }
                    baos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                byte[] data = baos.toByteArray();
                data[0] = (byte) 0xff;

                OutputStream os = new FileOutputStream(file);
                try {
                    os.write(data);
                    os.flush();
                    os.close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                try {
                    is.close();
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    /**
     * 图片加密
     *
     * @param sourceImagePath 图片路径
     */
    public static void encodeImage(String sourceImagePath) {
        File file = new File(sourceImagePath);
        if (file.exists()) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length = -1;
            InputStream is;
            try {
                is = new FileInputStream(file);
                try {
                    while ((length = is.read(buffer)) != -1) {
                        baos.write(buffer, 0, length);
                    }
                    baos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                byte[] data = baos.toByteArray();
                data[0] = (byte) 0x00;

                OutputStream os = new FileOutputStream(file);
                try {
                    os.write(data);
                    os.flush();
                    os.close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                try {
                    is.close();
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    /**
     * 根据字节流获取图片类型,jpeg,jpg,png
     *
     * @param imageBytes 图像字节流<br>
     * @return 返回jpeg, jpg, png类型
     */
    public static String getImageType(byte[] imageBytes) {
        ByteArrayInputStream bais = null;
        MemoryCacheImageInputStream mcis = null;
        try {
            bais = new ByteArrayInputStream(imageBytes);
            mcis = new MemoryCacheImageInputStream(bais);
            Iterator<ImageReader> itr = ImageIO.getImageReaders(mcis);
            while (itr.hasNext()) {
                ImageReader reader = (ImageReader) itr.next();
                String imageName = reader.getClass().getSimpleName();
                if (imageName != null && ("JPEGImageReader".equalsIgnoreCase(imageName))) {
                    return "jpeg";
                } else if (imageName != null && ("JPGImageReader".equalsIgnoreCase(imageName))) {
                    return "jpg";
                } else if (imageName != null && ("pngImageReader".equalsIgnoreCase(imageName))) {
                    return "png";
                }
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    /**
     * 获取新图片宽度和高度 根据sourceWidth与targetWidth的比例，重新生成图片大小
     *
     * @param source      源图片
     * @param sourceWidth 源图片宽度
     * @param targetWidth 新图片宽度
     * @return 返回整型数组，[0]宽度，[1]高度
     */
    private static int[] getImageWidthHeight(BufferedImage source, int sourceWidth, int targetWidth) {
        double ts = (double) targetWidth / sourceWidth;
        int newWidth = (int) (source.getWidth() * ts);
        int newHeight = (int) (source.getHeight() * ts);
        if (newWidth < 3)
            newWidth = 3;
        if (newHeight < 3)
            newHeight = 3;
        int[] wh = new int[2];
        wh[0] = newWidth;
        wh[1] = newHeight;
        return wh;
    }

    /**
     * 获取图片宽度和高度
     *
     * @param image 源图片
     * @return 返回整型数组，[0]宽度 [1]高度
     */
    public static int[] getImageWidthHeight(File image) {
        BufferedImage bi;
        try {
            bi = ImageIO.read(image);

            int[] wh = new int[2];
            wh[0] = bi.getWidth();
            wh[1] = bi.getHeight();
            return wh;
        } catch (IOException e) {
            logger.error(e);
        }
        return null;
    }

    /**
     * 获取图片宽度和高度
     *
     * @param image 源图片路径
     * @return 返回整型数组，[0]宽度 [1]高度
     */
    public static int[] getImageWidthHeight(String image) {
        return getImageWidthHeight(new File(image));
    }

    /**
     * 彩色转为黑白
     *
     * @param sourceImagePath 源图片路径<br>
     * @param savePath        输出图片路径
     */
    public static void gray(String sourceImagePath, String savePath) {
        try {
            BufferedImage src = ImageIO.read(new File(sourceImagePath));
            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
            ColorConvertOp op = new ColorConvertOp(cs, null);
            src = op.filter(src, null);
            ImageIO.write(src, "JPEG", new File(savePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查文件是否是图片
     *
     * @param sourceImagePath 文件路劲 <br>
     * @return 如果是图片，则返回true，否则返回false
     */
    public static boolean isPic(String sourceImagePath) {
        // 图片后缀
        String picSfix = "jpg|png|gif";
        String[] temp = picSfix.split("\\|");
        if (sourceImagePath.indexOf(".") > 0) {
            String fileSfix = sourceImagePath.substring(sourceImagePath.indexOf("."), sourceImagePath.length()).replace(".", "");
            for (int i = 0; i < temp.length; i++) {
                if (fileSfix.equals(temp[i]))
                    return true;
            }
        }
        return false;
    }

    /**
     * 测试用主方法
     *
     * @param args 主方法形参
     * @throws IOException io异常
     */
    public static void main(String[] args) throws IOException {
        // String root =
        // "C:\\Documents and Settings\\Administrator\\桌面\\images\\";
        // changeImage(root, 0.5625);
//		ImageUtil.rotate("f:/b.jpg");
//		File file = new File("F:\\陶研所");
//		File[] subFile = file.listFiles();
//		for (int i = 0; i < subFile.length; i++) {
//			ImageUtil.createImage(subFile[i].getAbsolutePath(), ImageUtil.resize(subFile[i].getAbsolutePath(), 150, 100));
//		}
    }

    /**
     * 重新设置图片的宽度高度
     *
     * @param sourceImagePath 图像位置<br>
     * @param width           新的宽度<br>
     * @param height          新的高度<br>
     * @return 返回BufferedImage图像流
     */
    public static BufferedImage resize(String sourceImagePath, int width, int height) {

        BufferedImage inputBufImage;
        try {
            inputBufImage = ImageIO.read(new File(sourceImagePath));
            ResampleOp resampleOp = new ResampleOp(Math.min(width, inputBufImage.getWidth()), Math.min(height, inputBufImage.getHeight()));
            BufferedImage rescaledTomato = resampleOp.filter(inputBufImage, null);
            return rescaledTomato;
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return null;
    }

    /**
     * 获取新图片宽度和高度 根据sourceWidth与targetWidth的比例，重新生成图片大小
     *
     * @param sourceImagePath 源文件
     * @param savePath        输出路径
     * @param width           源图片宽度
     * @param height          新图片宽度
     * @param sufix           图片后缀
     * @return 如果获取成功，则返回true，否则返回false
     */
    public static boolean resizeImage(File sourceImagePath, String savePath, int width, int height, String sufix) {
        try {
            BufferedImage inputBufImage = ImageIO.read(sourceImagePath);
//            ResampleOp resampleOp = new ResampleOp(Math.min(width, inputBufImage.getWidth()), Math.min(height, inputBufImage.getHeight()));
            ResampleOp resampleOp = new ResampleOp(width, height);
            BufferedImage rescaledTomato = resampleOp.filter(inputBufImage, null);
            ImageIO.write(rescaledTomato, sufix, new File(savePath));
            return true;
        } catch (Exception e) {
            logger.error(e);
            return false;
        }

    }

    /**
     * 重置图片大小，输出字节流
     *
     * @param sourceImagePath 源图片路径<br>
     * @param targetW         新图片宽度<br>
     * @param targetH         新图片高度<br>
     * @param type            图片类型jpg,png,bmp,gif
     * @return 返回byte数组图片流
     */
    public static byte[] resizeImageForBytes(String sourceImagePath, int targetW, int targetH, String type) {
        try {
            BufferedImage image = resize(sourceImagePath, targetW, targetH);
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();

            // JPEGImageEncoder encoder =
            // JPEGCodec.createJPEGEncoder(outStream);
            // encoder.encode(image);
            ImageIO.write(image, type, outStream);
            outStream.flush();
            byte[] result = outStream.toByteArray();
            outStream.close();
            return result;
        } catch (Exception ex) {
            logger.error(ex);
            return null;
        }
    }

    /**
     * 获取新图片宽度和高度 根据sourceWidth与targetWidth的比例，重新生成图片大小
     *
     * @param sourceImageFile 源文件
     * @param savePath        输出路径
     * @param width           源图片宽度
     * @param height          新图片宽度
     * @param sufix           图片后缀
     * @return 如果是获取成功，则返回true，否则返回false
     */
    public static boolean resizeImageForEncode(File sourceImageFile, String savePath, Integer width, Integer height, String sufix) {
        try {

            BufferedImage inputBufImage = ImageIO.read(sourceImageFile);
            ResampleOp resampleOp = new ResampleOp(Math.min(width, inputBufImage.getWidth()), Math.min(height, inputBufImage.getHeight()));
            BufferedImage rescaledTomato = resampleOp.filter(inputBufImage, null);
            ImageIO.write(rescaledTomato, sufix, new File(savePath));
            return true;
        } catch (Exception e) {
            logger.error(e);
            return false;
        }

    }

    /***
     * 图片旋转
     *
     * @param sourceImagePath 图片文件路径
     * @param direction       旋转方向 0:顺时针90度 1逆时针90度
     */
    public static void rotate(String sourceImagePath, int direction) {
        File file = null;
        BufferedImage original = null;
        BufferedImage bufOut = null;
        int width, height;

        file = new File(sourceImagePath);
        try {
            original = ImageIO.read(file);
        } catch (Exception e) {
            return;
        }

        width = original.getWidth();
        height = original.getHeight();
        bufOut = new BufferedImage(width, height, original.getType());
        // AffineTransform atx = AffineTransform.getScaleInstance(scale, scale);
        AffineTransform atx = new AffineTransform();
        switch (direction) {
            case 0:
                atx.rotate(-Math.PI / 2, width / 2, height / 2);
                break;
            case 1:
                atx.rotate(Math.PI / 2, width / 2, height / 2);
                break;
        }
        AffineTransformOp atop = new AffineTransformOp(atx, AffineTransformOp.TYPE_BICUBIC);
        atop.filter(original, bufOut);
        bufOut = bufOut.getSubimage(0, 0, width, height);
        try {
            ImageIO.write(bufOut, "JPG", new File(sourceImagePath));
        } catch (IOException e) {
            logger.debug(e);
        }
    }

    /**
     * 保存jpg图片
     *
     * @param sourceImagePath 原始图片地址<br>
     * @param savePath        保存后的图片地址<br>
     * @param width           新的图片宽度 0:不改变图片宽度<br>
     * @param hight           新的图片高度 0:不改变图片高度<br>
     */
    public static void saveImageAsJpg(String sourceImagePath, String savePath, int width, int hight) {
        BufferedImage srcImage = null;
        String imgType = "JPEG";
        if (sourceImagePath.toLowerCase().endsWith(".png")) {
            imgType = "PNG";
        }
        File saveFile = new File(savePath);
        File fromFile = new File(sourceImagePath);
        try {
            srcImage = ImageIO.read(fromFile);
        } catch (IOException e) {
            logger.error(e);
        }
        if (width > 0 || hight > 0) {
            srcImage = resize(sourceImagePath, width, hight);
        }
        try {
            ImageIO.write(srcImage, imgType, saveFile);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    /**
     * 缩放图像
     *
     * @param sourceImagePath 源图像文件地址
     * @param savePath        缩放后的图像地址
     * @param scale           缩放比例
     * @param flag            缩放选择:true 放大; false 缩小;
     */
    public static void scale(String sourceImagePath, String savePath, double scale, boolean flag) {
        try {
            BufferedImage src = ImageIO.read(new File(sourceImagePath)); // 读入文件
            int width = src.getWidth(); // 得到源图宽
            int height = src.getHeight(); // 得到源图长
            if (flag) {
                // 放大
                width = (int) (width * scale);
                height = (int) (height * scale);
            } else {
                // 缩小
                width = (int) (width / scale);
                height = (int) (height / scale);
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            ImageIO.write(tag, "JPEG", new File(savePath));// 输出到文件流
        } catch (IOException e) {
            logger.equals(e);
        }
    }

    /**
     * 缩放图像透明背景
     *
     * @param sourceImagePath 源图像文件地址
     * @param savePath        缩放后的图像地址
     * @param scale           缩放比例
     * @param flag            缩放选择:true 放大; false 缩小;
     */
    public static void scaleHyaline(String sourceImagePath, String savePath, double scale, boolean flag) {
        if (isPic(sourceImagePath)) {

            try {
                BufferedImage src = ImageIO.read(new File(sourceImagePath));
                BufferedImage dstImage = null;
                AffineTransform transform = AffineTransform.getScaleInstance(scale, scale);// 返回表示缩放变换的变换
                AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
                dstImage = op.filter(src, null);

                /********** save到本地 *****************/
                try {
                    ImageIO.write(dstImage, "png", new File(savePath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                /********** save end *****************/

            } catch (IOException e) {
                logger.equals(e);
            }

        }

    }


    /**
     * 图片剪切
     *
     * @param x               剪切的起点x轴
     * @param y               剪切的起点y轴
     * @param width           剪切后的宽度
     * @param height          剪切后的高度
     * @param sourceImagePath 原始图片路径
     * @param savePath        剪切后保存的路径
     */
    @SuppressWarnings("unused")
    public static void cut(int x, int y, int width, int height, String sourceImagePath, String savePath) {

        FileInputStream is = null;
        ImageInputStream iis = null;

        try {
            // 读取图片文件
            try {
                is = new FileInputStream(sourceImagePath);
            } catch (FileNotFoundException e) {
                logger.error("图片未找到:" + sourceImagePath);
                logger.error(e);
            }
            /*
             *
			 * 返回包含所有当前已注册 ImageReader 的 Iterator，这些 ImageReader 声称能够解码指定格式。
			 * 参数：formatName - 包含非正式格式名称 . （例如 "jpeg" 或 "tiff"）等 。
			 */
            Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName("jpg");
            ImageReader reader = it.next();

            // 获取图片流
            try {
                iis = ImageIO.createImageInputStream(is);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("图片未找到:" + sourceImagePath);
                logger.error(e);
            }

			/*
             * <p>iis:读取源.true:只向前搜索 </p>.将它标记为 ‘只向前搜索’。
			 * 此设置意味着包含在输入源中的图像将只按顺序读取，可能允许 reader 避免缓存包含与以前已经读取的图像关联的数据的那些输入部分。
			 */
            reader.setInput(iis, true);

			/*
             *
			 * <p>描述如何对流进行解码的类<p>.用于指定如何在输入时从 Java Image I/O
			 * 
			 * 框架的上下文中的流转换一幅图像或一组图像。用于特定图像格式的插件
			 * 
			 * 将从其 ImageReader 实现的 getDefaultReadParam 方法中返回
			 * 
			 * ImageReadParam 的实例。
			 */
            ImageReadParam param = reader.getDefaultReadParam();
            BufferedImage bid = null;
            try {
                bid = reader.read(0);
            } catch (IOException e) {
                logger.error("图片未找到:" + sourceImagePath);
                logger.error(e);
            }

			/*
             * 图片裁剪区域。Rectangle 指定了坐标空间中的一个区域，通过 Rectangle 对象
			 * 的左上顶点的坐标（x，y）、宽度和高度可以定义这个区域。
			 */
            Rectangle rect = new Rectangle(x, y, width, height);

//			// 如果图片很小，就不进行图片剪切处理
//			if (_h < height && _w < width) {
//				rect = new Rectangle(0, 0, _h, _w);
//			}
//
//			if (_h < height && _w > width) {
//				rect = new Rectangle((_w - width) / 2, 0, width, _h);
//			}
//
//			if (_w < width && _h > height) {
//				rect = new Rectangle(0, (_h - height) / 2, _w, height);
//			}
//
//			if (_w > width && _h > height) {
//				rect = new Rectangle(x, y, width, height);
//				//rect = new Rectangle((_w - width) / 2, (_h - height) / 2, width, height);
//			}

            // 提供一个 BufferedImage，将其用作解码像素数据的目标。
            param.setSourceRegion(rect);

			/*
             * 使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象，并将 它作为一个完整的
			 * BufferedImage 返回。
			 */
            BufferedImage bi = null;
            try {
                bi = reader.read(0, param);
            } catch (IOException e) {
                logger.error("图片未找到:" + sourceImagePath);
                logger.error(e);
            }

            // 保存新图片
            try {
                ImageIO.write(bi, "jpg", new File(savePath));
            } catch (IOException e) {
                logger.error("输出路径不正确:" + savePath);
                logger.error(e);
            }

        } finally {

            if (is != null)
                try {
                    is.close();
                } catch (IOException e) {
                    logger.error(e);
                }
            if (iis != null)
                try {
                    iis.close();
                } catch (IOException e) {
                    logger.error(e);
                }

        }

    }

    /**
     * 图片格式转换
     *
     * @param path 图片路径 <br>
     * @param fix  要转换成的文件格式,文件名后缀
     */
    @SuppressWarnings("restriction")
    public static void formatImage(String path, String fix) {
        String _path = path;
        try {
            File file = new File(_path);
            InputStream is = new FileInputStream(file);
            BufferedImage image = ImageIO.read(is);
            BufferedImage tag = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
            tag.getGraphics().drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null); // 绘制缩小后的图
            FileOutputStream newimage = new FileOutputStream(path.substring(0, path.lastIndexOf(".")) + fix); // 输出到文件流
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
            encoder.encode(tag);
            newimage.close();
        } catch (Exception ex) {
            logger.equals(ex);
        }

    }


    /**
     * 缩放图片
     *
     * @param sourceImagePath 被处理的图片文件路径
     * @param savePath        处理后保存路径
     * @param targetWidth     放大(缩小)后的宽度
     * @param targetHeight    放大(缩小)后的高度
     * @param more            是否多张图片
     * @param exp             多张图片的规则，例如1.jpg|2.jpg|3.jpg 那么exp=|
     */
    @SuppressWarnings("null")
    public static void zoom(String sourceImagePath, String savePath, int targetWidth, int targetHeight, boolean more, String exp) {
        File file = null;
        BufferedImage original = null;
        BufferedImage bufOut = null;
        int width, height;
        if (more) {
            file = new File(sourceImagePath);
            try {
                original = ImageIO.read(file);
            } catch (IOException e) {
                logger.debug(e);
            }
            width = original.getWidth();
            height = original.getHeight();
            if ((targetWidth >= width) && (targetHeight >= height)) {
                targetWidth = width;
                targetHeight = height;
            }
            bufOut = new BufferedImage(width, height, original.getType());
            double xScale = new Integer(targetWidth).doubleValue() / width;
            double yScale = new Integer(targetHeight).doubleValue() / height;
            double scale = java.lang.Math.min(xScale, yScale);
            int newWidth = new Double(original.getWidth() * scale).intValue();
            int newHeight = new Double(original.getHeight() * scale).intValue();
            AffineTransform atx = AffineTransform.getScaleInstance(scale, scale);
            AffineTransformOp atop = new AffineTransformOp(atx, AffineTransformOp.TYPE_BILINEAR);
            atop.filter(original, bufOut);
            bufOut = bufOut.getSubimage(0, 0, newWidth, newHeight);
            try {
                ImageIO.write(bufOut, "JPG", file);
            } catch (IOException e) {
                logger.debug(e);
            }

        } else {

            if (sourceImagePath == null && sourceImagePath.length() > 0)
                return;
            String[] filePath = sourceImagePath.split(exp);
            if (filePath.length > 0) {
                for (int i = 0; i < filePath.length; i++) {

                    file = new File(savePath);
                    try {
                        original = ImageIO.read(file);
                    } catch (Exception e) {
                        return;
                    }

                    width = original.getWidth();
                    height = original.getHeight();
                    if ((targetWidth >= width) && (targetHeight >= height)) {
                        targetWidth = width;
                        targetHeight = height;
                    }
                    bufOut = new BufferedImage(width, height, original.getType());
                    double xScale = new Integer(targetWidth).doubleValue() / width;
                    double yScale = new Integer(targetHeight).doubleValue() / height;
                    double scale = java.lang.Math.min(xScale, yScale);
                    int newWidth = new Double(original.getWidth() * scale).intValue();
                    int newHeight = new Double(original.getHeight() * scale).intValue();
                    AffineTransform atx = AffineTransform.getScaleInstance(scale, scale);
                    AffineTransformOp atop = new AffineTransformOp(atx, AffineTransformOp.TYPE_BILINEAR);
                    atop.filter(original, bufOut);
                    bufOut = bufOut.getSubimage(0, 0, newWidth, newHeight);
                    try {
                        ImageIO.write(bufOut, "JPG", new File(savePath));
                    } catch (IOException e) {
                        logger.debug(e);
                    }
                }
            }
        }
    }

    /**
     * 相对于图片的位置
     */
    private static final int POSITION_UPPERLEFT = 0;
    private static final int POSITION_UPPERRIGHT = 10;
    private static final int POSITION_LOWERLEFT = 1;
    private static final int POSITION_LOWERRIGHT = 11;
    /**
     * 几种常见的图片格式
     */
    public static String IMAGE_TYPE_GIF = "gif";// 图形交换格式
    public static String IMAGE_TYPE_JPG = "jpg";// 联合照片专家组
    public static String IMAGE_TYPE_JPEG = "jpeg";// 联合照片专家组
    public static String IMAGE_TYPE_BMP = "bmp";// 英文Bitmap（位图）的简写，它是Windows操作系统中的标准图像文件格式
    public static String IMAGE_TYPE_PNG = "png";// 可移植网络图形
    private static ImageUtils instance;

    private ImageUtils() {
        instance = this;
    }

    /**
     * 获取实例
     *
     * @return
     */
    public static ImageUtils getInstance() {
        if (instance == null) {
            instance = new ImageUtils();
        }
        return instance;
    }

    public BufferedImage image2BufferedImage(Image image) {
        System.out.println(image.getWidth(null));
        System.out.println(image.getHeight(null));
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(image, null, null);
        g.dispose();
        System.out.println(bufferedImage.getWidth());
        System.out.println(bufferedImage.getHeight());
        return bufferedImage;
    }

    /**
     * 缩放并转换格式后保存
     *
     * @param srcPath  源路径
     * @param destPath 目标路径
     * @param width    目标宽
     * @param height   目标高
     * @param format   文件格式
     * @return
     */
    public static boolean scaleToFile(String srcPath, String destPath, int width, int height, String format) {
        boolean flag = false;
        try {
            File file = new File(srcPath);
            File destFile = new File(destPath);
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdir();
            }
            BufferedImage src = ImageIO.read(file); // 读入文件
            Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            flag = ImageIO.write(tag, format, new FileOutputStream(destFile));// 输出到文件流
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 缩放Image，此方法返回源图像按百分比缩放后的图像
     *
     * @param inputImage
     * @param percentage 百分比 允许的输入0<percentage<10000
     * @return
     */
    public static BufferedImage scaleByPercentage(BufferedImage inputImage, int percentage) {
        //允许百分比
        if (0 > percentage || percentage > 10000) {
            throw new ImagingOpException("Error::不合法的参数:percentage->" + percentage + ",percentage应该大于0~小于10000");
        }
        //获取原始图像透明度类型
        int type = inputImage.getColorModel().getTransparency();
        //获取目标图像大小
        int w = inputImage.getWidth() * percentage;
        int h = inputImage.getHeight() * percentage;
        //开启抗锯齿
        RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_ANTIALIAS_ON);
        //使用高质量压缩
        renderingHints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_RENDER_QUALITY);
        BufferedImage img = new BufferedImage(w, h, type);
        Graphics2D graphics2d = img.createGraphics();
        graphics2d.setRenderingHints(renderingHints);
        graphics2d.drawImage(inputImage, 0, 0, w, h, 0, 0, inputImage
                .getWidth(), inputImage.getHeight(), null);
        graphics2d.dispose();
        return img;
        /*此代码将返回Image类型
        return inputImage.getScaledInstance(inputImage.getWidth()*percentage,
                inputImage.getHeight()*percentage, Image.SCALE_SMOOTH);
        */
    }

    /**
     * 缩放Image，此方法返回源图像按给定最大宽度限制下按比例缩放后的图像
     *
     * @param inputImage
     * @param maxWidth：压缩后允许的最大宽度
     * @param maxHeight：压缩后允许的最大高度
     * @throws IOException return
     */
    public static BufferedImage scaleByPixelRate(BufferedImage inputImage, int maxWidth, int maxHeight) throws Exception {
        //获取原始图像透明度类型
        int type = inputImage.getColorModel().getTransparency();
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        int newWidth = maxWidth;
        int newHeight = maxHeight;
        //如果指定最大宽度超过比例
        if (width * maxHeight < height * maxWidth) {
            newWidth = (int) (newHeight * width / height);
        }
        //如果指定最大高度超过比例
        if (width * maxHeight > height * maxWidth) {
            newHeight = (int) (newWidth * height / width);
        }
        //开启抗锯齿
        RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_ANTIALIAS_ON);
        //使用高质量压缩
        renderingHints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_RENDER_QUALITY);
        BufferedImage img = new BufferedImage(newWidth, newHeight, type);
        Graphics2D graphics2d = img.createGraphics();
        graphics2d.setRenderingHints(renderingHints);
        graphics2d.drawImage(inputImage, 0, 0, newWidth, newHeight, 0, 0, width, height, null);
        graphics2d.dispose();
        return img;
    }

    /**
     * 缩放Image，此方法返回源图像按给定宽度、高度限制下缩放后的图像
     *
     * @param inputImage
     * @param newWidth   压缩后宽度
     * @param newHeight  压缩后高度
     * @throws IOException return
     */
    public static BufferedImage scaleByPixel(BufferedImage inputImage, int newWidth, int newHeight) throws Exception {
        //获取原始图像透明度类型
        int type = inputImage.getColorModel().getTransparency();
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        //开启抗锯齿
        RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        //使用高质量压缩
        renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        BufferedImage img = new BufferedImage(newWidth, newHeight, type);
        Graphics2D graphics2d = img.createGraphics();
        graphics2d.setRenderingHints(renderingHints);
        graphics2d.drawImage(inputImage, 0, 0, newWidth, newHeight, 0, 0, width, height, null);
        graphics2d.dispose();
        return img;
    }

    /**
     * 切割图像，返回指定范围的图像
     *
     * @param inputImage
     * @param x          起点横坐标
     * @param y          起点纵坐标
     * @param width      切割图片宽度:如果宽度超出图片，将改为图片自x剩余宽度
     * @param height     切割图片高度：如果高度超出图片，将改为图片自y剩余高度
     * @param fill       指定目标图像大小超出时是否补白，如果true，则表示补白；false表示不补白，此时将重置目标图像大小
     * @return
     */
    public static BufferedImage cut(BufferedImage inputImage, int x, int y, int width, int height, boolean fill) {
        //获取原始图像透明度类型
        int type = inputImage.getColorModel().getTransparency();
        int w = inputImage.getWidth();
        int h = inputImage.getHeight();
        int endx = x + width;
        int endy = y + height;
        if (x > w)
            throw new ImagingOpException("起点横坐标超出源图像范围");
        if (y > h)
            throw new ImagingOpException("起点纵坐标超出源图像范围");
        BufferedImage img;
        //补白
        if (fill) {
            img = new BufferedImage(width, height, type);
            //宽度超出限制
            if ((w - x) < width) {
                width = w - x;
                endx = w;
            }
            //高度超出限制
            if ((h - y) < height) {
                height = h - y;
                endy = h;
            }
            //不补
        } else {
            //宽度超出限制
            if ((w - x) < width) {
                width = w - x;
                endx = w;
            }
            //高度超出限制
            if ((h - y) < height) {
                height = h - y;
                endy = h;
            }
            img = new BufferedImage(width, height, type);
        }
        //开启抗锯齿
        RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_ANTIALIAS_ON);
        //使用高质量压缩
        renderingHints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_RENDER_QUALITY);
        Graphics2D graphics2d = img.createGraphics();
        graphics2d.setRenderingHints(renderingHints);
        graphics2d.drawImage(inputImage, 0, 0, width, height, x, y, endx, endy, null);
        graphics2d.dispose();
        return img;
    }

    /**
     * 切割图像，返回指定起点位置指定大小图像
     *
     * @param inputImage
     * @param startPoint 起点位置：左上：0、右上:10、左下:1、右下:11
     * @param width      切割图片宽度
     * @param height     切割图片高度
     * @param fill       指定目标图像大小超出时是否补白，如果true，则表示补白；false表示不补白，此时将重置目标图像大小
     * @return
     */
    public static BufferedImage cut(BufferedImage inputImage, int startPoint, int width, int height, boolean fill) {
        //获取原始图像透明度类型
        int type = inputImage.getColorModel().getTransparency();
        int w = inputImage.getWidth();
        int h = inputImage.getHeight();
        BufferedImage img;
        //补白
        if (fill) {
            img = new BufferedImage(width, height, type);
            if (width > w)
                width = w;
            if (height > h)
                height = h;
            //不补
        } else {
            if (width > w)
                width = w;
            if (height > h)
                height = h;
            img = new BufferedImage(width, height, type);
        }
        //开启抗锯齿
        RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_ANTIALIAS_ON);
        //使用高质量压缩
        renderingHints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_RENDER_QUALITY);
        Graphics2D graphics2d = img.createGraphics();
        graphics2d.setRenderingHints(renderingHints);
        switch (startPoint) {
            //右上
            case POSITION_UPPERRIGHT:
                graphics2d.drawImage(inputImage, w - width, 0, w, height, 0, 0, width, height, null);
                break;
            //左下
            case POSITION_LOWERLEFT:
                graphics2d.drawImage(inputImage, 0, h - height, width, h, 0, 0, width, height, null);
                break;
            //右下
            case POSITION_LOWERRIGHT:
                graphics2d.drawImage(inputImage, w - width, h - height, w, h, 0, 0, width, height, null);
                break;
            //默认左上
            case POSITION_UPPERLEFT:
            default:
                graphics2d.drawImage(inputImage, 0, 0, width, height, 0, 0, width, height, null);
        }
        graphics2d.dispose();
        return img;
    }

    /**
     * 以指定角度旋转图片：使用正角度 theta 进行旋转，可将正 x 轴上的点转向正 y 轴。
     *
     * @param inputImage
     * @param degree     角度:以度数为单位
     * @return
     */
    public static BufferedImage rotateImage(final BufferedImage inputImage,
                                            final int degree) {
        int w = inputImage.getWidth();
        int h = inputImage.getHeight();
        int type = inputImage.getColorModel().getTransparency();
        BufferedImage img = new BufferedImage(w, h, type);
        Graphics2D graphics2d = img.createGraphics();
        //开启抗锯齿
        RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_ANTIALIAS_ON);
        //使用高质量压缩
        renderingHints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_RENDER_QUALITY);
        graphics2d.setRenderingHints(renderingHints);
        graphics2d.rotate(Math.toRadians(degree), w / 2, h / 2);
        graphics2d.drawImage(inputImage, 0, 0, null);
        graphics2d.dispose();
        return img;
    }

    /**
     * 水平翻转图像
     *
     * @param inputImage 目标图像
     * @return
     */
    public static BufferedImage flipHorizontalImage(final BufferedImage inputImage) {
        int w = inputImage.getWidth();
        int h = inputImage.getHeight();
        BufferedImage img;
        Graphics2D graphics2d;
        (graphics2d = (img = new BufferedImage(w, h, inputImage
                .getColorModel().getTransparency())).createGraphics())
                .drawImage(inputImage, 0, 0, w, h, w, 0, 0, h, null);
        graphics2d.dispose();
        return img;
    }

    /**
     * 竖直翻转图像
     *
     * @param inputImage 目标图像
     * @return
     */
    public static BufferedImage flipVerticalImage(final BufferedImage inputImage) {
        int w = inputImage.getWidth();
        int h = inputImage.getHeight();
        BufferedImage img;
        Graphics2D graphics2d;
        (graphics2d = (img = new BufferedImage(w, h, inputImage
                .getColorModel().getTransparency())).createGraphics())
                .drawImage(inputImage, 0, 0, w, h, 0, h, w, 0, null);
        graphics2d.dispose();
        return img;
    }

    /**
     * 图片水印
     *
     * @param inputImage 待处理图像
     * @param markImage  水印图像
     * @param x          水印位于图片左上角的 x 坐标值
     * @param y          水印位于图片左上角的 y 坐标值
     * @param alpha      水印透明度 0.1f ~ 1.0f
     */
    public static BufferedImage waterMark(BufferedImage inputImage, BufferedImage markImage, int x, int y,
                                          float alpha) {
        BufferedImage image = new BufferedImage(inputImage.getWidth(), inputImage
                .getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.drawImage(inputImage, 0, 0, null);
        // 加载水印图像
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                alpha));
        g.drawImage(markImage, x, y, null);
        g.dispose();
        return image;
    }

    /**
     * 文字水印
     *
     * @param inputImage 待处理图像
     * @param text       水印文字
     * @param font       水印字体信息
     * @param color      水印字体颜色
     * @param x          水印位于图片左上角的 x 坐标值
     * @param y          水印位于图片左上角的 y 坐标值
     * @param alpha      水印透明度 0.1f ~ 1.0f
     */
    public static BufferedImage textMark(BufferedImage inputImage, String text, Font font,
                                         Color color, int x, int y, float alpha) {
        Font dfont = (font == null) ? new Font("宋体", 20, 13) : font;
        BufferedImage image = new BufferedImage(inputImage.getWidth(), inputImage
                .getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.drawImage(inputImage, 0, 0, null);
        g.setColor(color);
        g.setFont(dfont);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                alpha));
        g.drawString(text, x, y);
        g.dispose();
        return image;
    }

    /**
     * 图像彩色转黑白色
     *
     * @param inputImage
     * @return 转换后的BufferedImage
     */
    public final static BufferedImage toGray(BufferedImage inputImage) {
        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        //对源 BufferedImage 进行颜色转换。如果目标图像为 null，
        //则根据适当的 ColorModel 创建 BufferedImage。
        ColorConvertOp op = new ColorConvertOp(cs, null);
        return op.filter(inputImage, null);
    }

    /**
     * 图像彩色转为黑白
     *
     * @param srcImageFile  源图像地址
     * @param destImageFile 目标图像地址
     * @param formatType    目标图像格式：如果formatType为null;将默认转换为PNG
     */
    public final static void toGray(String srcImageFile, String destImageFile, String formatType) {
        try {
            BufferedImage src = ImageIO.read(new File(srcImageFile));
            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
            ColorConvertOp op = new ColorConvertOp(cs, null);
            src = op.filter(src, null);
            //如果formatType为null;将默认转换为PNG
            if (formatType == null) {
                formatType = "PNG";
            }
            ImageIO.write(src, formatType, new File(destImageFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 图像类型转换：GIF->JPG、GIF->PNG、PNG->JPG、PNG->GIF(X)、BMP->PNG
     *
     * @param inputImage    源图像地址
     * @param formatType    包含格式非正式名称的 String：如JPG、JPEG、GIF等
     * @param destImageFile 目标图像地址
     */
    public final static void convert(BufferedImage inputImage, String formatType, String destImageFile) {
        try {
            ImageIO.write(inputImage, formatType, new File(destImageFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 图像切割（指定切片的行数和列数）
     *
     * @param inputImage 源图像地址
     * @param destDir    切片目标文件夹
     * @param formatType 目标格式
     * @param rows       目标切片行数。默认2，必须是范围 [1, 20] 之内
     * @param cols       目标切片列数。默认2，必须是范围 [1, 20] 之内
     */
    public final static void cut(BufferedImage inputImage, String destDir,
                                 String formatType, int rows, int cols) {
        try {
            if (rows <= 0 || rows > 20)
                rows = 2; // 切片行数
            if (cols <= 0 || cols > 20)
                cols = 2; // 切片列数
            // 读取源图像
            //BufferedImage bi = ImageIO.read(new File(srcImageFile));
            int w = inputImage.getHeight(); // 源图宽度
            int h = inputImage.getWidth(); // 源图高度
            if (w > 0 && h > 0) {
                Image img;
                ImageFilter cropFilter;
                Image image = inputImage.getScaledInstance(w, h,
                        Image.SCALE_DEFAULT);
                int destWidth = w; // 每张切片的宽度
                int destHeight = h; // 每张切片的高度
                // 计算切片的宽度和高度
                if (w % cols == 0) {
                    destWidth = w / cols;
                } else {
                    destWidth = (int) Math.floor(w / cols) + 1;
                }
                if (h % rows == 0) {
                    destHeight = h / rows;
                } else {
                    destHeight = (int) Math.floor(h / rows) + 1;
                }
                // 循环建立切片
                // 改进的想法:是否可用多线程加快切割速度
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        // 四个参数分别为图像起点坐标和宽高
                        // 即: CropImageFilter(int x,int y,int width,int height)
                        cropFilter = new CropImageFilter(j * destWidth, i
                                * destHeight, destWidth, destHeight);
                        img = Toolkit.getDefaultToolkit().createImage(
                                new FilteredImageSource(image.getSource(),
                                        cropFilter));
                        BufferedImage tag = new BufferedImage(destWidth,
                                destHeight, BufferedImage.TYPE_INT_ARGB);
                        Graphics g = tag.getGraphics();
                        g.drawImage(img, 0, 0, null); // 绘制缩小后的图
                        g.dispose();
                        // 输出为文件
                        ImageIO.write(tag, formatType, new File(destDir + "_r" + i
                                + "_c" + j + "." + formatType.toLowerCase()));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 给图片添加文字水印
     *
     * @param pressText     水印文字
     * @param srcImageFile  源图像地址
     * @param destImageFile 目标图像地址
     * @param fontName      水印的字体名称
     * @param fontStyle     水印的字体样式
     * @param color         水印的字体颜色
     * @param fontSize      水印的字体大小
     * @param x             修正值
     * @param y             修正值
     * @param alpha         透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @param formatType    目标格式
     */
    public final static void pressText(String pressText, String srcImageFile,
                                       String destImageFile, String fontName, int fontStyle, Color color,
                                       int fontSize, int x, int y, float alpha, String formatType) {
        try {
            File img = new File(srcImageFile);
            Image src = ImageIO.read(img);
            int width = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, width, height, null);
            g.setColor(color);
            g.setFont(new Font(fontName, fontStyle, fontSize));
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    alpha));
            // 在指定坐标绘制水印文字
            g.drawString(pressText, (width - (getLength(pressText) * fontSize))
                    / 2 + x, (height - fontSize) / 2 + y);
            g.dispose();
            ImageIO.write((BufferedImage) image, formatType,
                    new File(destImageFile));// 输出到文件流
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 给图片添加图片水印
     *
     * @param pressImg      水印图片
     * @param srcImageFile  源图像地址
     * @param destImageFile 目标图像地址
     * @param x             修正值。 默认在中间
     * @param y             修正值。 默认在中间
     * @param alpha         透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @param formatType    目标格式
     */
    public final static void pressImage(String pressImg, String srcImageFile,
                                        String destImageFile, int x, int y, float alpha, String formatType) {
        try {
            File img = new File(srcImageFile);
            Image src = ImageIO.read(img);
            int wideth = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(wideth, height,
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, wideth, height, null);
            // 水印文件
            Image src_biao = ImageIO.read(new File(pressImg));
            int width_biao = src_biao.getWidth(null);
            int height_biao = src_biao.getHeight(null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    alpha));
            g.drawImage(src_biao, (wideth - width_biao) / 2,
                    (height - height_biao) / 2, width_biao, height_biao, null);
            // 水印文件结束
            g.dispose();
            ImageIO.write((BufferedImage) image, formatType,
                    new File(destImageFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 计算text的长度（一个中文算两个字符）
     *
     * @param text
     * @return
     */
    public final static int getLength(String text) {
        int length = 0;
        for (int i = 0; i < text.length(); i++) {
            if (new String(text.charAt(i) + "").getBytes().length > 1) {
                length += 2;
            } else {
                length += 1;
            }
        }
        return length / 2;
    }
}
