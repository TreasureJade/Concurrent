package com.demo.concurrent.example.singleton;

import com.demo.concurrent.annoations.NotThreadSafe;
import com.demo.concurrent.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hobo
 * @description 枚举模式 ：最安全
 */
@Slf4j
@ThreadSafe
public class SingletonExample7 {

    // 私有构造函数
    private SingletonExample7() {
    }

    public static SingletonExample7 getInstance(){
        return Singleton.INSTANCE.getInstance();
    }

    private enum Singleton{
        INSTANCE;

        private SingletonExample7 singleton;

        // JVM保证这个方法绝对只调用一次
        Singleton(){
            singleton = new SingletonExample7();
        }

        public SingletonExample7 getInstance(){
            return singleton;
        }
    }
}
