package cn.liuhp;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description: 死锁例子
 * @author: hz16092620
 * @create: 2019-03-25 15:50
 */
public class ReentrantLockDeadLock {

    volatile boolean flag = Boolean.TRUE;
    Lock lock1 = new ReentrantLock();
    Lock lock2 = new ReentrantLock();


    /**
    * @Description:
     * m1完成任务需要lock1和lock2，先获取lock1，再去获取lock2
    * @param
    * @return
    * @throws
    * @author hz16092620
    * @date 2019/3/25 15:56
    */
    public void m1() {
        try {
            lock1.lock();
            System.out.println("m1 使用lock1");
            SleepUtils.sleepSeconds(3);
            lock2.lock();
            System.out.println("m1操作 lock2 " + lock2.toString());
        } finally {
            lock1.unlock();
            System.out.println("m1 释放lock1");
            lock2.unlock();
            System.out.println("m1 释放lock2");
        }
    }

    /**
    * @Description:
     * m2 一直使用说lock2，m1一直拿不到
    * @param
    * @return
    * @throws
    * @author hz16092620
    * @date 2019/3/25 15:55
    */
    public void m2() {
        try {
            lock2.lock();
            System.out.println("m2 使用lock2");
            lock1.lock();
            System.out.println("m2 操作lock1");
        } finally {
            lock2.unlock();
            System.out.println("m2 释放lock2");
            lock1.unlock();
            System.out.println("m2 释放lock1");
        }
    }

    public static void main(String[] args) {
        final ReentrantLockDeadLock deadLock = new ReentrantLockDeadLock();
        new Thread(new Runnable() {
            @Override
            public void run() {
                deadLock.m1();
            }
        }).start();

        SleepUtils.sleepSeconds(1);//等lock1锁完成

        new Thread(new Runnable() {
            @Override
            public void run() {
                deadLock.m2();
            }
        }).start();
        SleepUtils.sleepSeconds(10);
        System.out.println("m2 退出执行逻辑");
        deadLock.flag = Boolean.FALSE;
    }
}
