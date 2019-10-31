package cn.threadpool;

import cn.liuhp.SleepUtils;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description:
 * 1、练习线程类
 * 2、线程池拒绝任务处理，需要在服务器中测试
 * @author: liuhp534
 * @create: 2019-04-14 15:12
 */
public class PracticeThreadPoolExecutor {

    static ExecutorService executorService = new ThreadPoolExecutor(2, 4, 10L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>());//核心线程数2，最大线程数4，线程多久空闲销毁，工作队列


    private static AtomicInteger executeCountUtils = new AtomicInteger();

    private static AtomicInteger rejectCountUtils = new AtomicInteger();

    public static void main(String[] args) {
        m1();

        SleepUtils.sleepSeconds(30);
        System.out.println("执行的=" + executeCountUtils.get());
        System.out.println("拒绝的=" + rejectCountUtils.get());
    }


    /*
    * 执行任务
    * */
    private static final void m1() {
        executorService = new ThreadPoolExecutor(1, 1, 5L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(10), new ThreadPoolRejectHandler());//核心线程数2，最大线程数4，线程多久空闲销毁，工作队列
        //System.out.println(executorService);
        for (int i = 0; i < 100; i ++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    SleepUtils.sleepMillis(100);
                    System.out.println(Thread.currentThread().getName());
                    executeCountUtils.incrementAndGet();
                }
            });
        }
    }

    /*
    * 任务被线程池拒绝处理器
    * */
    private static class ThreadPoolRejectHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            r.run();
            //System.out.println("被拒绝的=" + executor);
            rejectCountUtils.incrementAndGet();
        }
    }
}
