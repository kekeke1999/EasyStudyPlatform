package com.keke.mapper;

import com.keke.domain.RealTime;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface RealTimeMapper {

    int insertRealTime(RealTime realTime);

    int deleteRealTime(@Param("username") String username, @Param("rid") Integer rid);

    int updateRealTime(RealTime realTime);

    List<RealTime> selectByDate(@Param("date") String date, @Param("username") String username);





    int deleteByPrimaryKey(Integer rid);

    int insert(RealTime record);

    int insertSelective(RealTime record);

    RealTime selectByPrimaryKey(Integer rid);

    int updateByPrimaryKeySelective(RealTime record);

    int updateByPrimaryKey(RealTime record);
}
