package com.longporo.dev.common.filter;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

/**
 * Backup response outputStream result into bodySB for logging<br>
 *
 * @param 
 * @return 
 * @author Zihao Long
 */
public class HttpServletResponseBodyWrapper extends HttpServletResponseWrapper {
    private StringBuffer bodySB = new StringBuffer();
    private boolean shouldLogBody = false;

    public HttpServletResponseBodyWrapper(HttpServletResponse response, boolean should) {
        super(response);
        this.shouldLogBody = should;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        final ServletOutputStream outputStream = super.getOutputStream();
        return new ServletOutputStream() {
            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {

            }

            @Override
            public void write(int b) throws IOException {
                if (shouldLogBody) {
                    bodySB.append((char) b);
                }
                outputStream.write(b);
            }
        };
    }

    public String getBody() {
        return bodySB.toString();
    }
}
