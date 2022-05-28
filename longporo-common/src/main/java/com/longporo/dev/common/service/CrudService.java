package com.longporo.dev.common.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.longporo.dev.common.page.PageData;

import java.util.List;
import java.util.Map;

/**
 * CRUD base Service<br>
 *
 * @param
 * @return
 * @author Zihao Long
 */
public interface CrudService<T, D> extends BaseService<T> {

    PageData<D> page(Map<String, Object> params);

    List<D> list(Map<String, Object> params);

    List<D> selectDtoList(QueryWrapper<T> queryWrapper);

    D get(String id);

    void save(D dto);

    void update(D dto);

    void delete(String[] ids);

}