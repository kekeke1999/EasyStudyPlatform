package com.keke;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@MapperScan("com.keke.mapper")
@SpringBootApplication
public class StuAuthMain8081 {
    public static void main(String[] args) {
        SpringApplication.run(StuAuthMain8081.class, args);
    }
}
