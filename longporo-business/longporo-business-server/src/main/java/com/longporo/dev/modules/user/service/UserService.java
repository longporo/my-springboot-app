package com.longporo.dev.modules.user.service;

import com.longporo.dev.common.page.PageData;
import com.longporo.dev.common.service.CrudService;
import com.longporo.dev.modules.user.dto.UserDTO;
import com.longporo.dev.modules.user.dto.UserQueryDTO;
import com.longporo.dev.modules.user.entity.UserEntity;

/**
 * User Service<br>
 *
 * @param 
 * @return 
 * @author Zihao Long
 */
public interface UserService extends CrudService<UserEntity, UserDTO> {

    /**
     * Get by page<br>
     *
     * @param [dto]
     * @return com.longporo.dev.common.page.PageData<com.longporo.dev.modules.user.dto.UserDTO>
     * @author Zihao Long
     */
    PageData<UserDTO> pageList(UserQueryDTO dto);

    /**
     * Delete by email<br>
     *
     * @param [email]
     * @return void
     * @author Zihao Long
     */
    void deleteByEmail(String email);
}