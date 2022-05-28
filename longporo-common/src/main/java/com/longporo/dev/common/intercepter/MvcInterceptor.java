/*
 * 文件名：MvcIntercept.java
 * 版权：Copyright 2012-2019 广州宝锶信息技术有限公司
 * 创建人：龙子豪
 * 创建时间：2021年03月26日 15:48
 * 修改人：
 * 修改时间：
 * 修改内容：
 */
package com.longporo.dev.common.intercepter;

import com.longporo.dev.common.exception.ErpException;
import com.longporo.dev.common.exception.ErrorCode;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * MVC interceptor<br>
 *
 * @param 
 * @return 
 * @author Zihao Long
 */
@Component
public class MvcInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // do something before the process enter the controller
        // like authentication check
        String longporo = request.getHeader("longporo");
        if (!StringUtils.isEmpty(longporo)) {
            throw new ErpException(ErrorCode.UNAUTHORIZED);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}