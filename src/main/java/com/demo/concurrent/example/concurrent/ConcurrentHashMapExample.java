package com.demo.concurrent.example.concurrent;

import com.demo.concurrent.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @author hobo
 * @description 验证ConcurrentSkipListMap 线程安全
 */
@Slf4j
@ThreadSafe
public class ConcurrentHashMapExample {

    // 请求总数
    // 若线程安全。则长度应为5000
    public static int clientTotal = 5000;

    // 同时并发执行的线程数
    public static int threadTotal = 50;

    private static Map<Integer,Integer> map = new ConcurrentSkipListMap<>();


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
        log.info("count:{}", map.size());

    }

    private static void add(int i) {
        map.put(i,i);
    }
}
