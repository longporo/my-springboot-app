package com.longporo.dev.common.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import java.io.Serializable;
import java.util.Collection;

/**
 * Base Service<br>
 *
 * @param 
 * @return 
 * @author Zihao Long
 */
public interface BaseService<T> {

    /**
     * Insert a piece of data<br>
     *
     * @param [entity]
     * @return boolean
     * @author Zihao Long
     */
    boolean insert(T entity);

    /**
     * Batch inserting data<br>
     *
     * @param [entityList]
     * @return boolean
     * @author Zihao Long
     */
    boolean insertBatch(Collection<T> entityList);

    /**
     * Batch inserting data with size, not support in Oracle, SQL Server<br>
     *
     * @param [entityList, batchSize]
     * @return boolean
     * @author Zihao Long
     */
    boolean insertBatch(Collection<T> entityList, int batchSize);

    /**
     * Update by ID<br>
     *
     * @param [entity]
     * @return boolean
     * @author Zihao Long
     */
    boolean updateById(T entity);

    /**
     * Update by wrapper(conditions)<br>
     *
     * @param [entity, updateWrapper]
     * @return boolean
     * @author Zihao Long
     */
    boolean update(T entity, Wrapper<T> updateWrapper);

    /**
     * Batch updating by id<br>
     *
     * @param [entityList]
     * @return boolean
     * @author Zihao Long
     */
    boolean updateBatchById(Collection<T> entityList);

    /**
     * Batch updating by id with size<br>
     *
     * @param [entityList, batchSize]
     * @return boolean
     * @author Zihao Long
     */
    boolean updateBatchById(Collection<T> entityList, int batchSize);

    /**
     * Select by id<br>
     *
     * @param [id]
     * @return T
     * @author Zihao Long
     */
    T selectById(Serializable id);

    /**
     * Delete by wrapper<br>
     *
     * @param [queryWrapper]
     * @return boolean
     * @author Zihao Long
     */
    boolean delete(QueryWrapper<T> queryWrapper);

    /**
     * Delete by id<br>
     *
     * @param [id]
     * @return boolean
     * @author Zihao Long
     */
    boolean deleteById(Serializable id);

    /**
     * Delete by ids<br>
     *
     * @param [idList]
     * @return boolean
     * @author Zihao Long
     */
    boolean deleteBatchIds(Collection<? extends Serializable> idList);

    /**
     * Get query wrapper<br>
     *
     * @param []
     * @return com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<T>
     * @author Zihao Long
     */
    QueryWrapper<T> getQueryWrapper();

    /**
     * Get update Wrapper<br>
     *
     * @param []
     * @return com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<T>
     * @author Zihao Long
     */
    UpdateWrapper<T> getUpdateWrapper();
}