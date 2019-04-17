/**
 * 并发容器 - ConcurrentLinkedQueue
 * 队列 - 链表实现的。
 */
package t06;

import cn.liuhp.SleepUtils;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

public class Test_03_ConcurrentLinkedQueue {

    public static void main(String[] args) {
        fun2();
    }
    /*100个线程去跑，*/
    private static void fun2() {
        int threadSize = 1000;
        final Queue<String> threadsQueue = new ConcurrentLinkedQueue<String>();
        final CountDownLatch latch = new CountDownLatch(threadSize);
        Thread[] threads = new Thread[threadSize];
        for (int i = 0; i < threadSize; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10000; i++) {
                        threadsQueue.offer(Thread.currentThread().getName() + " " + i);
                    }
                    latch.countDown();
                }
            });
        }
        for (Thread thread : threads) {
            thread.start();
        }
        //SleepUtils.sleepMillis(500);
        try {
            long start = System.currentTimeMillis();
            latch.await();//也就是只看门闩的个数，只要达到要求就通行。
            System.out.println("等待 " + (System.currentTimeMillis() - start));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(threadsQueue.size() == threadSize * 10000);//100 * 10000 = 1000000
    }

    private static void fun1() {
        Queue<String> queue = new ConcurrentLinkedQueue<String>();
        for (int i = 0; i < 10; i++) {
            queue.offer("value" + i);
            queue.add("value add" + i);
        }

        System.out.println(queue);
        System.out.println(queue.size());

        // peek() -> 查看queue中的首数据
        System.out.println(queue.peek());
        System.out.println(queue.size());

        // poll() -> 获取queue中的首数据
        System.out.println(queue.poll());
        System.out.println(queue.size());
    }

}
