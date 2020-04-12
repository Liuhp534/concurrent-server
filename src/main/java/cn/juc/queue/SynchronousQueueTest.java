package cn.juc.queue;

import java.util.concurrent.SynchronousQueue;

/**
 * @description: 同步队列
 * @author: liuhp534
 * @create: 2020-02-16 14:19
 *
 */
public class SynchronousQueueTest {


    public static void main(String[] args) {
        fun1();
    }


    private static void fun1() {
        SynchronousQueue queue = new SynchronousQueue();

        /*
        * 如果不消费那么就一直阻塞
        * */
        new Thread(() -> {
            try {
                queue.put("111");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            System.out.println(queue.poll());
        }).start();
    }
}
