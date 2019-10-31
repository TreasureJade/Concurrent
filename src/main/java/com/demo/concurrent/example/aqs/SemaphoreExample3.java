package com.demo.concurrent.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author hobo
 * @description Semaphore 的基本用法3 在一定时间内尝试获取许可，如果拿到许可就执行，拿不到就丢弃
 */
@Slf4j
public class SemaphoreExample3 {

    private static int threadCount = 200;


    public static void main(String[] args) throws InterruptedException {

        ExecutorService exec = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(20);


        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            exec.execute(() -> {
                try {
                    // 在一定时间内尝试获取许可，如果拿到许可就执行，拿不到就丢弃
                    if (semaphore.tryAcquire(5000, TimeUnit.MILLISECONDS)) {
                        test(threadNum);
                        semaphore.release();
                    }
                } catch (Exception e) {
                    log.error("exception", e);
                } finally {
                }
            });
        }
        // 可以保证每个线程都执行完
        log.info("finish");
        exec.shutdown();
    }

    private static void test(int threadNum) throws Exception {
        log.info("{}", threadNum);
        Thread.sleep(1000);
    }
}
