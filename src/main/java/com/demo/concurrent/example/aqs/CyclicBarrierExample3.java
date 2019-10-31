package com.demo.concurrent.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author hobo
 * @description CyclicBarrier 的基本用法3 线程到达屏障时，优先执行
 */
@Slf4j
public class CyclicBarrierExample3 {

    private static CyclicBarrier barrier = new CyclicBarrier(5,()->{
        //线程到达屏障时，优先执行
        log.info("CALLBACK IS RUNNING");
    });

    public static void main(String[] args) throws Exception  {

        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            final int threadNum = i;
            Thread.sleep(1000);
            executorService.execute(() -> {
                try {
                    race(threadNum);
                } catch (Exception e) {
                    log.error("exception",e);
                }
            });
        }
        executorService.shutdown();
    }

    private static void race(int threadNum) throws Exception {
        Thread.sleep(1000);
        log.info("{} is ready", threadNum);
        // 达到指定的数目之后。await之后的方法就可以执行
        barrier.await();
        log.info("{} continue",threadNum);
    }
}
