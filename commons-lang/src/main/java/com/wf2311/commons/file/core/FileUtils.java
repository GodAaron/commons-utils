package com.wf2311.commons.file.core;

import com.wf2311.commons.file.lang.SizeType;
import org.apache.log4j.Logger;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wf2311
 * @time 2016/04/19 20:55.
 */
public class FileUtils {
    private static final Logger logger = Logger.getLogger(FileUtils.class);
    public static final String DEFAULT_CHARSET = "utf8";
    public static final char POINT = '.';

    /**
     * 获取文件的大小,单位(b)
     *
     * @param file 文件
     * @return
     */
    public static long getSize(File file) {
        if (!file.exists()){
            return -1;
        }
        long size;
        try {
            FileInputStream fin = new FileInputStream(file);
            size = fin.available();
        } catch (IOException e) {
            size = -1;
        }
        return size;
    }

    /**
     * 获取文件大小
     *
     * @param file 文件
     * @param type 单位类型，参见{@link SizeType}
     * @return
     */
    public static Double getSize(File file, SizeType type) {
        long size = getSize(file);
        if (size < 0) {
            return -1d;
        }
        if (type == null) {
            type = SizeType.B;
        }
        if (type == SizeType.PB) {
            return (double) size / SizeType.GB.getUtil() / SizeType.KB.getUtil();
        }
        return (double) size / type.getUtil();
    }

    /**
     * 查找指定目录下所有文件
     *
     * @param dir   文件夹
     * @param files 存放结果集
     */
    public static void getFiles(File dir, List<File> files) {
        if (!dir.exists()) {
            return;
        }
        if (dir.isDirectory()) {
            File[] nodes = dir.listFiles();
            if (nodes != null) {
                files.addAll(Arrays.asList(nodes));
                for (File file : nodes) {
                    getFiles(file, files);
                }
            }
        }
    }


    /**
     * 查找指定目录下所有文件
     *
     * @param dirPath 文件夹路径
     * @param files   存放结果集
     */
    public static void getFiles(String dirPath, List<String> files) {
        File dir = new File(dirPath);
        if (!dir.exists()) {
            return;
        }
        if (dir.isDirectory()) {
            File[] nodes = dir.listFiles();
            if (nodes != null) {
                for (File file : nodes) {
                    files.add(file.getAbsolutePath());
                    getFiles(file.getAbsolutePath(), files);
                }
            }
        }
    }

    /**
     * 查找指定目录下所有文件
     *
     * @param dir
     * @return
     */
    @Deprecated
    public static List<File> getFiles(File dir) {
        List<File> files = new ArrayList<>();
        if (!dir.exists()) {
            return null;
        }
        if (dir.isDirectory()) {
            File[] nodes = dir.listFiles();
            if (nodes != null) {
                files.addAll(Arrays.asList(nodes));
                for (File file : nodes) {
                    List<File> list = getFiles(file);
                    if (list != null) {
                        files.addAll(list);
                    }
                }
            }
        }
        return files;
    }

    /**
     * 查找指定目录下所有文件
     *
     * @param dirPath
     * @return
     */
    @Deprecated
    public static List<String> getFiles(String dirPath) {
        List<String> files = new ArrayList<>();
        File dir = new File(dirPath);
        if (!dir.exists()) {
            return null;
        }
        if (dir.isDirectory()) {
            File[] nodes = dir.listFiles();
            if (nodes != null) {
                for (File file : nodes) {
                    files.add(file.getAbsolutePath());
                    List<String> list = getFiles(file.getPath());
                    if (list != null) {
                        files.addAll(list);
                    }
                }
            }
        }
        return files;
    }

