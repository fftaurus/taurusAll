package com.taurus.zjbm.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class HttpContextUtils {

    /**
     * [获取请求]
     *
     * @param
     * @return javax.servlet.http.HttpServletRequest
     * @author taurus
     * @date 2022/8/4
     */
    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
}