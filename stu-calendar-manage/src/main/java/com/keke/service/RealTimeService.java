package com.keke.service;

import com.keke.ResponseResult;
import com.keke.domain.RealTime;
import com.keke.enums.ResponseCodeEnum;
import com.keke.mapper.RealTimeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RealTimeService {

    @Autowired
    private RealTimeMapper realTimeMapper;

    public ResponseResult insertRealTime(RealTime realTime){
        return realTimeMapper.insertRealTime(realTime) == 1 ? ResponseResult.success() : ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "插入失败！");
    }

    public ResponseResult selectByDate(String date, String username){
        List<RealTime> realTimes  = realTimeMapper.selectByDate(date, username);
        return realTimes == null ? ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "未查到数据！") : ResponseResult.success(realTimes);
    }

    public ResponseResult updateRealTime(RealTime realTime){
        return realTimeMapper.updateRealTime(realTime) == 1 ? ResponseResult.success() : ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "更新失败！");
    }

    public ResponseResult deleteRealTime(String username, Integer rid){
        return realTimeMapper.deleteRealTime(username, rid) == 1 ? ResponseResult.success() : ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "删除失败！");
    }

}
