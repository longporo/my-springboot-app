package com.longporo.dev.modules.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.longporo.dev.common.exception.ErpException;
import com.longporo.dev.common.exception.ErrorCode;
import com.longporo.dev.common.page.PageData;
import com.longporo.dev.common.service.impl.CrudServiceImpl;
import com.longporo.dev.common.utils.ConvertUtils;
import com.longporo.dev.modules.user.dao.UserDao;
import com.longporo.dev.modules.user.dto.UserDTO;
import com.longporo.dev.modules.user.dto.UserQueryDTO;
import com.longporo.dev.modules.user.entity.UserEntity;
import com.longporo.dev.modules.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * UserServiceImpl<br>
 *
 * @param
 * @author Zihao Long
 * @return
 */
@Service
public class UserServiceImpl extends CrudServiceImpl<UserDao, UserEntity, UserDTO> implements UserService {

    @Override
    public QueryWrapper<UserEntity> getWrapper(Map<String, Object> params) {
        String id = (String) params.get("id");

        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }

    @Override
    public PageData<UserDTO> pageList(UserQueryDTO dto) {
        Page<UserEntity> page = new Page<>(dto.getPage(), dto.getLimit());
        List<UserEntity> list = this.baseDao.pageList(page, dto, dto.getSortStr(dto.getSort()));
        return new PageData<>(ConvertUtils.sourceToTarget(list, UserDTO.class), page.getTotal());
    }

    @Override
    public void deleteByEmail(String email) {
        if (StringUtils.isEmpty(email)) {
            throw new ErpException(ErrorCode.PARAMETER_ERROR);
        }
        QueryWrapper<UserEntity> queryWrapper = this.getQueryWrapper();
        queryWrapper.eq("email", email);
        boolean success = this.delete(queryWrapper);
        if (!success) {
            throw new ErpException(ErrorCode.DELETE_ERROR);
        }
    }
}