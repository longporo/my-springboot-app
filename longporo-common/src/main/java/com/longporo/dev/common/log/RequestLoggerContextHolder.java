package com.longporo.dev.common.log;

/**
 * Request logging thread, remember to clearContext for every creation<br>
 *
 * @param 
 * @return 
 * @author Zihao Long
 */
public class RequestLoggerContextHolder {
    private RequestLoggerContextHolder() {
    }

    private static final ThreadLocal<RequestLogger> context = new ThreadLocal<>();

    public static RequestLogger createContext() {
        RequestLogger invoking = new RequestLogger();
        context.set(invoking);
        return invoking;
    }

    public static RequestLogger getContext() {
        return context.get();
    }

    public static void clearContext() {
        context.remove();
    }
}
