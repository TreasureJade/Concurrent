package com.demo.concurrent.example.atomic;

import com.demo.concurrent.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author hobo
 * @description AtomicReference.compareAndSet的使用实例
 */
@Slf4j
@ThreadSafe
public class AtomicExample4 {

    private static AtomicReference<Integer> count = new AtomicReference<>(0);

    public static void main(String[] args) {
        // 更新为2
        count.compareAndSet(0,2);
        //不执行
        count.compareAndSet(0,1);
        count.compareAndSet(1,3);
        // 更新为4
        count.compareAndSet(2,4);
        //不执行
        count.compareAndSet(3,5);
        log.info("count:{}",count.get());
    }
}
