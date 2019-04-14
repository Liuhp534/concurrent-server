package t08;

import cn.liuhp.SleepUtils;

import java.sql.Time;
import java.util.concurrent.*;

/**
 * @description: 练习future
 * @author: liuhp534
 * @create: 2019-04-13 17:12
 */
public class PracticeFuture {

    //private static final ExecutorService executorService = Executors.newFixedThreadPool(3);

    public static void main(String[] args) {
        m1();
    }

    private static void m1() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        /*Future<String> future = (Future<String>) executorService.submit(new Runnable() {
            @Override
            public void run() {
                SleepUtils.sleepMillis(300);
                System.out.println(Thread.currentThread().getName());
            }
        });*/
        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {

                System.out.println(Thread.currentThread().getName());
                SleepUtils.sleepMillis(3000);
                System.out.println("返回结果");
                /*String str = "1";
                if ("1".equals(str)) {
                    throw new Exception();
                }*/
                return Thread.currentThread().getName();
            }
        });

        System.out.println(executorService);
        System.out.println(future);
        try {
            //SleepUtils.sleepMillis(30000);
            System.out.println(future.get(1, TimeUnit.SECONDS));//就算执行有异常，也需要等到get时候才调用
        } catch (InterruptedException e) {
            System.out.println("11111");
            e.printStackTrace();
        } catch (ExecutionException e) {
            System.out.println("2222");
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        System.out.println(executorService);
    }
}
