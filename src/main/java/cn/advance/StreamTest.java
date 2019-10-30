package cn.advance;

import cn.liuhp.SleepUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class StreamTest {


    public static void main(String[] args) {
        //fun1();
        SleepUtils.sleepSeconds(30);
        fun2();
        SleepUtils.sleepSeconds(30);
    }



    /*
    * stream的基本使用
    * */
    private static void fun1() {
        Stream<String> stringStream = Stream.of("1", "2", "c", "d");
        stringStream.forEach(key -> System.out.println(key));
    }

    /*
    * 并行执行
    * */
    private static void fun2() {

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        list.parallelStream().forEach(key -> System.out.println(key));
    }
}
