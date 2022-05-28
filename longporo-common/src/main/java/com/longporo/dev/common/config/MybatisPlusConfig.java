package com.longporo.dev.common.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * Mybatis-plus Config<br>
 *
 * @param
 * @return
 * @author Zihao Long
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * page query
     */
    @Bean
    @Order(0)
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}