package cn.liuhp;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description: 冲入锁测试
 * @author: liuhp534
 * @create: 2019-03-24 21:25
 */
public class ReentrantLockTest {

    public static void main(String[] args) {
        final Lock lock = new ReentrantLock();//新建一把锁
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock.lock();//锁开始
                    for (int i = 0; i < 5; i++) {
                        TimeUnit.SECONDS.sleep(1);
                        System.out.println(Thread.currentThread().getName() + i);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();//释放锁
                }
            }
        }).start();
        SleepUtils.sleepSeconds(1);
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock.lockInterruptibly();//锁开始
                    System.out.println(Thread.currentThread().getName() + "");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        lock.unlock();//释放锁
                    } catch (Exception e) {
                        System.out.println("释放锁异常");
                    }
                }
            }
        });
        t2.start();
        SleepUtils.sleepSeconds(1);
        System.out.println("尝试打断");
        t2.interrupt();//这个打断无效哦
    }
}
