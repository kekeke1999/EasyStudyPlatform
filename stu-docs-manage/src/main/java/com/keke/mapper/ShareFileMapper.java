package com.keke.mapper;

import com.keke.entities.Share;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ShareFileMapper {

    int insertShare(@Param("sid") String sid, @Param("fid") Integer fid, @Param("username") String username, @Param("shareUrl") String shareUrl);

    int deleteShare(@Param("sid") Integer sid);

    Share getShare(@Param("sid") String sid);



}
