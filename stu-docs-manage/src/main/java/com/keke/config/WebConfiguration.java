package com.keke.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.MultipartConfigElement;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        //上传的图片在D盘下的downLoad目录下，访问路径如：http://localhost:8899/downLoad/d3cf0281-bb7f-40e0-ab77-406db95ccf2c.jpg
        //file作用 指定文件传输协议 获取电脑文件一般都是file
        registry.addResourceHandler("/docs/**").addResourceLocations("file:////Users/uu/Desktop/1/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

}
