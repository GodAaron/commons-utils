package com.wf2311.commons.file.core;

import com.wf2311.commons.exception.WfException;
import org.apache.log4j.Logger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;

import java.io.*;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 解压缩文件
 * @author wf2311
 * @time 2016/03/29 12:50.
 */
public class ZipUtils {
    private static final Logger logger = Logger.getLogger(ZipUtils.class);
    private static final String ENCODING = "GBK";

    public static void zip(String inputFilePath, String zipFileName) {
        File inputFile = new File(inputFilePath);
        if (!inputFile.exists())
            throw new RuntimeException("原始文件不存在!!!");
        File basetarZipFile = new File(zipFileName).getParentFile();
        if (!basetarZipFile.exists() && !basetarZipFile.mkdirs())
            throw new RuntimeException("目标文件无法创建!!!");
        BufferedOutputStream bos = null;
        FileOutputStream out = null;
        ZipOutputStream zOut = null;
        try {
            // 创建文件输出对象out,提示:注意中文支持
            out = new FileOutputStream(new String(zipFileName.getBytes(ENCODING)));
            bos = new BufferedOutputStream(out);
            // 將文件輸出ZIP输出流接起来
            zOut = new ZipOutputStream(bos);
            zip(zOut, inputFile, inputFile.getName());
            if (zOut != null) {
                zOut.close();
            }
            if (bos != null) {
                bos.close();
            }
            if (out != null) {
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void zip(ZipOutputStream zOut, File file, String base) {

        try {
            // 如果文件句柄是目录
            if (file.isDirectory()) {
                // 获取目录下的文件
                File[] listFiles = file.listFiles();
                // 建立ZIP条目
                zOut.putNextEntry(new ZipEntry(base + "/"));
                base = (base.length() == 0 ? "" : base + "/");
                if (listFiles != null && listFiles.length > 0)
                    // 遍历目录下文件
                    for (File f : listFiles)
                        // 递归进入本方法
                        zip(zOut, f, base + f.getName());
            }
            // 如果文件句柄是文件
            else {
                if (Objects.equals(base, "")) {
                    base = file.getName();
                }
                // 填入文件句柄
                zOut.putNextEntry(new ZipEntry(base));
                // 开始压缩
                // 从文件入流读,写入ZIP 出流
                writeFile(zOut, file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void writeFile(ZipOutputStream zOut, File file)
            throws IOException {
        FileInputStream in = null;
        BufferedInputStream bis = null;
        in = new FileInputStream(file);
        bis = new BufferedInputStream(in);
        int len = 0;
        byte[] buff = new byte[2048];
        while ((len = bis.read(buff)) != -1)
            zOut.write(buff, 0, len);
        zOut.flush();
        if (bis != null) {
            bis.close();
        }
        if (in != null) {
            in.close();
        }
    }

    /****
     * 解压
     *
     * @param zipPath         zip文件路径
     * @param destinationPath 解压的目的地点
     * @param ecode           文件名的编码字符集
     */
    public static void unZip(String zipPath, String destinationPath,String ecode) {
        File zipFile = new File(zipPath);
        if (!zipFile.exists())
            throw new WfException("zip file " + zipPath
                    + " does not exist.");
        Project project = new Project();
        Expand expand = new Expand();
        expand.setProject(project);
        expand.setTaskType("unzip");
        expand.setTaskName("unzip");
        expand.setSrc(zipFile);
        expand.setDest(new File(destinationPath));
        expand.setEncoding(ecode);
        expand.execute();
        logger.info("unzip done!!!");
    }

    public static void unZip(String zipPath,String destinationPath){
        unZip(zipPath,destinationPath,ENCODING);
    }
}
