package com.longporo.dev.common.intercepter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Config MVC interceptor<br>
 *
 * @param
 * @return
 * @author Zihao Long
 */
@Configuration
public class MvcConfigure implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MvcInterceptor());
    }
}
