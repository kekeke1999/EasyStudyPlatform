package com.keke.mapper;

import com.keke.entities.Files;
import com.keke.entities.Share;
import com.keke.entities.ShareDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ShareFileMapper {

    int insertShare(@Param("sid") String sid, @Param("fid") Integer fid, @Param("username") String username, @Param("shareUrl") String shareUrl);

    int deleteShare(@Param("sid") String sid);

    Share getShare(@Param("sid") String sid);

    ShareDetail getShareDetail(@Param("sid") String sid);

    Files getFileDetail(@Param("fid") Integer fid);

    int insertFiles(Files files);

}
