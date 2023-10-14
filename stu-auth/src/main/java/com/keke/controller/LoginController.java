package com.keke.controller;

import com.keke.ResponseResult;
import com.keke.enums.ResponseCodeEnum;
import com.keke.service.UserService;
import com.keke.utils.JWTUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${secretKey:123456}")
    private String secretKey;

    @PostMapping("/login")
    public ResponseResult login(@RequestParam("username") @Validated String username, @RequestParam("password") @Validated String password){

        if(userService.login(username,password) == 1){
            //  生成Token
            String token = JWTUtils.sign(username);
            //  生成刷新Token
            String refreshToken = UUID.randomUUID().toString().replace("-", "");

            //  放入缓存
        //    HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();

        //    hashOperations.put(username, "token", token);
         //   hashOperations.put(username, "refreshToken",refreshToken);
          //  stringRedisTemplate.expire(username, 10*60*60*1000, TimeUnit.MILLISECONDS);

            HashMap<String, String> loginResponse = new HashMap<>();
            loginResponse.put("token",token);
         //   loginResponse.put("refreshToken",refreshToken);
            loginResponse.put("username",username);
            return ResponseResult.success(loginResponse);
        }

        return ResponseResult.error(ResponseCodeEnum.LOGIN_ERROR.getCode(), ResponseCodeEnum.LOGIN_ERROR.getMessage());

    }

    @GetMapping("/logout")
    public ResponseResult logout(@RequestParam("username") String username) {
        HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();
        hashOperations.delete(username);
        return ResponseResult.success();
    }

    @PostMapping("/refreshToken")
    public ResponseResult refreshToken(@RequestParam("username") String username, @RequestParam("refreshToken") String refreshToken) {

        HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();
        String originalRefreshToken = hashOperations.get(username, "refreshToken");
        if (StringUtils.isBlank(originalRefreshToken) || !originalRefreshToken.equals(refreshToken)) {
            return ResponseResult.error(ResponseCodeEnum.REFRESH_TOKEN_INVALID.getCode(), ResponseCodeEnum.REFRESH_TOKEN_INVALID.getMessage());
        }
        //  生成新token
        String newToken = JWTUtils.sign(username);
        hashOperations.put(username, "token", newToken);
        stringRedisTemplate.expire(username, 10*60*60*1000, TimeUnit.MILLISECONDS);

        return ResponseResult.success(newToken);
    }
}
