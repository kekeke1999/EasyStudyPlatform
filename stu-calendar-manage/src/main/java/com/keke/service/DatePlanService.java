package com.keke.service;

import com.keke.ResponseResult;
import com.keke.domain.DatePlan;
import com.keke.enums.ResponseCodeEnum;
import com.keke.mapper.DatePlanMapper;
import com.keke.mapper.MonthPlanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatePlanService {

    @Autowired
    private DatePlanMapper datePlanMapper;

    @Autowired
    private MonthPlanMapper monthPlanMapper;

    public ResponseResult insertDatePlan(DatePlan datePlan){
        if(datePlan.getMain()==1){
            datePlanMapper.updateMain(datePlan.getUsername());
        }
        return datePlanMapper.insertDatePlan(datePlan) == 1 ? ResponseResult.success() : ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "插入失败！");
    }

    public ResponseResult selectByDate(String date, String username){
        List<DatePlan> datePlans  = datePlanMapper.selectByDate(date, username);
        return datePlans == null ? ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "未查到数据！") : ResponseResult.success(datePlans);
    }

    public ResponseResult updateDatePlan(DatePlan datePlan){
        return datePlanMapper.updateDatePlan(datePlan) == 1 ? ResponseResult.success() : ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "更新失败！");
    }

    public ResponseResult deleteDatePlan(String username, Integer hid){
        return datePlanMapper.deleteDatePlan(username, hid) == 1 ? ResponseResult.success() : ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "删除失败！");
    }

}
