package com.wf2311.commons.fileupload.service.impl;

import com.wf2311.commons.fileupload.dao.AttachmentDao;
import com.wf2311.commons.fileupload.domain.Attachment;
import com.wf2311.commons.fileupload.service.AttachmentService;
import com.wf2311.commons.persist.mongo.dao.BaseMongoDao;
import com.wf2311.commons.persist.mongo.service.impl.BaseMongoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wf2311
 * @date 2016/03/25 20:01.
 */
@Service
public class AttachmentServiceImpl extends BaseMongoServiceImpl<Attachment,Long> implements AttachmentService {
    @Autowired
    private AttachmentDao attachmentDao;
    /**
     * @return
     */
    @Override
    public BaseMongoDao<Attachment, Long> baseMongoDao() {
        return attachmentDao;
    }
}
