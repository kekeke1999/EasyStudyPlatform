package com.keke.entities;

import java.io.Serializable;
import java.util.Date;

public class Markdowns implements Serializable {

    private Integer mid;

    private String content;

    private String title;

    private Date createTime;

    private Date updateTime;

    private String username;

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Markdowns(String content, String title,  String username) {
        this.content = content;
        this.title = title;
        this.username = username;
    }
}
