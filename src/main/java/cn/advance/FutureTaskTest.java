package cn.advance;

import cn.liuhp.SleepUtils;

import java.util.concurrent.*;

public class FutureTaskTest {


    public static void main(String[] args) {
        fun1();
    }



    /*
    * 对于有返回值的任务的使用
    * 1、实现Callable接口
    * 2、定制FutureTask任务结果
    * 3、Thread去执行FutureTask；
    * 4、FutureTask.get 获取结果；
    * */
    private static void fun1() {
        FutureTaskTest futureTaskTest = new FutureTaskTest();
        FutureTask<String> task1 = new FutureTask<>(new FutureTaskTest.CustomTask());
        FutureTask<String> task2 = new FutureTask<>(futureTaskTest.new CustomTaskTwo());
        new Thread(task1).start();
        new Thread(task2).start();
        try {
            System.out.println(task1.get());
            System.out.println(task2.get(1, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }



    /*
    * 内部静态类，静态的不依赖于外部对象
    * */
    private static class CustomTask implements Callable<String> {

        @Override
        public String call() throws Exception {
            System.out.println("CustomTask config someting");
            return "CustomTask.success";
        }
    }

    /*
     * 内部静态类，静态的不依赖于外部对象
     * */
    private class CustomTaskTwo implements Callable<String> {

        @Override
        public String call() throws Exception {
            System.out.println("CustomTaskTwo config someting");
            SleepUtils.sleepMillis(1500);
            return "CustomTaskTwo.success";
        }
    }


}
