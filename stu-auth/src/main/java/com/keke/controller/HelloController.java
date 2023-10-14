package com.keke.controller;

import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/sayHello")
    public String sayHello(String name) {
        return "Hello, " + name;
    }


    @GetMapping("/sayHi")
    public String sayHi(@RequestHeader("username") String username) {
        return username;
    }

    @GetMapping("/say")
    public String say() {
        return "username";
    }
}
