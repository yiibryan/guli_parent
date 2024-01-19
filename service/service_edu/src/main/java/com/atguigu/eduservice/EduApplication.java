package com.atguigu.eduservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.atguigu"})
public class EduApplication{
    public static void main(String[] args) {
        Logger log = LoggerFactory.getLogger(EduApplication.class);
        log.info("============SpringBoot Starting=========");
        SpringApplication.run(EduApplication.class, args);
        log.info("============SpringBoot Start Success===========");
    }
}
