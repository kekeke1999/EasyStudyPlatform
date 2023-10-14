package com.keke.mapper;

import com.keke.entities.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    Integer selectUser(@Param("username") String username, @Param("password") String password);

}
