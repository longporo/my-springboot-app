package com.longporo.dev.common.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * LogService<br>
 *
 * @param
 * @return
 * @author Zihao Long
 */
@Component
@Slf4j
public class LogService {

    /**
     * Log into file
     * @param msg
     */
    @Async
    public void record(String msg){
        log.info(msg);
    }
}
