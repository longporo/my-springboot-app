package com.longporo.dev.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * Math Utils<br>
 *
 * @param 
 * @return 
 * @author Zihao Long
 */
public class MathUtils {
    private static Logger logger = LoggerFactory.getLogger(MathUtils.class);

    public static BigDecimal nullToZero(BigDecimal target){
        if(target == null){
            return BigDecimal.ZERO;
        }
        return target;
    }

    public static Double nullToZero(Double target){
        if(target == null){
            return 0D;
        }
        return target;
    }

    public static Long nullToZero(Long target){
        if(target == null){
            return 0L;
        }
        return target;
    }

    public static Integer nullToZero(Integer target){
        if(target == null){
            return 0;
        }
        return target;
    }
}