package com.demo.concurrent.example.immutable;

import com.demo.concurrent.annoations.NotThreadSafe;
import com.demo.concurrent.annoations.ThreadSafe;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * @author hobo
 * @description Immutable相关类
 */
@Slf4j
@ThreadSafe
public class ImmutableExample3 {

    // .of()初始化，且大小不受限制
    private final static ImmutableList LIST = ImmutableList.of(1, 2, 3);

    private final static ImmutableSet SET = ImmutableSet.copyOf(LIST);

    private final static ImmutableMap<Integer, Integer> MAP = ImmutableMap.of(1, 2, 3, 4);

    private final static ImmutableMap<Integer, Integer> MAP2 = ImmutableMap.<Integer, Integer>builder()
            .put(1, 2).put(3, 4).put(5, 6).build();

    public static void main(String[] args) {
        //   不允许修改(List一致)
        //        LIST.add(4);
        //        SET.add(4);
        //
        //        MAP2.put(3,2);
        //        MAP.put(5,6);
        System.out.println(MAP.get(3));
    }
}
