package com.jj.investigation.customebehavior.bean;

import com.jj.investigation.customebehavior.http.DownLoadService;

import java.io.Serializable;

/**
 * Created by ${R.js} on 2018/3/22.
 */

public class DownloadInfo implements Serializable {

    private int id;
    /* 存储位置 */
    private String savePath;
    /* 文件总长度 */
    private long contentLength;
    /* 下载长度 */
    private long readLength;
    /* 下载该文件的url */
    private String url;
    private DownLoadService service;
    private String fileName;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public DownLoadService getService() {
        return service;
    }

    public void setService(DownLoadService service) {
        this.service = service;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public long getReadLength() {
        return readLength;
    }

    public void setReadLength(long readLength) {
        this.readLength = readLength;
    }

    @Override
    public String toString() {
        return "DownloadInfo{" +
                "savePath='" + savePath + '\'' +
                ", contentLength=" + contentLength +
                ", readLength=" + readLength +
                ", url='" + url + '\'' +
                '}';
    }
}

