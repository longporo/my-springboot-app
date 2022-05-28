package com.longporo.dev.modules.user.dto;

import cn.hutool.json.JSONUtil;
import com.longporo.dev.common.dto.BaseQueryDTO;
import lombok.Data;

import java.util.List;


/**
 * User query DTO<br>
 *
 * @param 
 * @return 
 * @author Zihao Long
 */
@Data
public class UserQueryDTO extends BaseQueryDTO{
    private static final long serialVersionUID = 1L;

	// current page
    private Long page = 1L;

    // page size
	private Long limit = 10L;

	// sort
	private UserSortDTO sort;
	
	//  is no null, 1: is null, 0: is not null
	private String idNoNull;
	
	// 
	private Long id;

	//  in list
	private List<Long> idList;

	//  not in list
	private List<Long> idNoInList;

	
	//  less than
	private Long idLt;

	//  less than or equal to
	private Long idLtEq;

	//  greater than
	private Long idGt;

	//  greater than or equal to
	private Long idGtEq;
			
	//  is no null, 1: is null, 0: is not null
	private String usernameNoNull;
	
	// 
	private String username;

	//  in list
	private List<String> usernameList;

	//  not in list
	private List<String> usernameNoInList;

		//  all like
	private String usernameAllLike;

	//  left like
	private String usernameLeftLike;

	//  right like
	private String usernameRightLike;
			
	//  is no null, 1: is null, 0: is not null
	private String passwordNoNull;
	
	// 
	private String password;

	//  in list
	private List<String> passwordList;

	//  not in list
	private List<String> passwordNoInList;

		//  all like
	private String passwordAllLike;

	//  left like
	private String passwordLeftLike;

	//  right like
	private String passwordRightLike;
			
	//  is no null, 1: is null, 0: is not null
	private String emailNoNull;
	
	// 
	private String email;

	//  in list
	private List<String> emailList;

	//  not in list
	private List<String> emailNoInList;

		//  all like
	private String emailAllLike;

	//  left like
	private String emailLeftLike;

	//  right like
	private String emailRightLike;
			
	//  is no null, 1: is null, 0: is not null
	private String phonenumNoNull;
	
	// 
	private String phonenum;

	//  in list
	private List<String> phonenumList;

	//  not in list
	private List<String> phonenumNoInList;

		//  all like
	private String phonenumAllLike;

	//  left like
	private String phonenumLeftLike;

	//  right like
	private String phonenumRightLike;
			
	//  is no null, 1: is null, 0: is not null
	private String statusNoNull;
	
	// 
	private Integer status;

	//  in list
	private List<Integer> statusList;

	//  not in list
	private List<Integer> statusNoInList;

	
	//  less than
	private Integer statusLt;

	//  less than or equal to
	private Integer statusLtEq;

	//  greater than
	private Integer statusGt;

	//  greater than or equal to
	private Integer statusGtEq;
			
	@Override
	public String toString() {
		return JSONUtil.toJsonStr(this);
	}

}