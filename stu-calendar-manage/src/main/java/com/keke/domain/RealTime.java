package com.keke.domain;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public class RealTime implements Serializable {
    private Integer rid;

    private Date beginTime;

    private Date endTime;

    private Time lastTime;

    private String content;

    private String username;

    private String tag;

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Time lastTime) {
        this.lastTime = lastTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    public RealTime(Date beginTime, Date endTime, Time lastTime, String content, String username, String tag) {
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.lastTime = lastTime;
        this.content = content;
        this.username = username;
        this.tag = tag;
    }
}
