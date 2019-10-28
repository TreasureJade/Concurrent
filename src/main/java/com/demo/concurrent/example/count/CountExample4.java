package com.demo.concurrent.example.count;

import com.demo.concurrent.annoations.NotThreadSafe;
import com.demo.concurrent.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hobo
 * @description 验证volatile线程不安全性
 */
@Slf4j
@NotThreadSafe
public class CountExample4 {

    // 请求总数
    public static int clientTotal = 5000;

    // 同时并发执行的线程数
    public static int threadTotal = 50;

    public static volatile int count = 0;

    public static void main(String[] args) throws Exception{
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);

        for (int i = 0; i < clientTotal; i++) {
            executorService.execute(() -> {
                        try {
                            semaphore.acquire();
                            add();
                            semaphore.release();
                        } catch (Exception e) {
                            log.error("exception", e);
                        }
                        countDownLatch.countDown();
                    });

        }
        countDownLatch.await();
        executorService.shutdown();
        log.info("count:{}",count);

    }

    private synchronized static void add() {
        count++;
        // 1.取出count
        // 2. +1
        // 3. count
    }
}
