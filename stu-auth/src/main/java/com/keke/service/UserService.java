package com.keke.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.keke.entities.User;
import com.keke.mapper.UserMapper;
import com.keke.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public Integer login(String username, String password) {
        System.out.println(username+"jjj"+password);
        if(userMapper.selectUser(username, password)==1){
            return 1;
        }
        return 0;
    }
}
