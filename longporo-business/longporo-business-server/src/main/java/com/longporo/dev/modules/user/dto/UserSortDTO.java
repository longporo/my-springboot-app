package com.longporo.dev.modules.user.dto;

import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import com.longporo.dev.common.dto.BaseSortDTO;
import lombok.Data;

import java.util.Map;


/**
 * User Sort DTO<br>
 *
 * @param
 * @return
 * @author Zihao Long
 */
@Data
public class UserSortDTO extends BaseSortDTO {
    private static final long serialVersionUID = 1L;

		// 
	private Integer id;

		// 
	private Integer username;

		// 
	private Integer password;

		// 
	private Integer email;

		// 
	private Integer phonenum;

		// 
	private Integer status;

	
	@Override
	public Map<String,Integer> getSortMap(){
		Map<String,Integer> map = Maps.newHashMap();
		if(null != id && id > 0){
			map.put("user.id",id);
		}

		if(null != username && username > 0){
			map.put("user.username",username);
		}

		if(null != password && password > 0){
			map.put("user.password",password);
		}

		if(null != email && email > 0){
			map.put("user.email",email);
		}

		if(null != phonenum && phonenum > 0){
			map.put("user.phonenum",phonenum);
		}

		if(null != status && status > 0){
			map.put("user.status",status);
		}

		
		return map;
	}
	@Override
	public String toString() {
		return JSONUtil.toJsonStr(this);
	}

}