package t08;

import cn.liuhp.SleepUtils;

import java.util.concurrent.*;

/**
 * @description: 练习线程类
 * @author: liuhp534
 * @create: 2019-04-14 15:12
 */
public class PracticeThreadPoolExecutor {

    static ExecutorService executorService = new ThreadPoolExecutor(2, 4, 10L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>());//核心线程数2，最大线程数4，线程多久空闲销毁，工作队列


    static ExecutorService fixedExecutorService = Executors.newFixedThreadPool(2);


    public static void main(String[] args) {
        /*System.out.println(fixedExecutorService);
        System.out.println(executorService);*/
        m1();
    }


    private static final void m1() {
        ExecutorService executorService = new ThreadPoolExecutor(1, 4, 5L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());//核心线程数2，最大线程数4，线程多久空闲销毁，工作队列
        System.out.println(executorService);
        for (int i = 0; i < 10; i ++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    SleepUtils.sleepSeconds(1);
                    System.out.println(Thread.currentThread().getName());
                }
            });
        }
        System.out.println(executorService);
        SleepUtils.sleepSeconds(7);
        System.out.println(executorService);
        /*ExecutorService fixedExecutorService = Executors.newFixedThreadPool(2);
        System.out.println(fixedExecutorService);*/
    }
}
