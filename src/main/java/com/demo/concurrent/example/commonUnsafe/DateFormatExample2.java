package com.demo.concurrent.example.commonUnsafe;

import com.demo.concurrent.annoations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author hobo
 * @description SimpleDateFormat 非线程安全类 多线程出现异常 -->采用堆栈封闭方法
 */
@Slf4j
public class DateFormatExample2 {



    private static SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
    // 请求总数
    public static int clientTotal = 5000;

    // 同时并发执行的线程数
    public static int threadTotal = 50;

    public static void main(String[] args) throws Exception {
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

    }

    private static void add() {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            format.parse("20180208");
        } catch (ParseException e) {
            log.error("parse exception",e);
        }
    }

}
