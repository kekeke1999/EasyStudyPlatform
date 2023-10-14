package com.keke.domain;

import java.util.Date;

public class MonthPlan {
    private Integer wid;

    private String content;

    private Date date;

    private String username;

    public MonthPlan(String content, Date date, String username) {
        this.content = content;
        this.date = date;
        this.username = username;
    }

    public MonthPlan(Integer wid, String content, Date date, String username) {
        this.wid = wid;
        this.content = content;
        this.date = date;
        this.username = username;
    }

    public Integer getWid() {
        return wid;
    }

    public void setWid(Integer wid) {
        this.wid = wid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    @Override
    public String toString() {
        return "MonthPlan{" +
                "wid=" + wid +
                ", content='" + content + '\'' +
                ", date=" + date +
                ", username='" + username + '\'' +
                '}';
    }
}