    /**
     * 递归查找文件
     *
     * @param baseDirName    查找的文件夹路径
     * @param targetFileName 需要查找的文件名
     * @param fileList       查找到的文件集合
     */
    public static void findFiles(String baseDirName, String targetFileName, List fileList) {

        File baseDir = new File(baseDirName);       // 创建一个File对象
        if (!baseDir.exists() || !baseDir.isDirectory()) {  // 判断目录是否存在
            logger.error("文件查找失败：" + baseDirName + "不是一个目录！");
        }
        String tempName;
        //判断目录是否存在
        File tempFile;
        File[] files = baseDir.listFiles();
        for (int i = 0; i < files.length; i++) {
            tempFile = files[i];
            if (tempFile.isDirectory()) {
                findFiles(tempFile.getAbsolutePath(), targetFileName, fileList);
            } else if (tempFile.isFile()) {
                tempName = tempFile.getName();
                if (wildcardMatch(targetFileName, tempName)) {
                    // 匹配成功，将文件名添加到结果集
                    fileList.add(tempFile.getAbsoluteFile());
                }
            }
        }
    }

    /**
     * 递归查找文件
     *
     * @param baseDirName    查找的文件夹路径
     * @param targetFileName 需要查找的文件名
     */
    public static List findFiles(String baseDirName, String targetFileName) {
        List fileList = new ArrayList();
        File baseDir = new File(baseDirName);       // 创建一个File对象
        if (!baseDir.exists() || !baseDir.isDirectory()) {  // 判断目录是否存在
            logger.error("文件查找失败：" + baseDirName + "不是一个目录！");
        }
        String tempName;
        //判断目录是否存在
        File tempFile;
        File[] files = baseDir.listFiles();
        for (int i = 0; i < files.length; i++) {
            tempFile = files[i];
            if (tempFile.isDirectory()) {
                fileList.addAll(findFiles(tempFile.getAbsolutePath(), targetFileName));
            } else if (tempFile.isFile()) {
                tempName = tempFile.getName();
                if (wildcardMatch(targetFileName, tempName)) {
                    // 匹配成功，将文件名添加到结果集
                    fileList.add(tempFile.getAbsoluteFile());
                }
            }
        }
        return fileList;
    }

    /**
     * 通配符匹配
     *
     * @param pattern 通配符模式
     * @param str     待匹配的字符串
     * @return 匹配成功则返回true，否则返回false
     */
    private static boolean wildcardMatch(String pattern, String str) {
        int patternLength = pattern.length();
        int strLength = str.length();
        int strIndex = 0;
        char ch;
        for (int patternIndex = 0; patternIndex < patternLength; patternIndex++) {
            ch = pattern.charAt(patternIndex);
            if (ch == '*') {
                //通配符星号*表示可以匹配任意多个字符
                while (strIndex < strLength) {
                    if (wildcardMatch(pattern.substring(patternIndex + 1),
                            str.substring(strIndex))) {
                        return true;
                    }
                    strIndex++;
                }
            } else if (ch == '?') {
                //通配符问号?表示匹配任意一个字符
                strIndex++;
                if (strIndex > strLength) {
                    //表示str中已经没有字符匹配?了。
                    return false;
                }
            } else {
                if ((strIndex >= strLength) || (ch != str.charAt(strIndex))) {
                    return false;
                }
                strIndex++;
            }
        }
        return (strIndex == strLength);
    }

    /**
     * 读取一个文件
     *
     * @param file 文件
     * @param charset 编码格式
     * @return
     * @throws IOException
     */
    public static List<String> readFile(File file, String charset)
            throws IOException {
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, charset);
        BufferedReader br = new BufferedReader(isr);
        LineNumberReader lnr = new LineNumberReader(br);

