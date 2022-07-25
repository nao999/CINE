package com.onlinechat.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName : OnlineChatConfig
 * @Description :
 * @Author : y
 * @Date: 2021-10-15 20:11
 */

@Configuration
public class OnlineChatConfig {
    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}
