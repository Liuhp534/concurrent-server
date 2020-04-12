package cn.juc.utils;

import cn.liuhp.SleepUtils;

import java.util.concurrent.Semaphore;

/**
 * @description: 信号量
 * @author: liuhp534
 * @create: 2020-02-16 10:05
 */
public class SemaphoreTest {

    public static void main(String[] args) {
        fun1();
    }



    private static void fun1() {
        Semaphore semaphore = new Semaphore(3);

        for (int i=0; i < 10; i ++) {
            int finalI = i;
            new Thread(() -> {
               try {
                   semaphore.acquire();
                   System.out.println("获取车位" + finalI);
                   SleepUtils.sleepSeconds(3);
                   System.out.println("释放车位" + finalI);
               } catch (Exception e) {
                   e.printStackTrace();
               } finally {
                   semaphore.release();
               }
           }).start();
        }
    }
}
