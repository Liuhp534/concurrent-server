package cn.lock;

import cn.liuhp.SleepUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @description: 读写锁
 * @author: liuhp534
 * @create: 2020-02-15 19:19
 */
public class ReadWriteLock {

    public static void main(String[] args) throws InterruptedException {
        ReadWriteLock readWriteLock = new ReadWriteLock();
        for (int i = 0; i < 10000; i ++) {
            int finalI = i;
            new Thread(() -> {
                readWriteLock.put(finalI +"", finalI);
            }, "t" + i).start();
        }

        SleepUtils.sleepMillis(3000);
        System.out.println(readWriteLock.cache.size());
        /*for (int i = 0; i < 50; i ++) {
            int finalI = i;
            new Thread(() -> {
                readWriteLock.get(finalI+"");
            }, "t" + i).start();
        }*/
    }

    private static void fun1() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10000);
        Map<String, Object> temp = new HashMap<>();
        ReadWriteLock readWriteLock = new ReadWriteLock();
        for (int i = 0; i < 10000; i ++) {
            int finalI = i;
            new Thread(() -> {
                readWriteLock.cache.put(finalI +"", finalI);
                countDownLatch.countDown();
            }, "t" + i).start();
        }
        countDownLatch.await();
        System.out.println(readWriteLock.cache.size());
    }

    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    private  Map<String, Object> cache = new HashMap<>();

    public void put(String key, Object value) {
        rwLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t,正在写入数据=" + key);
            //SleepUtils.sleepMillis(100);
            cache.put(key, value);
            System.out.println(Thread.currentThread().getName() + "\t,写入完成");
        } catch (Exception e) {
            System.out.println("异常");
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public void get(String key) {
        rwLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t,正在获取数据");
            SleepUtils.sleepMillis(100);
            Object value = cache.get(key);
            System.out.println(Thread.currentThread().getName() + "\t,获取完成=" + value);
        } catch (Exception e) {
            System.out.println("异常");
        } finally {
            rwLock.readLock().unlock();
        }

    }




}
