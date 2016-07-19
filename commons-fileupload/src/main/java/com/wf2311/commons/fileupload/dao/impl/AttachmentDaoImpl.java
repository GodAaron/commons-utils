package com.wf2311.commons.fileupload.dao.impl;

import com.wf2311.commons.fileupload.dao.AttachmentDao;
import com.wf2311.commons.fileupload.domain.Attachment;
import com.wf2311.commons.persist.annotation.Repository;
import com.wf2311.commons.persist.mongo.dao.impl.BaseMongoDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @author wf2311
 * @date 2016/03/25 19:59.
 */
@Repository(value = "attachmentDao", entity = Attachment.class)
public class AttachmentDaoImpl extends BaseMongoDaoImpl<Attachment, Long> implements AttachmentDao {
    @Autowired
    @Qualifier("mongoTemplate")
    private MongoTemplate mongoTemplate;

    @Override
    public MongoTemplate mongoTemplate() {
        return mongoTemplate;
    }
}
