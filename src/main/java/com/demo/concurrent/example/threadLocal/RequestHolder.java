package com.demo.concurrent.example.threadLocal;

/**
 * @author hobo
 * @description ThreadLocal 线程封闭策略
 */
public class RequestHolder {

    private final static ThreadLocal<Long> requestHolder = new ThreadLocal<>();

    // 将线程的id存到map中
    public static void add(Long id){
        requestHolder.set(id);
    }

    // 获取当前线程的id
    public static Long getId(){
        return requestHolder.get();
    }

    // 释放存储数据的空间
    public static void remove(){
        requestHolder.remove();
    }


}
