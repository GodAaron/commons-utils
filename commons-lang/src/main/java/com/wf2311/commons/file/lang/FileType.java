package com.wf2311.commons.file.lang;

import java.io.File;

/**
 * @author: wf2311
 * @date: 2015/8/10 9:32
 */
@Deprecated
public class FileType {
    public static final String SUFFIX_ZIP = "zip";
    public static final String P_SUFFIX_ZIP = ".zip";
    public static final String SUFFIX_RAR = "rar";
    public static final String P_SUFFIX_RAR = ".rar";
    public static final String SUFFIX_7Z = ".7z";
    public static final String P_SUFFIX_7Z = ".7z";

    public static final String SUFFIX_DOC = "doc";
    public static final String P_SUFFIX_DOC = ".doc";
    public static final String SUFFIX_DOCX = "docx";
    public static final String P_SUFFIX_DOCX = ".docx";
    public static final String SUFFIX_PDF = "pdf";
    public static final String P_SUFFIX_PDF = ".pdf";
    public static final String SUFFIX_TXT = "txt";
    public static final String P_SUFFIX_TXT = ".txt";
    public static final String FOLDER = "folder";
    public static final String IS_FOLDER = "1";
    public static final String IS_NO_FOLDER = "0";

    public static final String P_SUFFIX_JPG = ".jpg";
    public static final String P_SUFFIX_JPEG = ".jpeg";
    public static final String P_SUFFIX_PNG = ".png";
    public static final String P_SUFFIX_GIF = ".gif";
    public static final String P_SUFFIX_BMP = ".bmp";


    /**
     * 得到文件的文件格式
     *
     * @param file
     * @return 例如：getFileSuffix(new File("a.txt")) = txt ; <br/>
     * 如果是文件夹 则返回 FileType.FOLDER 对应的字符串
     */
    public static String getFileSuffix(File file) {

        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return FOLDER;
        }
    }

    public static String getFileSuffix(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return FOLDER;
        }
    }

    /**
     * 判断文件是否是压缩文件(目前只指zip和rar)
     *
     * @param file
     * @return
     */
    public static boolean isZipFile(File file) {
        String name = file.getName();
        return isEndsWithIgnoreCase(name, P_SUFFIX_ZIP) ||
                isEndsWithIgnoreCase(name, P_SUFFIX_RAR);
    }

    /**
     * 判断文件是否是指定的文件格式(后缀)
     *
     * @param s 文件名(包含后缀)
     * @param t 指定后缀
     * @return
     */
    public static boolean isEndsWithIgnoreCase(String s, String t) {
        return s.toLowerCase().endsWith(t.toLowerCase());
    }

//    /**
//     * 判断文件后缀是否是规定的图片格式
//     *
//     * @param file
//     * @return
//     */
//    public static boolean isImgFile(File file) {
//        String fileName = file.getName();
//        String imgTypes = ConfigUtils.getImgType();
//        String[] types = imgTypes.trim().split(",");
//        for (String s : types) {
//            if (s.trim() != null && s != null) {
//                if (isEndsWithIgnoreCase(fileName, s)) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 判断文件后缀是否是规定的图片格式
//     *
//     * @param fileName 文件名(包含文件后缀)
//     * @return
//     */
//    public static boolean isImgFile(String fileName) {
//        String imgTypes = ConfigUtils.getImgType();
//        String[] types = imgTypes.trim().split(",");
//        for (String s : types) {
//            if (s.trim() != null && s != null) {
//                if (isEndsWithIgnoreCase(fileName, s)) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

    public static void main(String[] args) {
        String fileName = "a.gif";
//        System.out.println(isImgFile(fileName));
    }

}
