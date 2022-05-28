package com.longporo.dev.common.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * Base Sort DTO<br>
 *
 * @param
 * @author Zihao Long
 * @return
 */
public abstract class BaseSortDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Get Sort Map
     *
     * @return
     */
    public abstract Map<String, Integer> getSortMap();


}
