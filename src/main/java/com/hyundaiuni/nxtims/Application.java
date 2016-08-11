package com.hyundaiuni.nxtims;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Configuration
    @MapperScan(basePackages = "com.hyundaiuni.nxtims.mapper")
    protected static class MybatisConfiguration {}
}
