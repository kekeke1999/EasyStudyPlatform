package com.keke.mapper;

import com.keke.domain.MonthPlan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface MonthPlanMapper {

    int deleteDatePlan(@Param("wid") Integer wid,@Param("username") String username);

    int insertDatePlan(MonthPlan monthPlan);

    int updateDatePlan(MonthPlan monthPlan);

    List<MonthPlan> selectMonthplans(@Param("month") String month, @Param("username") String username);

    List<MonthPlan> selectAllMonthplans(@Param("username") String username);

    int deleteByPrimaryKey(Integer wid);

    int insert(MonthPlan record);

    int insertSelective(MonthPlan record);

    MonthPlan selectByPrimaryKey(Integer wid);

    int updateByPrimaryKeySelective(MonthPlan record);

    int updateByPrimaryKey(MonthPlan record);
}
