package cn.advance;

import cn.liuhp.SleepUtils;

public class ThreadInterruptTest extends Thread {

    public ThreadInterruptTest(String name) {
        super(name);
    }

    public static void main(String[] args) {
        //fun1();

        fun2();
    }


    @Override
    public void run() {
        while (!Thread.interrupted()) {//
            System.out.println(Thread.currentThread().getName() + " running " + interrupted());
            SleepUtils.sleepMillis(1000);
        }
    }

    private static void fun1() {
        Thread t1 = new ThreadInterruptTest("thread one");

        Thread t2 = new ThreadInterruptTest("thread two");

        t1.start();
        t2.start();

        //SleepUtils.sleepMillis(3000);
        t1.interrupt();//打断睡眠，但是线程还是会继续执行
        //System.exit(-1);
    }

    /*
    * 这写法很特殊啊
    * */
    private static void fun2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("runnable");
            }
        }) {
            @Override
            public void run() {
                System.out.println("sub");
            }
        }.start();
    }


}
