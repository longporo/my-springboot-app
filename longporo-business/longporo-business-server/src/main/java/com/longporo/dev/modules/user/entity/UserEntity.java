package com.longporo.dev.modules.user.entity;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.longporo.dev.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * User Entity Class<br>
 *
 * @param
 * @return
 * @author Zihao Long
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("user")
public class UserEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
     * 
     */
	@TableField(value = "username")
	private String username;
	/**
     * 
     */
	@TableField(value = "password")
	private String password;
	/**
     * 
     */
	@TableField(value = "email")
	private String email;
	/**
     * 
     */
	@TableField(value = "phonenum")
	private String phonenum;
	/**
     * 
     */
	@TableField(value = "status")
	private Integer status;
	@Override
	public String toString() {
		return JSONUtil.toJsonStr(this);
	}
}