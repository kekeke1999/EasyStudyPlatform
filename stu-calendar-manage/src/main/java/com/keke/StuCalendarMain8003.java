package com.keke;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.keke.mapper")
@ComponentScan("com.keke.controller")
@SpringBootApplication
public class StuCalendarMain8003 {
    public static void main(String[] args) {
        SpringApplication.run(StuCalendarMain8003.class, args);
    }
}
