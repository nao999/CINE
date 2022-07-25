package com.onlinechat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName : OnlineChatApplication
 * @Description :
 * @Author : y
 * @Date: 2021-10-15 20:07
 */

@MapperScan("com.onlinechat.mapper")
@SpringBootApplication
public class OnlineChatApplication {
    public static void main(String[] args) {
        SpringApplication.run(OnlineChatApplication.class,args);
    }
}
