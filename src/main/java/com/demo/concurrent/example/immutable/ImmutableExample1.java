package com.demo.concurrent.example.immutable;

import com.demo.concurrent.annoations.NotThreadSafe;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author hobo
 * @description 测试final修饰
 */
@Slf4j
@NotThreadSafe
public class ImmutableExample1 {

    private final static Integer a =1;
    private final static String b ="2";
    private final static Map<Integer,Integer> map = Maps.newHashMap();

    static {
        map.put(1,2);
        map.put(3,4);
        map.put(5,6);
    }

    public static void main(String[] args) {
        // final修饰基本类型不能在修改
        //        a =2;
        //        b = "3"

        //  修饰引用类型不能再指向其他对象
        //        map = Maps.newHashMap();

        // 可修改值
        map.put(1,3);
        log.info("{}",map.get(1));
    }

    private void test(final int a ){
        //    不可修改
        //        a = 1
    }

}
