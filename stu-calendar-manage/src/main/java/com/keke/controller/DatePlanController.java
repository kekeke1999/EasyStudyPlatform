package com.keke.controller;

import com.keke.ResponseResult;
import com.keke.domain.DatePlan;
import com.keke.service.DatePlanService;
import com.keke.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.text.ParseException;
import java.util.Date;

@RestController
@RequestMapping("/calendarManage")
public class DatePlanController {

    @Autowired
    private DatePlanService datePlanService;

    @RequestMapping(value = "/datePlan", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseResult insertDatePlan(@RequestHeader("username") String username, @RequestParam("beginTime") Date beginTime, @RequestParam("endTime") Date endTime, @RequestParam("content")  String content,@RequestParam("tag")  String tag,@RequestParam("main") Integer main ) throws ParseException {
        TimeUtils timeUtils = new TimeUtils();
        Time lastTime = timeUtils.getDifference(endTime, beginTime);
        DatePlan datePlan = new DatePlan(beginTime,endTime,lastTime,content,username,tag,main);
        return datePlanService.insertDatePlan(datePlan);
    }

    @RequestMapping(value = "/datePlan", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
    public ResponseResult deleteDatePlan(@RequestHeader("username") String username, @RequestParam("hid") Integer hid){
        return datePlanService.deleteDatePlan(username, hid);
    }

    @RequestMapping(value = "/datePlans", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseResult selectByDate(@RequestParam("date") String date, @RequestHeader("username") String username){
        return datePlanService.selectByDate(date, username);
    }

    @RequestMapping(value = "/todayPlans", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseResult selectToday(@RequestParam("date") String date, @RequestHeader("username") String username){
        return datePlanService.selectByDate(new TimeUtils().getToday(), username);
    }

    @RequestMapping(value = "/datePlan", method = RequestMethod.PUT, produces="application/json;charset=UTF-8")
    public ResponseResult updateDatePlan (@RequestHeader("username") String username, @RequestParam("beginTime") Date beginTime, @RequestParam("endTime") Date endTime, @RequestParam("content")  String content, @RequestParam("tag")  String tag, @RequestParam("main") Integer main ) throws ParseException {
        TimeUtils timeUtils = new TimeUtils();
        Time lastTime = timeUtils.getDifference(endTime, beginTime);
        DatePlan datePlan = new DatePlan(beginTime,endTime,lastTime,content,username,tag,main);
        return datePlanService.updateDatePlan(datePlan);
    }

}
