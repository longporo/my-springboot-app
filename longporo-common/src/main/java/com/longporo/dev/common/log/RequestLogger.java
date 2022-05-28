package com.longporo.dev.common.log;

import cn.hutool.json.JSONUtil;
import com.longporo.dev.common.filter.HttpServletRequestBodyWrapper;
import com.google.common.collect.Maps;
import lombok.Data;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

/**
 * Web request logger<br>
 *
 * @param
 * @return
 * @author Zihao Long
 */
@Data
public class RequestLogger {
    private String path;
    private String method;
    private String remoteIP;
    private Map<String, Object> requestHeaders = Maps.newHashMap();
    private Map<String, Object> parameters = Maps.newHashMap();
    private String requestBody = "";
    private String requestTime;
    private Map<String, Object> responseHeaders = Maps.newHashMap();
    private int responseStatus;
    private String responseTime;
    private String responseBody;
    private String errorMeg;
    private Long timeCost;
    /**
     * log type: 200 normal request   500 sever error   5000 business exception
     */
    private String codeType;
    /**
     * request device
     */
    private String device;
    /**
     * user no
     */
    private String userNo;
    /**
     * isWeChat
     */
    private Boolean isWeChat = false;
    /**
     * Channel: wechat, aliapp, other(web)
     */
    private String mChannel;

    /**
     * trace id
     */
    private String traceId;

    HttpServletRequestBodyWrapper requestWrapper;


    public RequestLogger() {
    }

    public RequestLogger(HttpServletRequest request,
                         HttpServletResponse response, Throwable exception, String codeType) {

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerKey = headerNames.nextElement();
            String headerValue = request.getHeader(headerKey);
            this.requestHeaders.put(headerKey, headerValue);
        }
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        Enumeration<String> queryNames = request.getParameterNames();
        while (queryNames.hasMoreElements()) {
            String queryKey = queryNames.nextElement();
            String queryValue = request.getParameter(queryKey);
            this.parameters.put(queryKey, queryValue);
        }
        this.remoteIP = String.format("%s:%s", request.getRemoteAddr(), request.getRemotePort());


        Iterator<String> responseHeaderNames = response.getHeaderNames().iterator();
        while (responseHeaderNames.hasNext()) {
            String headerKey = responseHeaderNames.next();
            String headerValue = response.getHeader(headerKey);
            this.responseHeaders.put(headerKey, headerValue);
        }
        this.responseStatus = response.getStatus();
        if (exception != null) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PrintStream pout = new PrintStream(out);
            exception.printStackTrace(pout);
            this.errorMeg = new String(out.toByteArray());
        }
        this.codeType = codeType;
        String agent = request.getHeader("user-agent");
        if(!StringUtils.isEmpty(agent)){
            if (agent.contains("iPhone")) {
                this.device = "iPhone";
            } else if (agent.contains("iPod")) {
                this.device = "iPod";
            } else if (agent.contains("iPad")) {
                this.device = "iPad";
            } else if (agent.contains("Windows")) {
                this.device = "Windows";
            } else if (agent.contains("Android")) {
                this.device = "Android";
            } else if (agent.contains("Linux")) {
                this.device = "Linux";
            } else {
                this.device = "unknown";
            }
            // set isWeChat
            if (agent.indexOf("MicroMessenger") > 0) {
                this.isWeChat = true;
            }
        }


        this.mChannel = request.getHeader("mchannel");

    }


    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }
}
