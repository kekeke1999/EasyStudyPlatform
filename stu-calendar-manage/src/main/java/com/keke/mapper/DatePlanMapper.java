package com.keke.mapper;

import com.keke.domain.DatePlan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface DatePlanMapper {

    int insertDatePlan(DatePlan datePlan);

    int deleteDatePlan(@Param("username") String username, @Param("hid") Integer hid);

    int updateDatePlan(DatePlan datePlan);

    List<DatePlan> selectByDate(@Param("date") String date, @Param("username") String username);

    int updateMain(String username);




    int deleteByPrimaryKey(Integer hid);

    int insert(DatePlan record);

    int insertSelective(DatePlan record);

    DatePlan selectByPrimaryKey(Integer hid);

    int updateByPrimaryKeySelective(DatePlan record);

    int updateByPrimaryKey(DatePlan record);
}
