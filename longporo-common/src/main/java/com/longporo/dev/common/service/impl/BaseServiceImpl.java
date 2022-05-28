package com.longporo.dev.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.longporo.dev.common.constant.Constant;
import com.longporo.dev.common.page.PageData;
import com.longporo.dev.common.service.BaseService;
import com.longporo.dev.common.utils.ConvertUtils;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Base service implement class<br>
 *
 * @param
 * @return
 * @author Zihao Long
 */
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T>  implements BaseService<T> {

    @Autowired
    protected M baseDao;

    /**
     * Get page<br>
     *
     * @param [params, defaultOrderField, isAsc]
     * @return com.baomidou.mybatisplus.core.metadata.IPage<T>
     * @author Zihao Long
     */
    protected IPage<T> getPage(Map<String, Object> params, String defaultOrderField, boolean isAsc) {
        // page info
        long curPage = 1;
        long limit = 10;

        String pag = (String) params.get(Constant.PAGE);
        if(StringUtils.isNotEmpty(pag)){
            curPage = Long.parseLong(pag);
        }
        String paramLimit = (String) params.get(Constant.LIMIT);
        if(StringUtils.isNotEmpty(paramLimit)){
            limit = Long.parseLong(paramLimit);
        }

        // page object
        Page<T> page = new Page<>(curPage, limit);
        params.put(Constant.PAGE, page);

        // order filed
        String orderField = (String)params.get(Constant.ORDER_FIELD);
        String order = (String)params.get(Constant.ORDER);

        // set order by
        if(StringUtils.isNotEmpty(orderField) && StringUtils.isNotEmpty(order)){
            if(Constant.ASC.equalsIgnoreCase(order)) {
                return page.setAsc(orderField);
            }else {
                return page.setDesc(orderField);
            }
        }

        // default order filed
        if(isAsc) {
            page.setAsc(defaultOrderField);
        }else {
            page.setDesc(defaultOrderField);
        }

        return page;
    }

    protected <T> PageData<T> getPageData(List<?> list, long total, Class<T> target){
        List<T> targetList = ConvertUtils.sourceToTarget(list, target);

        return new PageData<>(targetList, total);
    }

    protected <T> PageData<T> getPageData(IPage page, Class<T> target){
        return getPageData(page.getRecords(), page.getTotal(), target);
    }

    protected Map<String, Object> paramsToLike(Map<String, Object> params, String... likes){
        for (String like : likes){
            String val = (String)params.get(like);
            if (StringUtils.isNotEmpty(val)){
                params.put(like, "%" + val + "%");
            }else {
                params.put(like, null);
            }
        }
        return params;
    }

    /**
     * Check whether the database operation is successful<br>
     *
     * @param [result]
     * @return boolean
     * @author Zihao Long
     */
    protected static boolean retBool(Integer result) {
        return SqlHelper.retBool(result);
    }

    protected Class<T> currentModelClass() {
        return ReflectionKit.getSuperClassGenericType(getClass(), 1);
    }

    /**
     * Database batch operation<br>
     *
     * @param []
     * @return org.apache.ibatis.session.SqlSession
     * @author Zihao Long
     */
    protected SqlSession sqlSessionBatch() {
        return SqlHelper.sqlSessionBatch(currentModelClass());
    }

    /**
     * Release SQL session<br>
     *
     * @param [sqlSession]
     * @return void
     * @author Zihao Long
     */
    protected void closeSqlSession(SqlSession sqlSession){
        SqlSessionUtils.closeSqlSession(sqlSession, GlobalConfigUtils.currentSessionFactory(currentModelClass()));
    }

    /**
     * Get SQL statement<br>
     *
     * @param [sqlMethod]
     * @return java.lang.String
     * @author Zihao Long
     */
    protected String sqlStatement(SqlMethod sqlMethod) {
        return SqlHelper.table(currentModelClass()).getSqlStatement(sqlMethod.getMethod());
    }

    @Override
    public boolean insert(T entity) {
        return BaseServiceImpl.retBool(baseDao.insert(entity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertBatch(Collection<T> entityList) {
        return insertBatch(entityList, 100);
    }

    /**
     * Batch inserting<br>
     *
     * @param [entityList, batchSize]
     * @return boolean
     * @author Zihao Long
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertBatch(Collection<T> entityList, int batchSize) {
        SqlSession batchSqlSession = sqlSessionBatch();
        int i = 0;
        String sqlStatement = sqlStatement(SqlMethod.INSERT_ONE);
        try {
            for (T anEntityList : entityList) {
                batchSqlSession.insert(sqlStatement, anEntityList);
                if (i >= 1 && i % batchSize == 0) {
                    batchSqlSession.flushStatements();
                }
                i++;
            }
            batchSqlSession.flushStatements();
        }finally {
            closeSqlSession(batchSqlSession);
        }
        return true;
    }

    @Override
    public boolean updateById(T entity) {
        return BaseServiceImpl.retBool(baseDao.updateById(entity));
    }

    @Override
    public boolean update(T entity, Wrapper<T> updateWrapper) {
        return BaseServiceImpl.retBool(baseDao.update(entity, updateWrapper));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateBatchById(Collection<T> entityList) {
        return updateBatchById(entityList, 30);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateBatchById(Collection<T> entityList, int batchSize) {
        if (CollectionUtils.isEmpty(entityList)) {
            throw new IllegalArgumentException("Error: entityList must not be empty");
        }
        SqlSession batchSqlSession = sqlSessionBatch();
        int i = 0;
        String sqlStatement = sqlStatement(SqlMethod.UPDATE_BY_ID);
        try {
            for (T anEntityList : entityList) {
                MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
                param.put(Constants.ENTITY, anEntityList);
                batchSqlSession.update(sqlStatement, param);
                if (i >= 1 && i % batchSize == 0) {
                    batchSqlSession.flushStatements();
                }
                i++;
            }
            batchSqlSession.flushStatements();
        }finally {
            closeSqlSession(batchSqlSession);
        }
        return true;
    }

    @Override
    public T selectById(Serializable id) {
        return baseDao.selectById(id);
    }

    @Override
    public boolean delete(QueryWrapper<T> queryWrapper) {
        return SqlHelper.delBool(baseDao.delete(queryWrapper));
    }

    @Override
    public boolean deleteById(Serializable id) {
        return SqlHelper.delBool(baseDao.deleteById(id));
    }

    @Override
    public boolean deleteBatchIds(Collection<? extends Serializable> idList) {
        return SqlHelper.delBool(baseDao.deleteBatchIds(idList));
    }

    @Override
    public QueryWrapper<T> getQueryWrapper() {
        return new QueryWrapper<>();
    }

    @Override
    public UpdateWrapper<T> getUpdateWrapper() {
        return new UpdateWrapper<>();
    }
}