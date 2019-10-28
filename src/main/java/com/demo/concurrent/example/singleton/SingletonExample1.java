package com.demo.concurrent.example.singleton;

import com.demo.concurrent.annoations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hobo
 * @description 懒汉模式
 * 单例实例在第一次使用时进行创建
 */
@Slf4j
@NotThreadSafe
public class SingletonExample1 {

    // 私有构造函数
    private SingletonExample1() {

    }

    // 单例对象
    private static SingletonExample1 instance = null;

    // 静态的工厂方法
    public static SingletonExample1 getInstance() {
        // 多线程下可能会被实例化两次
        if (instance == null) {
            instance = new SingletonExample1();
        }
        return instance;
    }

}
