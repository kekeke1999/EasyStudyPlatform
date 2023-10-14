package com.keke.mapper;

import com.keke.domain.Tags;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface TagsMapper {
    int deleteByPrimaryKey(Integer tid);

    int insert(Tags record);

    int insertSelective(Tags record);

    Tags selectByPrimaryKey(Integer tid);

    int updateByPrimaryKeySelective(Tags record);

    int updateByPrimaryKey(Tags record);
}
