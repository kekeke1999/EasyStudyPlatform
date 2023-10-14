package com.keke.entities;

import java.io.Serializable;
import java.util.Date;

public class Paths implements Serializable {

    private String localPath;

    private String beforePath;

    private String username;

    private Integer pathId;

    private Integer layer;

    private Date createTime;

    @Override
    public String toString() {
        return "Paths{" +
                "localPath='" + localPath + '\'' +
                ", beforePath='" + beforePath + '\'' +
                ", username='" + username + '\'' +
                ", pathId=" + pathId +
                ", layer=" + layer +
                ", createTime=" + createTime +
                '}';
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getBeforePath() {
        return beforePath;
    }

    public void setBeforePath(String beforePath) {
        this.beforePath = beforePath;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getPathId() {
        return pathId;
    }

    public void setPathId(Integer pathId) {
        this.pathId = pathId;
    }

    public Integer getLayer() {
        return layer;
    }

    public void setLayer(Integer layer) {
        this.layer = layer;
    }
}
