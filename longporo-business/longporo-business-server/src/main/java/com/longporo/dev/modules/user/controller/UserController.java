package com.longporo.dev.modules.user.controller;

import com.longporo.dev.common.utils.Result;
import com.longporo.dev.modules.user.dto.UserDTO;
import com.longporo.dev.modules.user.dto.UserQueryDTO;
import com.longporo.dev.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * User Controller<br>
 *
 * @param 
 * @return 
 * @author Zihao Long
 */
@RestController
@RequestMapping("user")
public class UserController {
    
    @Autowired
    private UserService userService;

    @PostMapping
    @ResponseBody
    public Result create(@RequestBody UserDTO dto) {
        userService.save(dto);
        return new Result().ok();
    }

    /**
     * Get user list by page<br>
     *
     * @param [userQueryDTO]
     * @return com.longporo.dev.common.utils.Result
     * @author Zihao Long
     */
    @GetMapping
    @ResponseBody
    public Result read(@RequestBody UserQueryDTO userQueryDTO) {
        return new Result<>().ok(userService.pageList(userQueryDTO));
    }

    /**
     * Update a user<br>
     *
     * @param [dto]
     * @return com.longporo.dev.common.utils.Result
     * @author Zihao Long
     */
    @PutMapping
    @ResponseBody
    public Result update(@RequestBody UserDTO dto) {
        userService.update(dto);
        return new Result().ok();
    }

    /**
     * Delete a user<br>
     *
     * @param [id]
     * @return com.longporo.dev.common.utils.Result
     * @author Zihao Long
     */
    @DeleteMapping
    @ResponseBody
    public Result delete(Long id) {
        userService.deleteById(id);
        return new Result().ok();
    }

    /**
     * Delete users by email<br>
     *
     * @param [email]
     * @return com.longporo.dev.common.utils.Result
     * @author Zihao Long
     */
    @DeleteMapping("/delByEmail")
    @ResponseBody
    public Result delByEmail(String email) {
        userService.deleteByEmail(email);
        return new Result().ok();
    }
}