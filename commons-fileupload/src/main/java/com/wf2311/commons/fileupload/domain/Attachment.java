package com.wf2311.commons.fileupload.domain;

import com.wf2311.commons.persist.annotation.Domain;
import com.wf2311.commons.persist.annotation.Primary;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wf2311
 * @date 2016/03/25 19:58.
 */
@Document
@Domain
@Data
@ToString
public class Attachment implements Serializable {

    /**
     * 主键
     */
    @Id
    @Primary
    String fid;
    /**
     * uid,唯一值,用于下载
     */
    String uid;
    /**
     * 文件名(包含后缀)
     */
    String fileName;
    /**
     * 保存路径
     */
    String savePath;
    /**
     * 文件名
     */
    String suffix;
    /**
     * 上传时间
     */
    Date created;
    /**
     * 文件大小
     */
    Long size;
    /**
     * md5码
     */
    String md5;
    /**
     * 文件下载次数
     */
    Long downloadCount;
    /**
     * 文件类型
     */
    String baseType;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Long getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Long downloadCount) {
        this.downloadCount = downloadCount;
    }

    public String getBaseType() {
        return baseType;
    }

    public void setBaseType(String baseType) {
        this.baseType = baseType;
    }
}
