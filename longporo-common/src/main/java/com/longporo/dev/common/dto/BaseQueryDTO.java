package com.longporo.dev.common.dto;


import com.google.common.collect.Maps;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Base Sort Query DTO<br>
 *
 * @param 
 * @return 
 * @author Zihao Long
 */
public class BaseQueryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Get order by SQL<br>
     *
     * @param [dtos]
     * @return java.lang.String
     * @author Zihao Long
     */
    public String getSortStr(BaseSortDTO... dtos) {
        if (null != dtos && dtos.length > 0) {
            Map<String, Integer> map = Maps.newHashMap();
            Arrays.asList(dtos).stream().forEach(dto -> {
                if (null != dto) {
                    map.putAll(dto.getSortMap());
                }
            });
            if (map.isEmpty()) {
                return null;
            }
            LinkedHashMap<String, Integer> collect = map.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

            StringBuffer sb = new StringBuffer();
            if (!collect.isEmpty()) {
                collect.forEach((k, v) -> {
                    // append orderBy SQL
                    if (v % 2 != 0) {
                        // odd: desc
                        sb.append(k + " " + "DESC" + ",");
                    } else {
                        // even: asc
                        sb.append(k + ",");
                    }
                });
                return sb.substring(0, sb.length() - 1);
            }
        }
        return null;
    }
}
