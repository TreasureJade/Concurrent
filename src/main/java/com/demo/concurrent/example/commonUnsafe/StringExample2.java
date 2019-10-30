package com.demo.concurrent.example.commonUnsafe;

import com.demo.concurrent.annoations.NotThreadSafe;
import com.demo.concurrent.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author hobo
 * @description 线程安全类 StringBuffer
 *              如果线程安全，则字符串长度应为5000
 */
@Slf4j
@ThreadSafe
public class StringExample2 {

    // 请求总数
    public static int clientTotal = 5000;

    // 同时并发执行的线程数
    public static int threadTotal = 50;

    public static StringBuffer stringBuffer = new StringBuffer();

    public static void main(String[] args) throws Exception{
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);

        for (int i = 0; i < clientTotal; i++) {
            executorService.execute(() -> {
                        try {
                            semaphore.acquire();
                            update();
                            semaphore.release();
                        } catch (Exception e) {
                            log.error("exception", e);
                        }
                        countDownLatch.countDown();
                    });

        }
        countDownLatch.await();
        executorService.shutdown();
        log.info("count:{}", stringBuffer.length());

    }

    private static void update() {
        stringBuffer.append("1");
    }
}
