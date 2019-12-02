package cn.threadpool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/*
*
* 线程池工具
* */
public class ThreadPoolExecutorUtils {
    private static ThreadFactory factory = new ThreadFactory() {

        private final AtomicInteger integer = new AtomicInteger();

        private int count = 0;

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "john, ThreadPool thread: " + integer.getAndIncrement() + ", count=" + count ++);
        }
    };

    /*
    * 线程池
    * */
    public static final ExecutorService executorService
            = new ThreadPoolExecutor(500, 500, 10L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(), factory);//核心线程数2，最大线程数4，线程多久空闲销毁，工作队列



}
