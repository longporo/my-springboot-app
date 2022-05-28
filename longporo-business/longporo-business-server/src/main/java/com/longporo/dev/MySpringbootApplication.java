package com.longporo.dev;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * My SpringBoot Application<br>
 *
 * @param
 * @return
 * @author Zihao Long
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableScheduling
@Slf4j
public class MySpringbootApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(MySpringbootApplication.class, args);
        Environment env = application.getEnvironment();
        log.info("\n----------------------------------------------------------\n\t" +
                        "Server '{}' launched successfully! \n" +
                        "----------------------------------------------------------",
                env.getProperty("spring.application.name"));
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MySpringbootApplication.class);
    }

}
