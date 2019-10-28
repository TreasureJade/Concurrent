package com.demo.concurrent.example.sync;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author hobo
 * @description 修饰代码块
 * 如果子类继承了此类，则方法不包含 synchronized 修饰符
 * synchronized 不属于方法声明的一部分
 * 子类想使用synchronized 则需要在方法上使用synchronized 修饰
 */
@Slf4j
public class SyncchronizedExample1 {

    // 修饰代码块
    public void test1(int j){
        synchronized (this){
            for (int i = 0; i < 10; i++) {
                log.info("test1:{}",j,i);
            }
        }
    }

    // 修饰一个方法
    public synchronized void test2(int j){
        for (int i = 0; i < 10; i++) {
            log.info("test2:{} - {}",j,i);
        }
    }

    public static void main(String[] args) {
        SyncchronizedExample1 example1 = new SyncchronizedExample1();
        SyncchronizedExample1 example2 = new SyncchronizedExample1();
        ExecutorService service = Executors.newCachedThreadPool();
        // 交替执行则证明交替执行
        service.execute(()->{
            example1.test2(1);
        });
        service.execute(()->{
            example2.test2(2);
        });
    }
}
