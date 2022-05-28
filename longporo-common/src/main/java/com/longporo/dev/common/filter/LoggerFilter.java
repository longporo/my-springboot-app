package com.longporo.dev.common.filter;


import com.longporo.dev.common.log.RequestLogger;
import com.longporo.dev.common.log.RequestLoggerContextHolder;
import com.longporo.dev.common.utils.DateUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

/**
 * The loger filter<br>
 *
 * @param
 * @return
 * @author Zihao Long
 */
@Component
public class LoggerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = httpServletRequest.getRequestURI();
        // do not log the request if it is file upload operation
        if (requestURI.contains("upload.htm")) {
            RequestLogger createContext = RequestLoggerContextHolder.createContext();
            createContext.setRequestTime(DateUtil.format(new Date(), DateUtil.yyyyMMddTHH_mm_ssSSSZ));
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        HttpServletRequestBodyWrapper requestWrapper = new HttpServletRequestBodyWrapper(httpServletRequest);
        RequestLogger createContext = RequestLoggerContextHolder.createContext();
        StringBuffer bodySB = new StringBuffer();
        String line;
        BufferedReader br = requestWrapper.getReader();
        while ((line = br.readLine()) != null) {
            bodySB.append(line);
        }
        createContext.setRequestBody(bodySB.toString());
        createContext.setRequestTime(DateUtil.format(new Date(), DateUtil.yyyyMMddTHH_mm_ssSSSZ));
        createContext.setRequestWrapper(requestWrapper);
        filterChain.doFilter(requestWrapper, httpServletResponse);
    }

}
