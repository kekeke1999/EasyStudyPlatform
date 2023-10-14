package com.keke.service;

import com.keke.ResponseResult;
import com.keke.domain.Tags;
import com.keke.enums.ResponseCodeEnum;
import com.keke.mapper.TagsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagsService {

    @Autowired
    private TagsMapper tagsMapper;

    public ResponseResult insertTag(Tags tag){
        if(tagsMapper.insert(tag)==1){
            return ResponseResult.success();
        }else{
            return ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "插入失败！");
        }
    }


}
