package com.keke.domain;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public class DatePlan implements Serializable {
    private Integer hid;

    private Date beginTime;

    private Date endTime;

    private Time lastTime;

    private String content;

    private String username;

    private String tag;

    private Integer main;

    public DatePlan(Date beginTime, Date endTime, Time lastTime, String content, String username, String tag, Integer main) {
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.lastTime = lastTime;
        this.content = content;
        this.username = username;
        this.tag = tag;
        this.main = main;
    }

    public Integer getHid() {
        return hid;
    }

    public void setHid(Integer hid) {
        this.hid = hid;
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

    public Integer getMain() {
        return main;
    }

    public void setMain(Integer main) {
        this.main = main;
    }
}
