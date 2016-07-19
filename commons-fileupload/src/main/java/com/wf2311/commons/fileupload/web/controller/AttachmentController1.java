package com.wf2311.commons.fileupload.web.controller;

import com.wf2311.commons.file.lang.FileType;
import com.wf2311.commons.file.lang.FileUtils;
import com.wf2311.commons.file.lang.MD5BigFileUtil;
import com.wf2311.commons.fileupload.domain.Attachment;
import com.wf2311.commons.fileupload.service.AttachmentService;
import com.wf2311.commons.lang.text.PropertiesLoader;
import com.wf2311.commons.lang.utils.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wf2311
 * @date 2016/03/25 20:02.
 */
@Controller
public class AttachmentController1 implements ServletContextAware {
    /**
     * Spring这里是通过实现ServletContextAware接口来注入ServletContext对象
     **/
    private ServletContext servletContext;

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    private PropertiesLoader propertiesLoader = new PropertiesLoader("config/application.properties");

    private String BASE_UPLOAD_PATH = propertiesLoader.getProperty("base.upload.path");

    @Autowired
    private AttachmentService attachmentService;

    @RequestMapping(value = "/upload",method = RequestMethod.GET)
    public ModelAndView upload() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("upload");
        return mav;
    }

    @RequestMapping(value = "/test.html")
    public Map<String,Object> test(){
        Map<String,Object> map=new HashMap<>();
        map.put("code",1);
        map.put("msg","success");
        return map;
    }

    /**
     * 批量上传文件
     *
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @Deprecated
    public ModelAndView upload(HttpServletRequest request) throws IOException {
        ModelAndView mav = new ModelAndView();
        //创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(servletContext);
        //判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MMdd/HHmm");
            /**文件保存目录的真实路径**/
            String saveFolder = BASE_UPLOAD_PATH + File.separator + dateFormat.format(new Date());
            File folder = new File(saveFolder);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            String fileFullPath;
            while (iter.hasNext()) {
                //取得上传文件
                MultipartFile file = multiRequest.getFile(iter.next());
                fileFullPath = (FileUtils.getInstance().saveFile(file, saveFolder, null));
                if (fileFullPath != null) {
                    Attachment attachment = new Attachment();
                    attachment.setUid(UUID.randomUUID().toString());
                    attachment.setDownloadCount(0l);
                    String savePath = fileFullPath.substring(FileUtils.path2ProperFormat(BASE_UPLOAD_PATH).length());
                    savePath = FileUtils.path2ProperFormat(savePath);
                    attachment.setSavePath(savePath);
                    attachment.setFileName(file.getOriginalFilename());
                    attachment.setSuffix(FileType.getFileSuffix(fileFullPath));
                    attachment.setSize(file.getSize());
                    attachment.setMd5(MD5BigFileUtil.md5(new File(fileFullPath)));

                    attachmentService.insert(attachment);
                }
            }
        }
        mav.setViewName("/upload");
        return mav;
    }

    @RequestMapping("/download/{fid}.html")
    public void fileDownload(@PathVariable Long fid, HttpServletResponse response, HttpServletRequest request) {
        Attachment attachment = attachmentService.get(fid);
        String fileName = attachment.getFileName();
        String fileFullPath = FileUtils.path2ProperFormat(BASE_UPLOAD_PATH + File.separator + attachment.getSavePath());
        File file = new File(fileFullPath);
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setContentType("multipart/form-data");
        //设置文件头：最后一个参数是下载文件名(通过调用ServletUtil.setResponseHeaders()方法解决文件名中文不显示或乱码)
        ServletUtils.setResponseHeaders(request, response, fileName);
        try {
            InputStream inputStream = new FileInputStream(file);

            OutputStream os = response.getOutputStream();
            byte[] b = new byte[2048];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
            }
            // 这里主要关闭。
            os.close();
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
