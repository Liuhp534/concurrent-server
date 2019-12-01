package cn.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/*
*
* 线程池工具
* */
public class ThreadPoolExecutorUtils {


    /*
    * 线程池
    * */
    public static final ExecutorService executorService = new ThreadPoolExecutor(500, 500, 10L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>());//核心线程数2，最大线程数4，线程多久空闲销毁，工作队列



}
