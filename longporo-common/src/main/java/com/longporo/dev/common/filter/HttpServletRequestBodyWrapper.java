package com.longporo.dev.common.filter;

import com.google.common.io.ByteStreams;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Backup request inputStream result to body for logging<br>
 *
 * @param
 * @author Zihao Long
 * @return
 */
public class HttpServletRequestBodyWrapper extends HttpServletRequestWrapper {
    private final byte[] body;

    public HttpServletRequestBodyWrapper(HttpServletRequest request) throws IOException {
        super(request);
        body = ByteStreams.toByteArray(request.getInputStream());
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bodyInputStream = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return bodyInputStream.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }
}
