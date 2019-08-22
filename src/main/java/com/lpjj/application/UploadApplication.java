package com.lpjj.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

/**
 * com.lpjj.application
 * 黄新乐
 * 2019/6/21
 */

@SpringBootApplication

@ComponentScan(basePackages = {"com.lpjj.*"})
public class UploadApplication {

    public static void main(String[] args) {
        SpringApplication.run(UploadApplication.class, args);
    }
}
