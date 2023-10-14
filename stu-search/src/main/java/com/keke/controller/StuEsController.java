package com.keke.controller;

import com.keke.ResponseResult;
import com.keke.entities.Files;
import com.keke.entities.FilesNew;
import com.keke.service.StuEsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@RestController
public class StuEsController {

    @Autowired
    private StuEsService stuEsService;

    @GetMapping("/search/all")
    public ResponseResult selectKeyword(@RequestParam("word") String word, @RequestHeader("username") String username) throws IOException {
        List<Files> list = stuEsService.selectKeyword(word, username);
        List<FilesNew> results = new LinkedList<>();
        for (Files f :
                list) {
            String location = f.getOriginalUrl().replace("/"+f.getFilename(),"").replace("http://localhost:8002/docs","");
            FilesNew fn = new FilesNew(f,location);
            results.add(fn);
        }
        return ResponseResult.success(results);
    }

}
