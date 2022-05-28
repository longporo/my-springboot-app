package com.longporo.dev.common.page;

import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Page Data Class<br>
 *
 * @param
 * @author Zihao Long
 * @return
 */
@Data
public class PageData<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private int total;

    private List<T> list;

    /**
     * Constructor method
     *
     * @param list  data list
     * @param total total count
     */
    public PageData(List<T> list, long total) {
        if (null == list) {
            this.list = Lists.newArrayList();
        } else {
            this.list = list;
        }

        this.total = (int) total;
    }

    public PageData() {
    }
}