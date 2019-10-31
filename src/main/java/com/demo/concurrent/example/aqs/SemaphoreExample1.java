package com.demo.concurrent.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author hobo
 * @description Semaphore 的基本用法1
 */
@Slf4j
public class SemaphoreExample1 {

    private static int threadCount = 200;


    public static void main(String[] args) throws InterruptedException {

        ExecutorService exec = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(20);


        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            exec.execute(() -> {
                try {
                    // 获取一个许可
//                    semaphore.acquire();
                    // 获取多个许可
                    semaphore.acquire(3);
                    test(threadNum);
                    // 释放一个许可
//                    semaphore.release();
                    // 释放多个许可
                    semaphore.release(3);
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
