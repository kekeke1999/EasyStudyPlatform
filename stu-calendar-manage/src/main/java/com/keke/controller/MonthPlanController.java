package com.keke.controller;

import com.keke.ResponseResult;
import com.keke.domain.MonthDatePlan;
import com.keke.domain.MonthPlan;
import com.keke.service.MonthPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/calendarManage")
public class MonthPlanController {

    @Autowired
    private MonthPlanService monthPlanService;

    @ResponseBody
    @RequestMapping(value = "/deleteMonthPlan",produces="application/json;charset=UTF-8")
    public ResponseResult deleteDatePlan(@RequestParam("wid") Integer wid, @RequestHeader("username") String username){
        return monthPlanService.deleteDatePlan(wid, username);
    }

    @ResponseBody
    @RequestMapping(value = "/insertMonthPlan",produces="application/json;charset=UTF-8")
    public ResponseResult insertDatePlan(@RequestParam("content") String content, @RequestParam("date") String date, @RequestHeader("username") String username){
        System.out.println("date:"+date);
        System.out.println("我在新增！");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateTime = null;
        try {
            dateTime = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return monthPlanService.insertDatePlan(new MonthPlan(content, dateTime, username));
    }

    @ResponseBody
    @RequestMapping(value = "/updateMonthPlan",produces="application/json;charset=UTF-8")
    public ResponseResult updateDatePlan(@RequestParam("wid") Integer wid,@RequestParam("content") String content, @RequestParam("date") String date, @RequestHeader("username") String username){
        System.out.println("date:"+date);
        System.out.println("我在修改！");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateTime = null;
        try {
            dateTime = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return monthPlanService.updateDatePlan(new MonthPlan(wid,content, dateTime, username));
    }

    @ResponseBody
    @RequestMapping(value = "/monthPlan", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
    public ResponseResult selectMonthplans(@RequestHeader("username") String username,@RequestParam("date") String date){
        List<MonthPlan> monthPlans = monthPlanService.selectMonthplans(date, username);
        System.out.println("monthPlans:"+monthPlans);
        System.out.println("date:"+date);
        if(monthPlans.isEmpty()){
            return ResponseResult.success();
        }
        else{
            MonthDatePlan monthDatePlan = new MonthDatePlan();
            for (MonthPlan m :
                    monthPlans) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String str=df.format(m.getDate());

                Integer wid = m.getWid();

                System.out.println("str:"+str+"\n\n\n");

                String months = str.split("-")[1];

                String days = str.split("-")[2];

                monthDatePlan.setWid(wid);
                monthDatePlan.setDays(days);
                monthDatePlan.setMonths(months);

                monthDatePlan.setThings(m.getContent());
            }
            System.out.println("monthPlan:"+monthDatePlan);
            return ResponseResult.success(monthDatePlan);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/monthPlans", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
    public ResponseResult selectAllMonthplans(@RequestHeader("username") String username){
        List<MonthPlan> list = monthPlanService.selectAll(username);
        //   System.out.println("list:"+list);
        List<MonthDatePlan> result = new LinkedList<>();

        for (MonthPlan m :
                list) {
            MonthDatePlan monthDatePlan = new MonthDatePlan();

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String str=df.format(m.getDate());

            System.out.println("str:"+str+"\n\n\n");

            String months = str.split("-")[1];

            String days = str.split("-")[2];

            monthDatePlan.setDays(days);
            monthDatePlan.setMonths(months);

            monthDatePlan.setThings(m.getContent());
            result.add(monthDatePlan);
        }
        System.out.println("result:"+result);
        return ResponseResult.success(result);
    }

    @ResponseBody
    @GetMapping("/allMonthPlans")
    public ResponseResult selectMonthplanTest() throws ParseException {
        List<MonthPlan> list = monthPlanService.selectAll("kk");
     //   System.out.println("list:"+list);
        List<MonthDatePlan> result = new LinkedList<>();

        for (MonthPlan m :
                list) {
            HashMap<String, String> hashMap = new LinkedHashMap<>();
            MonthDatePlan monthDatePlan = new MonthDatePlan();

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String str=df.format(m.getDate());

            System.out.println("str:"+str+"\n\n\n");

            String months = str.split("-")[1];

            String days = str.split("-")[2];

            monthDatePlan.setDays(days);
            monthDatePlan.setMonths(months);

            monthDatePlan.setThings(m.getContent());
            result.add(monthDatePlan);
        }
        System.out.println("result:"+result);
        return ResponseResult.success(result);
    }
}
