package com.demo.concurrent.example.atomic;

import com.demo.concurrent.annoations.ThreadSafe;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author hobo
 * @description AtomicReference.compareAndSet的使用实例
 */
@Slf4j
@ThreadSafe
public class AtomicExample5 {

    private static AtomicIntegerFieldUpdater<AtomicExample5> updater = AtomicIntegerFieldUpdater.newUpdater(AtomicExample5.class, "count");

    /** 需要特殊修饰符volatile修饰 以及 非static*/
    @Getter
    public volatile int count = 100;


    private static AtomicExample5 example5 = new AtomicExample5();

    public static void main(String[] args) {
        if (updater.compareAndSet(example5,100,120)){
            log.info("更新成功,{}",example5.getCount());
        }
        // 没有执行
        if (updater.compareAndSet(example5,100,120)){
            log.info("更新成功,{}",example5.getCount());
        }else {
            log.error("更新失败,{}",example5.getCount());
        }
    }
}
