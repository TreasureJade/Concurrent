package com.demo.concurrent.example.singleton;

import com.demo.concurrent.annoations.NotRecommend;
import com.demo.concurrent.annoations.NotThreadSafe;
import com.demo.concurrent.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hobo
 * @description 懒汉模式
 * 单例实例在第一次使用时进行创建
 * 加synchronized将懒汉模式变为线程安全
 */
@Slf4j
@ThreadSafe
@NotRecommend
public class SingletonExample3 {

    // 私有构造函数
    private SingletonExample3() {

    }

    // 单例对象
    private static SingletonExample3 instance = null;

    // 静态的工厂方法
    // 加大了性能开销
    public static synchronized SingletonExample3 getInstance() {
        if (instance == null) {
            instance = new SingletonExample3();
        }
        return instance;
    }

}
