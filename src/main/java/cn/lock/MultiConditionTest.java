package cn.lock;

import cn.liuhp.SleepUtils;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description: 分组唤醒
 * @author: liuhp534
 * @create: 2020-02-16 17:25
 *  实现线程打印顺序abc
 */
public class MultiConditionTest {

    public static void main(String[] args) {
        fun1();
    }


    private static void fun1() {
        MultiPrintUtils printUtils = new MultiPrintUtils();
        SinglePrintUtils singlePrintUtils = new SinglePrintUtils();
        SyncPrintUtils syncPrintUtils = new SyncPrintUtils();


        new Thread(() -> {
            //printUtils.print();
            //singlePrintUtils.print();
            syncPrintUtils.print();
        }, "B").start();
        SleepUtils.sleepSeconds(1);
        new Thread(() -> {
            //printUtils.print();
            //singlePrintUtils.print();
            syncPrintUtils.print();
        }, "C").start();


        SleepUtils.sleepSeconds(1);

        new Thread(() -> {
            //printUtils.print();
            //singlePrintUtils.print();
            syncPrintUtils.print();
        }, "A").start();


    }


}
/*
* 通过synchronize实现打印abc
* */
class SyncPrintUtils {
    private Object lock = new Object();

    private String threadName = "A";//1-A, 2-B, 3-C


    public void print() {
        synchronized (lock) {
            //System.out.println(Thread.currentThread().getName() + "-进入锁");
            try {
                while (true) {
                    //判断， while虚假唤醒
                    while (!threadName.equals(Thread.currentThread().getName()) ) {
                        //System.out.println(Thread.currentThread().getName() + "-进入等待");
                        lock.wait();
                        System.out.println("唤醒=" + Thread.currentThread().getName());
                        SleepUtils.sleepSeconds(10);
                    }
                    //干活
                    this.printAndSetNextThreadName();
                    //通知
                    lock.notifyAll();
                    System.out.println("唤醒全部");
                    SleepUtils.sleepSeconds(30);
                }
            } catch (Exception e) {
                System.out.println("打印状态出错");
            }
        }
    }

    private void printAndSetNextThreadName() {
        SleepUtils.sleepSeconds(1);
        System.out.println(threadName);
        if ("A".equals(threadName)) {
            threadName = "B";
        } else if ("B".equals(threadName)) {
            threadName = "C";
        } else if ("C".equals(threadName)) {
            threadName = "A";
            //System.out.println();
        }
    }


}
/*
 * 通过lock 单个condition实现打印abc
 * */
class SinglePrintUtils {
    private Lock lock = new ReentrantLock();

    private String threadName = "A";//1-A, 2-B, 3-C

    private Condition c1 = lock.newCondition();

    public void print() {
        lock.lock();
        //System.out.println(Thread.currentThread().getName() + "-进入锁");
        try {
            while (true) {
                //判断， while虚假唤醒
                while (!threadName.equals(Thread.currentThread().getName()) ) {
                    //System.out.println(Thread.currentThread().getName() + "-进入等待");
                    c1.await();
                    System.out.println("唤醒=" + Thread.currentThread().getName());
                    //SleepUtils.sleepSeconds(15);
                }
                //干活
                this.printAndSetNextThreadName();
                //通知
                c1.signalAll();
            }
        } catch (Exception e) {
            System.out.println("打印状态出错");
        } finally {
            lock.unlock();
        }
    }

    private void printAndSetNextThreadName() {
        SleepUtils.sleepSeconds(1);
        System.out.println(threadName);
        if ("A".equals(threadName)) {
            threadName = "B";
        } else if ("B".equals(threadName)) {
            threadName = "C";
        } else if ("C".equals(threadName)) {
            threadName = "A";
            //System.out.println();
        }
    }


}
/*
 * 通过lock 分组condition实现打印abc
 * */
class MultiPrintUtils {

    private Lock lock = new ReentrantLock();

    private String threadName = "A";//1-A, 2-B, 3-C

    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void print() {
        lock.lock();
        try {
            while (true) {
                //判断， while虚假唤醒
                while (!threadName.equals(Thread.currentThread().getName()) ) {
                    this.getCondition(Thread.currentThread().getName()).await();
                    System.out.println("唤醒=" + Thread.currentThread().getName());
                }
                //干活
                this.printAndSetNextThreadName();
                //通知
                this.getNextCondition().signal();
            }
        } catch (Exception e) {
            System.out.println("打印状态出错");
        } finally {
            lock.unlock();
        }
    }

    private void printAndSetNextThreadName() {
        SleepUtils.sleepSeconds(1);
        System.out.println(threadName);
        if ("A".equals(threadName)) {
            threadName = "B";
        } else if ("B".equals(threadName)) {
            threadName = "C";
        } else if ("C".equals(threadName)) {
            threadName = "A";
            //System.out.println();
        }
    }

    private Condition getCondition(String _threadName) {
        if ("A".equals(_threadName)) {
            return c1;
        } else if ("B".equals(_threadName)) {
            return c2;
        } else if ("C".equals(_threadName)) {
            return c3;
        }
        return null;
    }

    private Condition getNextCondition() {
        if ("A".equals(threadName)) {
            return c1;
        } else if ("B".equals(threadName)) {
            return c2;
        } else if ("C".equals(threadName)) {
            return c3;
        }
        return null;
    }
}
