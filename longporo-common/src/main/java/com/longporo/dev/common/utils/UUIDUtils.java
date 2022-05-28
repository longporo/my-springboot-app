package com.longporo.dev.common.utils;

import java.util.UUID;

/**
 * UUID Utils<br>
 *
 * @param
 * @return
 * @author Zihao Long
 */
public class UUIDUtils {

    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
