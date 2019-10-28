package com.demo.concurrent.example.publish;

import com.demo.concurrent.annoations.NotRecommend;
import com.demo.concurrent.annoations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hobo
 * @description 对象逸出实例演示
 */
@Slf4j
@NotThreadSafe
@NotRecommend
public class Escape {

    private int thisCanBeEscape = 0;

    public Escape() {
        new InnerClass();
    }

    private class InnerClass {
        public InnerClass() {
            // 对封装实例的隐含的调用
            // 对象没有被正确构造完成之前就被引用  不安全！！
            log.info("{}", Escape.this.thisCanBeEscape);
        }
    }

    public static void main(String[] args) {
        new Escape();
    }

}
