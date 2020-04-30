package cn.lock;

import cn.liuhp.SleepUtils;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @description:
 * @author: liuhp534
 * @create: 2020-04-05 17:16
 */
public class ReadWriteLockTest {


    public static void main(String[] args) {
        //fun1();
        fun4();


    }

    private static void fun4() {
        Thread t1 = new Thread(() -> {
            fun2();
        });
        t1.start();
        SleepUtils.sleepSeconds(2);
        Thread t2 = new Thread(() -> {
            fun3();
        });
        t2.start();
    }

    private static synchronized void  fun2() {
        SleepUtils.sleepSeconds(60);
    }

    private static synchronized void  fun3() {

    }

    /*
    * 读锁能互斥写锁，不互斥读锁
    * 写锁能互斥读写锁
    * */
    private static void fun1() {
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        Thread t1 = new Thread(() -> {
            readWriteLock.readLock().lock();
            System.out.println("获取到读锁1");
            try {
                SleepUtils.sleepSeconds(30);
            } finally {
                readWriteLock.readLock().unlock();
            }
        });
        t1.start();
        SleepUtils.sleepSeconds(3);
        Thread t2 = new Thread(() -> {
            readWriteLock.readLock().lock();
            System.out.println("获取到读锁2");
            try {
                SleepUtils.sleepSeconds(30);
            } finally {
                readWriteLock.readLock().unlock();
            }
        });
        t2.start();
        Thread t3 = new Thread(() -> {
            System.out.println("准备获取写锁1");
            readWriteLock.writeLock().lock();
            System.out.println("获取到写锁1");
            try {
                SleepUtils.sleepSeconds(30);
            } finally {
                readWriteLock.writeLock().unlock();
            }
        });
        t3.start();
    }
}
