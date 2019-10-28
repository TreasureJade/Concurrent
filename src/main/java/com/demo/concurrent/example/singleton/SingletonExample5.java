package com.demo.concurrent.example.singleton;

import com.demo.concurrent.annoations.NotThreadSafe;
import com.demo.concurrent.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hobo
 * @description 懒汉模式 -->volatile + 双重同步锁单利模式
 * 单例实例在第一次使用时进行创建
 */
@Slf4j
@ThreadSafe
public class SingletonExample5 {

    // 私有构造函数
    private SingletonExample5() {

    }

    //  执行 instance = new SingletonExample4();
    // 1.分配对象内存空间 memory = allocate()
    // 2.初始化对象 ctorInstance
    // 3.instance = memory 设置instance指向分配的内存



    // 单例对象
    // 使用volatile + 双重检测机制限制指令重排
    private volatile static SingletonExample5 instance = null;

    // 静态的工厂方法
    public static synchronized SingletonExample5 getInstance() {
        // 双重检测机制
        if (instance == null) {
            // 同步锁
            synchronized (SingletonExample5.class) {
                if (instance == null) {
                    instance = new SingletonExample5();
                }
            }
        }
        return instance;
    }

}
