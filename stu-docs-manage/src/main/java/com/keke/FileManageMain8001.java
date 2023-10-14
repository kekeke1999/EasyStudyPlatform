package com.keke;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.keke.mapper")
public class FileManageMain8001 {
    public static void main(String[] args) {
        SpringApplication.run(FileManageMain8001.class, args);
    }
}
