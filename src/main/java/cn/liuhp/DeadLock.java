package cn.liuhp;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @description: 死锁例子
 * @author: hz16092620
 * @create: 2019-03-25 15:50
 */
public class DeadLock {


    public static void main(String[] args) {
        //test1();
        //test2();
        test3();
    }

    /*
     * 死锁的例子
     * */
    private static void test1() {
        DeadLock deadLock = new DeadLock();
        new Thread(() -> deadLock.m1()).start();
        new Thread(() -> deadLock.m2()).start();
    }

    /*
     * volatile的使用
     * */
    private static void test2() {
        DeadLock deadLock = new DeadLock();
        new Thread(() -> deadLock.m2()).start();
        SleepUtils.sleepSeconds(3);
        System.out.println("m2 退出执行逻辑");
        deadLock.flag = Boolean.FALSE;//执行重排序
    }

    /*
     * 死锁的例子通过ReentrantLock
     * */
    private static void test3() {
        ReentrantLockDeadLockTest reentrantLockDeadLockTest1 = new DeadLock.ReentrantLockDeadLockTest("lockone");
        ReentrantLockDeadLockTest reentrantLockDeadLockTest2 = new DeadLock.ReentrantLockDeadLockTest("locktwo");
        new Thread(() -> reentrantLockDeadLockTest1.m3()).start();
        new Thread(() -> reentrantLockDeadLockTest1.m4()).start();
    }

    volatile boolean flag = Boolean.TRUE;
    Object lock1 = new Object();
    Object lock2 = new Object();

    /**
     * @param
     * @return
     * @throws
     * @Description: m1完成任务需要lock1和lock2，先获取lock1，再去获取lock2
     * @author hz16092620
     * @date 2019/3/25 15:56
     */
    public void m1() {
        synchronized (lock1) {//获取lock1锁
            System.out.println("m1 使用lock1");
            SleepUtils.sleepSeconds(1);
            synchronized (lock2) {
                System.out.println("m1操作 lock2 " + lock2.toString());
            }
            System.out.println("m1 释放lock1");
        }
    }

    /**
     * @param
     * @return
     * @throws
     * @Description: m2 一直使用说lock2，m1一直拿不到
     * @author hz16092620
     * @date 2019/3/25 15:55
     */
    public void m2() {
        synchronized (lock2) {
            System.out.println("m2 使用lock2");
            SleepUtils.sleepSeconds(1);
            synchronized (lock1) {
                System.out.println("m2 操作lock1");
            }
            while (flag) {

            }
            System.out.println("m2 释放lock2");
        }
    }


    /*
     * 静态内部类
     * */
    private static class ReentrantLockDeadLockTest {
        /*
         * ===============通过ReentranLock
         * */
        private String name;

        private static ReentrantLock reentrantLock1 = new ReentrantLock();

        private static ReentrantLock reentrantLock2 = new ReentrantLock();

        public ReentrantLockDeadLockTest(String name) {
            this.name = name;
        }

        /*
         * 测试ReentranLock
         * */
        public void m3() {
            try {
                reentrantLock1.lock();
                System.out.println(this.name + ".m3 使用reentrantLock1");
                SleepUtils.sleepSeconds(1);
                try {
                    reentrantLock2.lock();
                    System.out.println(this.name + ".m3 使用reentrantLock2");
                } finally {
                    reentrantLock2.unlock();
                }
            } finally {
                reentrantLock1.unlock();//一定要释放掉
            }
        }

        /*
         * 测试ReentranLock
         * */
        public void m4() {
            try {
                reentrantLock2.lock();
                System.out.println(this.name + ".m4 使用reentrantLock2");
                SleepUtils.sleepSeconds(1);
                try {
                    reentrantLock1.lock();
                    System.out.println(this.name + ".m4 使用reentrantLock1");
                } finally {
                    reentrantLock1.unlock();
                }
            } finally {
                reentrantLock2.unlock();//一定要释放掉
            }
        }
    }


}
