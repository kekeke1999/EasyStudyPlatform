package com.keke.controller;

import com.keke.ResponseResult;
import com.keke.service.MarkdownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MarkdownController {

    @Autowired
    private MarkdownService markdownService;

    @GetMapping("/file/markdowns")
    public ResponseResult getAllMarkdowns(@RequestHeader("username") String username){
        return ResponseResult.success(markdownService.getAllMarkdowns(username));
    }
}
