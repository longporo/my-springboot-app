package com.longporo.dev.common.exception;

/**
 * Business Error Code<br>
 *
 * @param
 * @return
 * @author Zihao Long
 */
public interface ErrorCode {
    int INTERNAL_SERVER_ERROR = 500;
    int UNAUTHORIZED = 401;
    int FORBIDDEN = 403;

    int PARAMETER_ERROR = 800;
    int UPDATE_ERROR = 900;
    int DATA_ERROR = 901;
    int SAVE_ERROR = 902;
    int DELETE_ERROR = 903;
}
