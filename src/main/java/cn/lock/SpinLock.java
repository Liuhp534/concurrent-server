package cn.lock;

import cn.liuhp.SleepUtils;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @description: 自旋锁
 * @author: liuhp534
 * @create: 2020-02-15 16:30
 */
public class SpinLock {


    public static void main(String[] args) {
        SpinLock spinLock = new SpinLock();
        for (int i = 0; i < 5; i ++) {
            new Thread(() -> {
                //spinLock.lock();
                //SleepUtils.sleepMillis(50);
                spinLock.unLock();
            }, "t" + i).start();
        }
    }

    private static final AtomicReference<Boolean> atomicR = new AtomicReference<>(Boolean.FALSE);

    private AtomicReference<Thread> atomicT = new AtomicReference<>(null);

    public void lock() {
        //while 自旋的方式获取锁
        Thread thread = Thread.currentThread();
        while (!atomicT.compareAndSet(null, thread)) {
            //System.out.println("未获取到锁");
        }
        /*while (!atomicR.compareAndSet(Boolean.FALSE, Boolean.TRUE)) {
            //当while一定时间，返回false不继续
        }*/
        System.out.println(Thread.currentThread().getName() + "\t获取到锁" + "\t 获取锁的线程=" + atomicT.get().getName());
        if (!Thread.currentThread().getName().equals(atomicT.get().getName())) {
            System.out.println(Thread.currentThread().getName() + "\t获取到锁" + "\t 获取锁的线程=" + atomicT.get().getName());
            System.out.println("不一致情况");
        }
    }

    public void unLock() {
        Thread thread = Thread.currentThread();
        while (!atomicT.compareAndSet(thread, null)) {
            System.out.println("释放锁失败");
        }
        /*while (!atomicR.compareAndSet(Boolean.TRUE, Boolean.FALSE)) {

        }*/
        System.out.println(Thread.currentThread().getName() + "\t释放锁");
    }






}
