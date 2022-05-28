package com.longporo.dev.common.exception;


import com.longporo.dev.common.utils.MessageUtils;
import com.longporo.dev.common.utils.Result;

/**
 * Business Exception<br>
 *
 * @param 
 * @return 
 * @author Zihao Long
 */
public class ErpException extends RuntimeException {
	private static final long serialVersionUID = 1L;

    private int code;
	private String msg;
	private Throwable realException;

	public ErpException(int code) {
		this.code = code;
		this.msg = MessageUtils.getMessage(code);
	}

	public ErpException(int code, String... params) {
		this.code = code;
		this.msg = MessageUtils.getMessage(code, params);
	}

	public ErpException(int code, Throwable e) {
		super(e);
		this.code = code;
		this.msg = MessageUtils.getMessage(code);
		this.realException = e;
	}

	public ErpException(int code, Throwable e, String... params) {
		super(e);
		this.code = code;
		this.msg = MessageUtils.getMessage(code, params);
		this.realException = e;
	}

	/**
	 * 只给参数异常用
	 * @param msg
	 */
	public ErpException(String msg) {
		super(msg);
		this.code = ErrorCode.PARAMETER_ERROR;
		this.msg = msg;
	}

	public ErpException(String msg, Throwable e) {
		super(msg, e);
		this.code = ErrorCode.INTERNAL_SERVER_ERROR;
		this.msg = msg;
		this.realException = e;
	}

	public ErpException(Result result) {
		this.code = result.getCode();
		this.msg = result.getMsg();
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Throwable getRealException() {
		return realException;
	}

	public void setRealException(Throwable realException) {
		this.realException = realException;
	}
}