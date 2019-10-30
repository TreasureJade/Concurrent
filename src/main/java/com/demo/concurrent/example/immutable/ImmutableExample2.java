package com.demo.concurrent.example.immutable;

import com.demo.concurrent.annoations.NotThreadSafe;
import com.demo.concurrent.annoations.ThreadSafe;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * @author hobo
 * @description 测试unmodifiableMap()
 */
@Slf4j
@ThreadSafe
public class ImmutableExample2 {

    private  static Map<Integer,Integer> map = Maps.newHashMap();

    static {
        map.put(1,2);
        map.put(3,4);
        map.put(5,6);
        map  = Collections.unmodifiableMap(map);
    }

    public static void main(String[] args) {
        // unmodifiableMap()处理过后的数据是不能被修改的
        // unmodifiableMap（）会生成一个新的map，将修改操作都抛出异常。
        map.put(1,3);
        log.info("{}",map.get(1));
    }


}
