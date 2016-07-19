package com.wf2311.commons.fileupload.web.controller;

import com.wf2311.commons.fileupload.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.Set;

/**
 * @author wf2311
 * @time 2016/06/23 09:18.
 */
@Controller
public class AttachmentController {
    @Autowired
    private AttachmentService attachmentService;

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ResponseBody
    public String upload(MultipartHttpServletRequest request){
        MultiValueMap<String,MultipartFile> multiMap=request.getMultiFileMap();
        Set<String> keys=multiMap.keySet();
        for (String key:keys){
            List<MultipartFile> list=multiMap.get(key);
            for (MultipartFile file:list){
                file.getOriginalFilename();
            }
        }
        return null;
    }
}
