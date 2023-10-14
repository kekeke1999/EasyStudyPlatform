package com.keke.domain;

import java.io.Serializable;

public class MonthDatePlan implements Serializable {
    private String months;
    private String days;
    private String things;
    private Integer wid;

    @Override
    public String toString() {
        return "MonthDatePlan{" +
                "months='" + months + '\'' +
                ", days='" + days + '\'' +
                ", things='" + things + '\'' +
                '}';
    }

    public MonthDatePlan() {
    }

    public MonthDatePlan(String months, String days, String things) {
        this.months = months;
        this.days = days;
        this.things = things;
    }

    public Integer getWid() {
        return wid;
    }

    public void setWid(Integer wid) {
        this.wid = wid;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getThings() {
        return things;
    }

    public void setThings(String things) {
        this.things = things;
    }
}
