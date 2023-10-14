package com.keke.service;

import com.keke.ResponseResult;
import com.keke.domain.MonthPlan;
import com.keke.enums.ResponseCodeEnum;
import com.keke.mapper.MonthPlanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonthPlanService {

    @Autowired
    private MonthPlanMapper monthPlanMapper;

    public List selectAll(String username){
        return monthPlanMapper.selectAllMonthplans(username);
    }

    public ResponseResult deleteDatePlan(Integer wid, String username){
        return monthPlanMapper.deleteDatePlan(wid, username) == 1 ? ResponseResult.success() : ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "删除失败！");
    }

    public ResponseResult insertDatePlan(MonthPlan monthPlan){
        return monthPlanMapper.insertDatePlan(monthPlan) == 1 ? ResponseResult.success() : ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "插入失败！");
    }

    public ResponseResult updateDatePlan(MonthPlan monthPlan){
        return monthPlanMapper.updateDatePlan(monthPlan) == 1 ? ResponseResult.success() : ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "更新失败！");
    }

    public List<MonthPlan> selectMonthplans(String month, String username){
        return monthPlanMapper.selectMonthplans(month, username);
    }


}
