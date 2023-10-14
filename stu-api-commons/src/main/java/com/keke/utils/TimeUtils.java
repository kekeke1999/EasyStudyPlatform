package com.keke.utils;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 描述：Java计算时间差（两个时间相减）
 */
public class TimeUtils {

    public Time getDifference(Date after, Date before) throws ParseException {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date afterDate = df.parse(df.format(after));
        Date beforeDate = df.parse(df.format(before));
        long diff = afterDate.getTime() - beforeDate.getTime();//这样得到的差值是微秒级别
        long days = diff / (1000 * 60 * 60 * 24);
        long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
        long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
        long seconds = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60)-minutes*(1000*60))/1000;

        Time time = new Time((int)hours,(int)minutes,(int)seconds);
        System.out.println(""+time.getDay()+"天"+time.getHours()+"小时"+time.getMinutes()+"分"+time.getSeconds()+"秒");
        return time;
    }

    public String getToday(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = new Date();
        return df.format(d1);
    }


    public static void main(String[] args) throws ParseException {
      /*  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            Date d1 = df.parse("2004-03-26 13:31:50");
            Date d2 = df.parse("2004-03-26 14:31:40");
            long diff = d2.getTime() - d1.getTime();//这样得到的差值是微秒级别
            long days = diff / (1000 * 60 * 60 * 24);

            long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
            long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
            long seconds = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60)-minutes*(1000*60))/1000;
            Time time = new Time((int)hours,(int)minutes,(int)seconds);
            System.out.println("timeMins:"+time.getMinutes());

            System.out.println(""+days+"天"+hours+"小时"+minutes+"分"+seconds+"秒");
        }catch (Exception e)
        {
        }*/
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = new Date();
        String format = df.format(d1);
        System.out.println("format:"+format);
    }

}

