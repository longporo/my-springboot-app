package com.longporo.dev.common.utils;

import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * REST request Util<br>
 *
 * @param
 * @return
 * @author Zihao Long
 */
public class HttpUtil {

    /**
     * Do REST request<br>
     *
     * @param [json, url, method]
     * @return java.lang.String
     * @author Zihao Long
     */
    public static String rest(String json, String url, HttpMethod method) {
        try {
            // set headers
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            headers.add("Accept", MediaType.APPLICATION_JSON.toString());
            // set body
            HttpEntity<String> entity = new HttpEntity<>(json, headers);
            RestTemplate restTemplate = new RestTemplate();
            setRestTemplateEncode(restTemplate);
            ResponseEntity<String> result = restTemplate.exchange(url, method, entity, String.class);
            return result.getBody();
        } catch (Exception e) {
            throw e;
        }
    }

    public static void setRestTemplateEncode(RestTemplate restTemplate) {
        if (null == restTemplate || ObjectUtils.isEmpty(restTemplate.getMessageConverters())) {
            return;
        }

        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        for (int i = 0; i < messageConverters.size(); i++) {
            HttpMessageConverter<?> httpMessageConverter = messageConverters.get(i);
            if (httpMessageConverter.getClass().equals(StringHttpMessageConverter.class)) {
                messageConverters.set(i, new StringHttpMessageConverter(StandardCharsets.UTF_8));
            }
        }
    }

}
