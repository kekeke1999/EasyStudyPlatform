package com.keke.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PicturesMapper {

    int insertPictures(@Param("url") String url, @Param("pname") String pname);
}
