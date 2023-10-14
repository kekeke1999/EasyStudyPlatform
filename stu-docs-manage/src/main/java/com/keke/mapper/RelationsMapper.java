package com.keke.mapper;

import com.keke.entities.Connect;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface RelationsMapper {

    int deleteRelation(Integer rid);

    List<Connect> selectAllRelations(@Param("username") String username,@Param("mid") Integer mid);

    int addRelation(@Param("username") String username, @Param("mid") Integer mid, @Param("mainfile") String mainfile, @Param("refile") String refile, @Param("fid") Integer fid);

}
