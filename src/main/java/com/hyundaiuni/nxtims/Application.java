package com.hyundaiuni.nxtims;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableAsync
public class Application {
    @Value("${system.task-executor.max-pool-size}")
    private int maxPoolSize;
    
    @Value("${system.task-executor.queue-capacity}")
    private int queueCapacity;    
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Configuration
    @MapperScan(basePackages = "com.hyundaiuni.nxtims.mapper")
    protected static class MybatisConfiguration {}
    
    @Bean(name="taskExecutor")
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

        taskExecutor.setMaxPoolSize(maxPoolSize);
        taskExecutor.setQueueCapacity(queueCapacity);
        taskExecutor.afterPropertiesSet();

        return taskExecutor;
    }    
}
