package com.longporo.dev.common.utils;

import cn.hutool.json.JSONUtil;
import com.longporo.dev.common.exception.ErrorCode;

import java.io.Serializable;

/**
 * The response Result<br>
 *
 * @param
 * @return
 * @author Zihao Long
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * code: 0 stands for success, others for failure
     */
    private int code = 0;
    /**
     * msg content
     */
    private String msg = "success";
    /**
     * response data
     */
    private T data;

    public Result<T> ok(T data) {
        this.setData(data);
        return this;
    }

    public Result<T> ok() {
        return this;
    }

    public boolean success(){
        return code == 0 ? true : false;
    }

    public Result<T> error() {
        this.code = ErrorCode.INTERNAL_SERVER_ERROR;
        this.msg = MessageUtils.getMessage(this.code);
        return this;
    }

    public Result<T> error(int code) {
        this.code = code;
        this.msg = MessageUtils.getMessage(this.code);
        return this;
    }

    public Result<T> error(int code, String msg) {
        this.code = code;
        this.msg = msg;
        return this;
    }

    public Result<T> error(String msg) {
        this.code = ErrorCode.INTERNAL_SERVER_ERROR;
        this.msg = msg;
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }
}
