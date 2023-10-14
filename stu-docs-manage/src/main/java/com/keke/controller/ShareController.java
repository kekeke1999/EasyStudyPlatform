package com.keke.controller;

import com.keke.ResponseResult;
import com.keke.service.ShareService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ShareController {

    @Autowired
    private ShareService shareService;

    @PostMapping("/insertShare")
    public ResponseResult insertShare(@RequestParam("fid") Integer fid, @RequestHeader("username") String username){
        return ResponseResult.success(shareService.insertShare(fid, username));
    }

    @GetMapping("/share")
    public ResponseResult shareUrl(@RequestParam("sid") String sid){
        return ResponseResult.success(shareService.getShare(sid).toString());
    }

    @PostMapping("/deleteShare")
    public ResponseResult deleteShare(@RequestParam("sid") Integer sid){
        if(shareService.deleteShare(sid) == 1){
            return ResponseResult.success();
        }else{
            return ResponseResult.error();
        }
    }


}
