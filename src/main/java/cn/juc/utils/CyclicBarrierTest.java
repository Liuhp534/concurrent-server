package cn.juc.utils;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @description: 屏障
 * @author: liuhp534
 * @create: 2020-02-16 09:41
 * 做加法，可重复使用屏障。直到增加到指定数据量，执行；
 */
public class CyclicBarrierTest {

    static CyclicBarrier cyclicBarrier = new CyclicBarrier(20, () -> {
        System.out.println("会议开始");
    });

    public static void main(String[] args) {
        fun1();
        fun1();
    }


    private static void fun1() {


        for (int i=0; i < 20; i++) {
            new Thread(() -> {
                System.out.println("等待开会");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println("开会");
            }).start();
        }
    }
}
