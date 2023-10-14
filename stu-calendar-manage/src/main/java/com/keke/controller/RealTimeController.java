package com.keke.controller;

import com.keke.ResponseResult;
import com.keke.domain.RealTime;
import com.keke.service.RealTimeService;
import com.keke.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.text.ParseException;
import java.util.Date;

@CrossOrigin
@RestController
@RequestMapping("/calendarManage")
public class RealTimeController {

    @Autowired
    private RealTimeService realTimeService;

    @RequestMapping(value = "/realTime", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseResult insertRealTime(@RequestHeader("username") String username, @RequestParam("beginTime") Date beginTime, @RequestParam("endTime") Date endTime, @RequestParam("content")  String content,@RequestParam("tag")  String tag) throws ParseException {
        TimeUtils timeUtils = new TimeUtils();
        Time lastTime = timeUtils.getDifference(endTime, beginTime);
        RealTime realTime = new RealTime(beginTime,endTime,lastTime,content,username,tag);
        return realTimeService.insertRealTime(realTime);
    }

    @RequestMapping(value = "/realTime", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
    public ResponseResult deleteRealTime(@RequestHeader("username") String username, @RequestParam("rid") Integer rid){
        return realTimeService.deleteRealTime(username, rid);
    }

    @RequestMapping(value = "/realTimes", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseResult selectByDate(@RequestParam("date") String date, @RequestHeader("username") String username){
        return realTimeService.selectByDate(date, username);
    }

    @RequestMapping(value = "/todayAllPlans", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseResult selectTodayPlanss(@RequestParam("date") String date, @RequestHeader("username") String username){
        return realTimeService.selectByDate(new TimeUtils().getToday(), username);
    }

    @RequestMapping(value = "/realTime", method = RequestMethod.PUT, produces="application/json;charset=UTF-8")
    public ResponseResult updateDatePlan (@RequestHeader("username") String username, @RequestParam("beginTime") Date beginTime, @RequestParam("endTime") Date endTime, @RequestParam("content")  String content, @RequestParam("tag")  String tag, @RequestParam("main") Integer main ) throws ParseException {
        TimeUtils timeUtils = new TimeUtils();
        Time lastTime = timeUtils.getDifference(endTime, beginTime);
        RealTime realTime = new RealTime(beginTime,endTime,lastTime,content,username,tag);
        return realTimeService.updateRealTime(realTime);
    }

}
