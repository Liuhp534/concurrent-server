package cn.threadpool;

import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ScheduleThreadPool {


    private static AtomicInteger ato = new AtomicInteger(0);

    public static void main(String[] args) {
        fun1();
    }

    private static void fun1() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);
        executor.scheduleWithFixedDelay(() -> {
            System.out.println(ato.getAndIncrement());
        }, 3, 1, TimeUnit.SECONDS);

    }
}
