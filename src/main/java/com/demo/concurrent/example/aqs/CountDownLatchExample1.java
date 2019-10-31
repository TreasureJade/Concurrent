package com.demo.concurrent.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author hobo
 * @description CountDownLatch 的基本用法
 */
@Slf4j
public class CountDownLatchExample1 {

    private static int threadCount = 20;


    public static void main(String[] args) throws InterruptedException {

        ExecutorService exec = Executors.newCachedThreadPool();

        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            exec.execute(()->{
                try {
                    test(threadNum);
                } catch (Exception e) {
                    log.error("exception",e);
                }finally {
                    countDownLatch.countDown();
                }
            });
        }
        // 可以保证每个线程都执行完
        countDownLatch.await();
        log.info("finish");
        exec.shutdown();
    }

    private static void test(int threadNum) throws Exception {
        log.info("{}",threadNum);
        Thread.sleep(100);
    }
}
