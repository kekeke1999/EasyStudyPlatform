package com.keke.entities;

import java.io.Serializable;
import java.sql.SQLTransactionRollbackException;
import java.util.Date;

public class Files implements Serializable {

    private String originalUrl;

    private String pdfUrl;

    private Date createTime;

    private String keyword;

    private Integer fid;

    private String filename;

    private String username;

    private Integer layer;

    private Integer click;

    private Date recentopen;

    public Date getRecentopen() {
        return recentopen;
    }

    public void setRecentopen(Date recentopen) {
        this.recentopen = recentopen;
    }

    public Files(String originalUrl, String pdfUrl, String filename, String username, Integer layer, Integer click) {
        this.originalUrl = originalUrl;
        this.pdfUrl = pdfUrl;
        this.filename = filename;
        this.username = username;
        this.layer = layer;
        this.click = click;
    }

    public Integer getClick() {
        return click;
    }

    public void setClick(Integer click) {
        this.click = click;
    }

    public Integer getLayer() {
        return layer;
    }

    public void setLayer(Integer layer) {
        this.layer = layer;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Files() {
    }

    public Files(String originalUrl, String pdfUrl, String filename) {
        this.originalUrl = originalUrl;
        this.pdfUrl = pdfUrl;
        this.filename = filename;
    }

    public Files(String originalUrl, String pdfUrl, String filename, String username) {
        this.originalUrl = originalUrl;
        this.pdfUrl = pdfUrl;
        this.filename = filename;
        this.username = username;
    }

    public Files(String originalUrl, String pdfUrl, String filename, String username, Integer layer) {
        this.originalUrl = originalUrl;
        this.pdfUrl = pdfUrl;
        this.filename = filename;
        this.username = username;
        this.layer = layer;
    }

    public Files(String originalUrl, String pdfUrl, Date createTime, String keyword, Integer fid, String filename, String username, Integer layer) {
        this.originalUrl = originalUrl;
        this.pdfUrl = pdfUrl;
        this.createTime = createTime;
        this.keyword = keyword;
        this.fid = fid;
        this.filename = filename;
        this.username = username;
        this.layer = layer;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public String toString() {
        return "Files{" +
                "originalUrl='" + originalUrl + '\'' +
                ", pdfUrl='" + pdfUrl + '\'' +
                ", createTime=" + createTime +
                ", keyword='" + keyword + '\'' +
                ", fid=" + fid +
                ", filename='" + filename + '\'' +
                ", username='" + username + '\'' +
                ", layer=" + layer +
                '}';
    }
}
