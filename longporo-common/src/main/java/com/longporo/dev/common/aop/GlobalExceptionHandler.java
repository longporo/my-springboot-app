package com.longporo.dev.common.aop;


import com.longporo.dev.common.constant.TraceConstant;
import com.longporo.dev.common.exception.ErpException;
import com.longporo.dev.common.exception.ErrorCode;
import com.longporo.dev.common.filter.HttpServletRequestBodyWrapper;
import com.longporo.dev.common.filter.HttpServletResponseBodyWrapper;
import com.longporo.dev.common.log.LogService;
import com.longporo.dev.common.log.RequestLogger;
import com.longporo.dev.common.log.RequestLoggerContextHolder;
import com.longporo.dev.common.utils.DateUtil;
import com.longporo.dev.common.utils.Result;
import com.longporo.dev.common.utils.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * Global Exception Handler<br>
 *
 * @param
 * @author Zihao Long
 * @return
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    @Resource
    LogService logService;


    /**
     * Handle all exception<br>
     *
     * @param [request, response, exception]
     * @return java.lang.Object
     * @author Zihao Long
     */
    @ExceptionHandler(value = Exception.class)
    public Object allExceptionHandler(HttpServletRequest request, HttpServletResponse response,
                                      Throwable exception) throws Throwable {
        Throwable realException = null;
        Result result = new Result();
        String codeType;
        if (exception instanceof ErpException) {
            codeType = TraceConstant.CODE_TYPE_BUSINESS_ERROR;
            ErpException ex = (ErpException) exception;
            realException = ex.getRealException();
            result.error(ex.getCode(), ex.getMsg());
        } else if (exception instanceof MethodArgumentNotValidException) {
            codeType = TraceConstant.CODE_TYPE_BUSINESS_ERROR;
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) exception;
            BindingResult bindingResult = ex.getBindingResult();
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            if (allErrors.isEmpty()) {
                result.error(ErrorCode.PARAMETER_ERROR);
            } else {
                ObjectError error = allErrors.get(0);
                String msg = error.getDefaultMessage();
                result.error(ErrorCode.PARAMETER_ERROR, msg);
            }
        } else {
            codeType = TraceConstant.CODE_TYPE_SERVE_ERROR;
            result.error();
        }

        // dev environment，print the log to console
        if (!TraceConstant.ACTIVE_PROFILE_PROD.equals(SpringContextUtils.getActiveProfile())) {
            // when it is the system exception, then use printStackTrace which is to trace error easily
            if (!(exception instanceof ErpException)) {
                exception.printStackTrace();
            }
        }

        // log exception detail
        HttpServletRequestBodyWrapper requestWrapper = new HttpServletRequestBodyWrapper(request);
        HttpServletResponseBodyWrapper responseWrapper = new HttpServletResponseBodyWrapper(response, true);
        if (realException != null) {
            exception = realException;
        }
        RequestLogger rlo = new RequestLogger(requestWrapper, responseWrapper, exception, codeType);
        RequestLogger createContext = RequestLoggerContextHolder.getContext();
        if (null != createContext) {
            String requestBody = createContext.getRequestBody();
            rlo.setRequestBody(requestBody);
            rlo.setRequestTime(createContext.getRequestTime());
            rlo.setTimeCost(System.currentTimeMillis() - DateUtil.parseDate(createContext.getRequestTime(), DateUtil.yyyyMMddTHH_mm_ssSSSZ).getTime());
            rlo.setResponseBody(result.toString());
            String logStr = rlo.toString();
            logService.record(logStr);
            // clear context in case of memory overflow
            RequestLoggerContextHolder.clearContext();
            // dev environment，print the log to console
            if (!TraceConstant.ACTIVE_PROFILE_PROD.equals(SpringContextUtils.getActiveProfile())) {
                log.error(logStr);
            }
        }

        return result;
    }

}
