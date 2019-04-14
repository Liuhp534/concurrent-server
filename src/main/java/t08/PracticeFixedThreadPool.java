package t08;

import cn.liuhp.SleepUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description: 练习固定线程池
 * @author: liuhp534
 * @create: 2019-04-13 14:43
 */
public class PracticeFixedThreadPool {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(3);

    public static void main(String[] args) {
        m1();
    }

    private static void m1() {
        for (int i = 0; i < 10; i ++) {//10个任务到线程池中去执行
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    SleepUtils.sleepMillis(300);
                    System.out.println(Thread.currentThread().getName());
                }
            });
        }

        //优雅关闭
        System.out.println(executorService);//shutdown前打印的
        //executorService.shutdown();
        System.out.println(executorService);//shutdown后打印的
        System.out.println(executorService.isTerminated());//资源回收了才会是true
        System.out.println(executorService.isShutdown());//只要调用了就会true

        SleepUtils.sleepSeconds(2);
        System.out.println(executorService.isTerminated());//资源回收了才会是true
        System.out.println(executorService.isShutdown());//只要调用了就会true
        System.out.println(executorService);//资源回收之后
    }
}
