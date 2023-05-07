package com.gym;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: 71made
 * @Date: 2023/05/06 12:26
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
@SpringBootApplication
@MapperScan("com.gym.server.mapper")
public class GYMServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(GYMServerApplication.class, args);
    }
}
