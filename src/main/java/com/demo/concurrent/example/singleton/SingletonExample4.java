package com.demo.concurrent.example.singleton;

import com.demo.concurrent.annoations.NotRecommend;
import com.demo.concurrent.annoations.NotThreadSafe;
import com.demo.concurrent.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hobo
 * @description 懒汉模式 -->双重同步锁单利模式
 * 单例实例在第一次使用时进行创建
 */
@Slf4j
@NotThreadSafe
public class SingletonExample4 {

    // 私有构造函数
    private SingletonExample4() {

    }

    //  执行 instance = new SingletonExample4();
    // 1.分配对象内存空间 memory = allocate()
    // 2.初始化对象 ctorInstance
    // 3.instance = memory 设置instance指向分配的内存


    // 多线程下 线程不安全
    // Jvm和cpu优化，发生了指令重排
    // 1.分配对象内存空间 memory = allocate()
    // 3.instance = memory 设置instance指向分配的内存
    // 2.初始化对象 ctorInstance


    // 单例对象
    private static SingletonExample4 instance = null;

    // 静态的工厂方法
    public static synchronized SingletonExample4 getInstance() {
        // 双重检测机制
        if (instance == null) {
            // 同步锁
            synchronized (SingletonExample4.class) {
                if (instance == null) {
                    instance = new SingletonExample4();
                }
            }
        }
        return instance;
    }

}
