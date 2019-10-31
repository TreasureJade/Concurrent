package com.demo.concurrent.example.concurrent;

import com.demo.concurrent.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.concurrent.*;

/**
 * @author hobo
 * @description 验证CopyOnWriteArraySet 写操作为线程安全
 */
@Slf4j
@ThreadSafe
public class CopyOnWriteArraySetExample {
    // 请求总数
    public static int clientTotal = 5000;

    // 同时并发执行的线程数
    public static int threadTotal = 200;

    private static Set<Integer> set = new CopyOnWriteArraySet<>();


    public static void main(String[] args) throws Exception{
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);

        for (int i = 0; i < clientTotal; i++) {
            int finalI = i;
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    add(finalI);
                    semaphore.release();
                } catch (Exception e) {
                    log.error("exception", e);
                }
                countDownLatch.countDown();
            });

        }
        countDownLatch.await();
        executorService.shutdown();
        log.info("count:{}", set.size());

    }


    private static void add(int i) {
        // add操作加锁
        set.add(i);
    }
}
