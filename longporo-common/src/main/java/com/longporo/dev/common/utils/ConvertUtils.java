package com.longporo.dev.common.utils;

import com.google.common.collect.Maps;
import com.longporo.dev.common.exception.ErpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanMap;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * ConverUtils<br>
 *
 * @param
 * @author Zihao Long
 * @return
 */
public class ConvertUtils {
    private static Logger logger = LoggerFactory.getLogger(ConvertUtils.class);

    public static <T> T sourceToTarget(Object source, Class<T> target) {
        if (source == null) {
            return null;
        }
        T targetObject = null;
        try {
            targetObject = target.newInstance();
            BeanUtils.copyProperties(source, targetObject);
        } catch (Exception e) {
            logger.error("convert error ", e);
        }

        return targetObject;
    }

    /**
     * Bean to Map<br>
     *
     * @param [bean]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @author Zihao Long
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = Maps.newHashMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key + "", beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * Set value to object<br>
     *
     * @param [obj, fieldMap]
     * @return void
     * @author Zihao Long
     */
    public static void setValueToObject(Object obj, Map<String, Object> fieldMap) {
        Class<?> clazz = obj.getClass();
        fieldMap.forEach((k, v) -> {
            Field field = null;
            try {
                field = clazz.getDeclaredField(k);
            } catch (NoSuchFieldException e) {
                throw new ErpException(e.getMessage());
            }
            field.setAccessible(true);
            try {
                field.set(obj, v);
            } catch (IllegalAccessException e) {
                throw new ErpException(e.getMessage());
            }
        });
    }

    /**
     * Source to target<br>
     *
     * @param [sourceList, target]
     * @return java.util.List<T>
     * @author Zihao Long
     */
    public static <T> List<T> sourceToTarget(Collection<?> sourceList, Class<T> target) {
        if (sourceList == null) {
            return null;
        }

        List targetList = new ArrayList<>(sourceList.size());
        try {
            for (Object source : sourceList) {
                T targetObject = target.newInstance();
                BeanUtils.copyProperties(source, targetObject);
                targetList.add(targetObject);
            }
        } catch (Exception e) {
            logger.error("convert error ", e);
        }

        return targetList;
    }
}