package cn.lock;

import cn.liuhp.SleepUtils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @description: 自旋锁
 * @author: liuhp534
 * @create: 2020-02-15 16:30
 */
public class SpinLock {

    int count = 0;

    public void addCount() {
        //System.out.println(Thread.currentThread().getName() + "\t递增");
        count ++;
    }

    /*
    * count是正确的，也就是说，加锁成功了。但是打印加锁的信息会显示同时两个线程加了锁。
    * */
    public static void main(String[] args) throws InterruptedException {
        int threadSize = 1000;
        CountDownLatch countDownLatch = new CountDownLatch(threadSize);
        SpinLock spinLock = new SpinLock();
        for (int i = 0; i < threadSize; i ++) {
            new Thread(() -> {
                spinLock.lock();
                spinLock.addCount();
                //SleepUtils.sleepMillis(50);
                spinLock.unLock();
                countDownLatch.countDown();
            }, "t" + i).start();
        }
        countDownLatch.await();
        System.out.println(spinLock.count);
    }

    private static  volatile AtomicReference<Boolean> atomicR = new AtomicReference<>(Boolean.FALSE);

    private AtomicReference<Thread> atomicT = new AtomicReference<>(null);

    public void lock() {
        //while 自旋的方式获取锁
        Thread thread = Thread.currentThread();
        while (!atomicT.compareAndSet(null, thread)) {
            //System.out.println("未获取到锁");
            //SleepUtils.sleepMillis(10);
        }
        System.out.println("获取到锁");
        /*while (!atomicR.compareAndSet(Boolean.FALSE, Boolean.TRUE)) {
            //当while一定时间，返回false不继续
        }*/
        //System.out.println(Thread.currentThread().getName() + "\t获取到锁" + "\t 获取锁的线程=" + atomicT.get().getName());

    }

    public void unLock() {
        Thread thread = Thread.currentThread();
        System.out.println("释放锁");
        while (!atomicT.compareAndSet(thread, null)) {
            System.out.println("释放锁失败");
        }
        /*while (!atomicR.compareAndSet(Boolean.TRUE, Boolean.FALSE)) {

        }*/
        //System.out.println(Thread.currentThread().getName() + "\t释放锁");
    }






}
