package com.longporo.dev.common.aop;

import cn.hutool.json.JSONUtil;
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
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Intercept the requests in Controller to log<br>
 *
 * @param
 * @author Zihao Long
 * @return
 */
@Aspect
@Component
@Slf4j
public class WebLogAspect {

    @Resource
    LogService logService;

    /**
     * config the controller packages
     */
    private final String executeExpr = "execution(* com.longporo.dev..*Controller.*(..))";


    @Pointcut(executeExpr)
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        RequestLogger createContext = RequestLoggerContextHolder.getContext();
        try {

            if (null == createContext) {
                // return result
                return pjp.proceed();
            }
            String requestBody = createContext.getRequestBody();
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            HttpServletResponse response = attributes.getResponse();
            Date startDate = new Date();
            long startTime = System.currentTimeMillis();
            // proceed result
            Object ob = pjp.proceed();

            RequestLogger rlo = new RequestLogger(request, response, null, TraceConstant.CODE_TYPE_SUCCESS);

            createContext = RequestLoggerContextHolder.getContext();
            if (createContext == null) {
                return ob;
            }
            BeanUtils.copyProperties(rlo, createContext);

            String responseBodyStr;
            if (ob instanceof ResponseEntity
                    && ((ResponseEntity) ob).getBody() instanceof byte[]) {
                // the byte data is not logged
                responseBodyStr = "";
            } else {
                responseBodyStr = JSONUtil.toJsonStr(ob);
            }

            createContext.setRequestBody(requestBody);
            createContext.setResponseBody(responseBodyStr);
            createContext.setRequestTime(DateUtil.format(startDate, DateUtil.yyyyMMddTHH_mm_ssSSSZ));
            createContext.setTimeCost(System.currentTimeMillis() - startTime);
            String msg = createContext.toString();
            logService.record(msg);
            if (!TraceConstant.ACTIVE_PROFILE_PROD.equals(SpringContextUtils.getActiveProfile())) {
                // dev environment，print the log to console
                log.info(msg);
            }
            // clear context in case of memory overflow
            RequestLoggerContextHolder.clearContext();
            return ob;
        } catch (Exception e) {
            // save exception log. When method throws exception, process goes here
            return saveErrorLog(e, createContext);
        }

    }

    /**
     * Save exception log<br>
     *
     * @param [exception, createContext]
     * @return java.lang.Object
     * @author Zihao Long
     */
    private Object saveErrorLog(Throwable exception, RequestLogger createContext) throws IOException {
        Throwable realException;
        realException = null;
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
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
        HttpServletRequestBodyWrapper requestWrapper = new HttpServletRequestBodyWrapper(request);
        HttpServletResponseBodyWrapper responseWrapper = new HttpServletResponseBodyWrapper(response, true);
        if (realException != null) {
            exception = realException;
        }
        RequestLogger rlo = new RequestLogger(requestWrapper, responseWrapper, exception, codeType);
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
        return result;
    }

}