        List<String> returnValue = new ArrayList<String>();
        while (true) {
            String tempStr = lnr.readLine();
            if (tempStr == null)
                break;
            if (tempStr.length() < 2)
                continue;
            returnValue.add(tempStr);
        }
        lnr.close();
        br.close();
        isr.close();
        fis.close();
        return returnValue;
    }

    /**
     * 读取一个文件
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static List<String> readFile(File file) throws IOException {
        return readFile(file, FileCharsetDetector.guessFileEncoding(file));
    }

    /**
     * 读取一个文件,并去掉每行前后的空格后返回
     * @param file 文件
     * @param charset 编码格式
     */
    public static List<String> readFileNoDup(File file, String charset)
            throws IOException {
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, charset);
        BufferedReader br = new BufferedReader(isr);
        LineNumberReader lnr = new LineNumberReader(br);

        Set<String> set = new HashSet<String>();
        while (true) {
            String tempStr = lnr.readLine();
            if (tempStr == null)
                break;
            if (tempStr.length() < 2)
                continue;
            set.add(tempStr.trim());
        }
        lnr.close();
        br.close();
        isr.close();
        fis.close();
        List<String> returnValue = new ArrayList<String>(set.size());
        returnValue.addAll(set);
        return returnValue;
    }

    /**
     * 读取一个文件,并去掉每行前后的空格后返回
     *
     * @param file 文件
     */
    public static List<String> readFileNoDup(File file)
            throws IOException {
        return readFileNoDup(file, FileCharsetDetector.guessFileEncoding(file));
    }

    /**
     * 添加内容到指定文件
     *
     * @param file    要写入的文件
     * @param content 要写入的内容
     * @param flag    true\false，true则向现有文件中添加内容，否则覆盖原有内容
     * @param charset 文件编码格式
     * @throws IOException
     */
    public static void writeFile(File file, String content, boolean flag, String charset) throws IOException {
        if (null == content || content.length() < 1)
            return;
        if (!file.exists()) {
            new File(file.getParent()).mkdirs();
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file, flag);
        OutputStreamWriter osw = new OutputStreamWriter(fos, charset);
        osw.write(content + "\r\n");
        osw.flush();
        osw.close();
    }

    /**
     * 添加内容到指定文件
     * 默认编码  {@link FileUtils#DEFAULT_CHARSET}
     *
     * @param file    要写入的文件
     * @param content 要写入的内容
     * @param flag    true\false，true则向现有文件中添加内容，否则覆盖原有内容
     * @throws IOException
     */
    public static void writeFile(File file, String content, boolean flag) throws IOException {
        writeFile(file, content, flag, DEFAULT_CHARSET);
    }

    /**
     * 添加内容到指定文件
     * 如果该文件不存在，则创建并添加内容;如果该文件已存在，则添加内容到已有内容最后
     *
     * @param file    要写入的文件
     * @param content 要写入的内容
     * @param charset 文件编码格式
     * @throws IOException
     */
    public static void writeFile(File file, String content, String charset) throws IOException {
        writeFile(file, content, true, charset);
    }

    /**
     * 添加内容到指定文件
     * 如果该文件不存在，则创建并添加内容;如果该文件已存在，则添加内容到已有内容最后
     * 默认编码  {@link FileUtils#DEFAULT_CHARSET}
     *
     * @param file    要写入的文件
     * @param content 要写入的内容
     * @throws IOException
     */
    public static void writeFile(File file, String content) throws IOException {
        writeFile(file, content, true, DEFAULT_CHARSET);
    }

    /**
     * 添加内容到指定文件
     *
     * @param path    要写入的文件路径
     * @param content 要写入的内容
     * @param flag    true\false，true则向现有文件中添加内容，否则覆盖原有内容
     * @param charset 文件编码格式
     * @throws IOException
     */
    public static void writeFile(String path, String content, boolean flag, String charset) throws IOException {
        File file = new File(path);
        writeFile(file, content, flag, charset);
    }

    /**
     * 添加内容到指定文件
     * 如果该文件不存在，则创建并添加内容;如果该文件已存在，则添加内容到已有内容最后
     *
     * @param path    要写入的文件路径
     * @param content 要写入的内容
     * @param charset 文件编码格式
     * @throws IOException
     */
    public static void writeFile(String path, String content, String charset) throws IOException {
        File file = new File(path);
        writeFile(file, content, true, charset);
    }

    /**
     * 添加内容到指定文件
     * 默认编码  {@link FileUtils#DEFAULT_CHARSET}
     *
     * @param path    要写入的文件路径
     * @param content 要写入的内容
     * @param flag    true\false，true则向现有文件中添加内容，否则覆盖原有内容
     * @throws IOException
     */
    public static void writeFile(String path, String content, boolean flag) throws IOException {
        File file = new File(path);
        writeFile(file, content, flag, DEFAULT_CHARSET);
    }

    /**
     * 添加内容到指定文件
     * 如果该文件不存在，则创建并添加内容;如果该文件已存在，则添加内容到已有内容最后
     * 默认编码  {@link FileUtils#DEFAULT_CHARSET}
     *
     * @param path    要写入的文件路径
     * @param content 要写入的内容
     * @throws IOException
     */
    public static void writeFile(String path, String content) throws IOException {
        File file = new File(path);
        writeFile(file, content, true, DEFAULT_CHARSET);
    }

    /**
     * 添加内容到指定文件
     *
     * @param file        要写入的文件
     * @param fileContent 要写入的内容集合
     * @param flag        true\false，true则向现有文件中添加内容，否则覆盖原有内容
     * @param charset     文件编码格式
     */
    public static void writeFile(File file,
                                 List<String> fileContent, boolean flag, String charset) throws IOException {

        if (!file.exists()) {
            new File(file.getParent()).mkdirs();
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file, flag);
        OutputStreamWriter osw = new OutputStreamWriter(fos, charset);
        for (String temp : fileContent)
            osw.write(temp + "\r\n");
        osw.flush();
        osw.close();
    }

    /**
     * 添加内容到指定文件
     * 默认编码  {@link FileUtils#DEFAULT_CHARSET}
     *
     * @param file        要写入的文件
     * @param fileContent 要写入的内容集合
     * @param flag        true\false，true则向现有文件中添加内容，否则覆盖原有内容
     */
    public static void writeFile(File file, List<String> fileContent, boolean flag) throws IOException {
        writeFile(file, fileContent, flag, DEFAULT_CHARSET);
    }

    /**
     * 添加内容到指定文件
     * 如果该文件不存在，则创建并添加内容;如果该文件已存在，则添加内容到已有内容最后
     *
     * @param file        要写入的文件
     * @param fileContent 要写入的内容集合
     * @param charset     文件编码格式
     */
    public static void writeFile(File file,
                                 List<String> fileContent, String charset) throws IOException {
        writeFile(file, fileContent, true, charset);
    }

    /**
     * 添加内容到指定文件
     * 如果该文件不存在，则创建并添加内容;如果该文件已存在，则添加内容到已有内容最后
     * 默认编码  {@link FileUtils#DEFAULT_CHARSET}
     *
     * @param file        要写入的文件
     * @param fileContent 要写入的内容集合
     */
    public static void writeFile(File file, List<String> fileContent) throws IOException {
        writeFile(file, fileContent, true, DEFAULT_CHARSET);
    }


    /**
     * 添加内容到指定文件
     *
     * @param path        要写入的文件路径
     * @param fileContent 要写入的内容集合
     * @param flag        true\false，true则向现有文件中添加内容，否则覆盖原有内容
     * @param charset   文件编码格式
     */
    public static void writeFile(String path,
                                 List<String> fileContent, boolean flag, String charset) throws IOException {
        File file = new File(path);
        writeFile(file, fileContent, flag, charset);
    }

    /**
     * 添加内容到指定文件
     * 如果该文件不存在，则创建并添加内容;如果该文件已存在，则添加内容到已有内容最后
     *
     * @param path        要写入的文件路径
     * @param fileContent 要写入的内容集合
     * @param charset     文件编码格式
     */
    public static void writeFile(String path,
                                 List<String> fileContent, String charset) throws IOException {
        File file = new File(path);
        writeFile(file, fileContent, true, charset);
    }

    /**
     * 添加内容到指定文件
     * 默认编码  {@link FileUtils#DEFAULT_CHARSET}
     *
     * @param path        要写入的文件路径
     * @param fileContent 要写入的内容集合
     * @param flag        true\false，true则向现有文件中添加内容，否则覆盖原有内容
     */
    public static void writeFile(String path,
                                 List<String> fileContent, boolean flag) throws IOException {
        writeFile(path, fileContent, flag, DEFAULT_CHARSET);
    }

    /**
     * 添加内容到指定文件
     * 如果该文件不存在，则创建并添加内容;如果该文件已存在，则添加内容到已有内容最后
     * 默认编码  {@link FileUtils#DEFAULT_CHARSET}
     *
     * @param path        要写入的文件路径
     * @param fileContent 要写入的内容集合
     */
    public static void writeFile(String path, List<String> fileContent) throws IOException {
        writeFile(path, fileContent, true, DEFAULT_CHARSET);
    }

    /**
     * 返回按照当前时间生成的文件夹
     * 默认格式为yyyy/MM/dd/HH/
     *
     * @return
     */
    public static String dateFolder() {
        String pattern = "yyyy/MM/dd/HH/";
        return dateFolder(pattern, true);
    }

    /**
     * 返回按照当前时间生成的文件夹
     *
     * @param pattern 格式，例如 yyyy/MM/dd/HH/
     * @return
     */
    public static String dateFolder(String pattern) {
        return dateFolder(pattern, true);
    }

    /**
     * 返回按照当前时间生成的文件夹
     *
     * @param identifyOS 是否识别操作系统，在windows下，文件分隔符为\;linux下为/
     * @return
     */
    public static String dateFolder(boolean identifyOS) {
        String pattern = "yyyy/MM/dd/HH/";
        return dateFolder(pattern, identifyOS);
    }

    /**
     * 返回按照当前时间生成的文件夹
     *
     * @param pattern    格式，例如 yyyy/MM/dd/HH/
     * @param identifyOS 是否识别操作系统，在windows下，文件分隔符为\;linux下为/
     * @return
     */
    public static String dateFolder(String pattern, boolean identifyOS) {
        if (pattern.contains("/") && identifyOS) {
            pattern.replace("/", File.separator);
        }
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }


    /**
     * 获取文件后缀名(以第一个点号为准)
     * getSuffix(new File("a.txt"))="txt"
     * getSuffix(new File("b.tar.gz"))="tar.gz"
     * getSuffix(new File("c."))=""
     * getSuffix(new File("d"))=null
     *
     * @param file 文件
     * @return
     */
    public static String getSuffix(File file) {
        String name = file.getName();
        int index = name.indexOf(POINT);
        if (index < 0) {
            return null;
        }
        return name.substring(index + 1, name.length());
    }

    /**
     * 获取文件后缀名(以第一个点号为准)
     * getSuffix("a.txt")="txt"
     * getSuffix("b.tar.gz")="tar.gz"
     * getSuffix("c.")=""
     * getSuffix("d")=null
     *
     * @param path 文件路径
     * @return
     */
    public static String getSuffix(String path) {
        return getSuffix(new File(path));
    }

    /**
     * 获取文件不包含后缀的名称
     * getMainName("c:\\test\\a.txt",false)="a"
     * getMainName("c:\\test\\a.txt",true)="c:\\test\\a"
     * getMainName("c:\\test\\.txt",true)="c:\\test\\"
     * getMainName("c:\\test\\.txt",false)=""
     *
     * @param path 文件路径
     * @param full 是否要包含父路径
     * @return
     */
    public static String getMainName(String path, boolean full) {
        return getMainName(new File(path), full);
    }

    /**
     * 获取文件不包含后缀的名称
     * getMainName(new File("c:\\test\\a.txt",false))="a"
     * getMainName(new File("c:\\test\\a.txt",true))="c:\\test\\a"
     * getMainName(new File("c:\\test\\.txt",true))="c:\\test\\"
     * getMainName(new File("c:\\test\\.txt",false))=""
     *
     * @param file 文件
     * @param full 是否要包含父路径
     * @return
     */
    public static String getMainName(File file, boolean full) {
        String name = file.getName();
        String mainName;
        int index = name.indexOf(POINT);
        if (index < 0) {
            mainName = name;
        } else if (index == 0) {
            mainName = "";
        } else {
            mainName = name.substring(0, index);
        }
        if (full) {
            mainName = file.getParent() + File.separator + mainName;
        }
        return mainName;
    }

    /**
     * 获取在文件名最后以追加方式修改文件的名称
     * appendFileName("abc.txt","def")="abcdef.txt"
     * appendFileName("c:\\test\\abc.txt","def")="c:\\test\\abcdef.txt"
     *
     * @param fileName 文件名
     * @param append   要追加的内容
     * @return
     */
    public static String appendFileName(String fileName, String append) {
        File file = new File(fileName);
        String name = getMainName(file, false);
        String suffix = getSuffix(file);
        String after= name +append+ POINT + suffix;
        if (file.getParent()!=null){
            after=file.getParent()+File.separator+after;
        }
        return after;
    }

}
