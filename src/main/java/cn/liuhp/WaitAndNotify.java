package cn.liuhp;

import cn.threadpool.ThreadPoolExecutorUtils;

/*
*
* wait notify的使用
* */
public class WaitAndNotify {


    public static void main(String[] args) {
        fun1();
    }

    /*
     * wait notify必须在synchronized对象块中
     * */
    private static void fun1() {
        WaitAndNotify test = new WaitAndNotify();
        ThreadPoolExecutorUtils.executorService.submit(() -> {
            test.fun2();
        });

        ThreadPoolExecutorUtils.executorService.submit(() -> {
            test.fun3();
        });
    }

    //如果synchronized方法的话，是static方法，需要通过其他lock
    private static Object lock = new Object();

    private static void fun2() {
        synchronized (lock) {
            try {
                lock.wait();
                System.out.println("fun2 完成");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    private static void fun3() {
        synchronized (lock) {
            SleepUtils.sleepSeconds(2);
            lock.notify();
            System.out.println("fun3 notify");
        }

    }


    //如果synchronized方法的话，不能是static方法
    private synchronized void fun4() {
        try {
            this.wait();
            System.out.println("fun4 完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    //如果synchronized方法的话，不能是static方法
    private synchronized void fun5() {
        SleepUtils.sleepSeconds(2);
        this.notify();
        System.out.println("fun5 notify");

    }
}
