package com.wf2311.commons.file.lang;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author: wf2311
 * @date: 2015/8/24 10:04
 */
public class FileUtils {

    private static class FileUtilHolder {
        private static final FileUtils INSTANCE = new FileUtils();
    }

    private FileUtils() {
    }

    public static FileUtils getInstance() {
        return FileUtilHolder.INSTANCE;
    }

    /**
     * 将文件路径转化为适合当前操作系统的合适的格式
     *
     * @param path
     * @return
     */
    public static String path2ProperFormat(String path) {
        String s = File.separator;
        if (s.equals("\\")) {
            s = s + s;
        }
        path = path.replaceAll("(/)+|(\\\\)", s);

        if (s.equals("\\\\")) {
            path = path.replaceAll("[\\\\]+", s);
        }
        if (s.equals("/")) {
            path = path.replaceAll("[/]", s);
        }
        return path;
    }

    /**
     * 保存上传文件
     *
     * @param file
     * @param saveFolder 文件保存路径(不包含文件名)
     * @param fileName   保存文件名(为空则默认为上传时文件名)
     * @return
     */
    public String saveFile(MultipartFile file, String saveFolder, String fileName) {
        if (file != null) {
            //取得当前上传文件的文件名称
            String fileRealName = file.getOriginalFilename();
            if (fileRealName.trim() != "") {
                fileName = (fileName != null && fileName.trim() != "") ? fileName : fileRealName;
//                logger.debug("FileUtil.saveFile().fileName = " + fileName);
                String path = saveFolder + File.separator + fileName;
                try {
                    file.transferTo(new File(path));
                    return path;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return null;
    }
}
