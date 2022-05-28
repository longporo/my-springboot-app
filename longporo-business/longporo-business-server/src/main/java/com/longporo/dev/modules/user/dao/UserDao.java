package com.longporo.dev.modules.user.dao;

import com.longporo.dev.common.dao.BaseDao;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.longporo.dev.modules.user.entity.UserEntity;
import com.longporo.dev.modules.user.dto.UserQueryDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * User Dao<br>
 *
 * @param
 * @return
 * @author Zihao Long
 */
@Mapper
public interface UserDao extends BaseDao<UserEntity> {

    /**
     * Get by page
     * @param dto
     * @param page
     * @return
     */
    List<UserEntity> pageList(Page<UserEntity> page,
                              @Param("user") UserQueryDTO dto,
                              @Param("sortStr") String sortStr);
	
}