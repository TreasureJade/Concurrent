package com.demo.concurrent.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author hobo
 * @description CountDownLatch 的基本用法2
 */
@Slf4j
public class CountDownLatchExample2 {

    private static int threadCount = 200;


    public static void main(String[] args) throws InterruptedException {

        ExecutorService exec = Executors.newCachedThreadPool();

        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
//            无用
//            Thread.sleep(100);
            exec.execute(()->{
                try {
                    test(threadNum);
                } catch (Exception e) {
                    log.error("exception",e);
                }finally {
                    // 做计数器递减 如果可能出现异常 那就需要放到finally中
                    countDownLatch.countDown();
                }
            });
        }
        // await函数需要 countDownLatch.countDown()减到 0
        // 支持给定时间的等待
        countDownLatch.await(10,TimeUnit.MICROSECONDS);
        log.info("finish");
        exec.shutdown();
    }

    private static void test(int threadNum) throws Exception {
        log.info("{}",threadNum);
        Thread.sleep(100);
    }
}
