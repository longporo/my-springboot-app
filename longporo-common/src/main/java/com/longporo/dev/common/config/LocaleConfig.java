package com.longporo.dev.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * I18n config<br>
 *
 * @param 
 * @return 
 * @author Zihao Long
 */
@Configuration
public class LocaleConfig {

    /**
     * Set default locale<br>
     *
     * @param []
     * @return org.springframework.web.servlet.LocaleResolver
     * @author Zihao Long
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.US);
        return localeResolver;
    }

    /**
     * Set interceptor to support specify language with parameter name lang<br>
     *
     * @param []
     * @return org.springframework.web.servlet.config.annotation.WebMvcConfigurer
     * @author Zihao Long
     */
    @Bean
    public WebMvcConfigurer localeInterceptor() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
                localeInterceptor.setParamName("lang");
                registry.addInterceptor(localeInterceptor);
            }
        };
    }
}
