package com.demo.concurrent;

import com.demo.concurrent.example.threadLocal.RequestHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hobo
 * @description
 */
@Slf4j
public class HttpInterceptor extends HandlerInterceptorAdapter {

    // 接口处理之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle");
        return true;
    }


    // 接口处理之后
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        RequestHolder.remove();
        return;
    }
}
