package com.longporo.dev.modules.user.dto;

import cn.hutool.json.JSONUtil;
import lombok.Data;

import java.io.Serializable;


/**
 * User DTO<br>
 *
 * @param
 * @return
 * @author Zihao Long
 */
@Data
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	// 
	private Long id;

	// 
	private String username;

	// 
	private String password;

	// 
	private String email;

	// 
	private String phonenum;

	// 
	private Integer status;

	@Override
	public String toString() {
		return JSONUtil.toJsonStr(this);
	}

}