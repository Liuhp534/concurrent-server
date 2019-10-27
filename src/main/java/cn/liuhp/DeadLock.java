package cn.liuhp;

/**
 * @description: 死锁例子
 * @author: hz16092620
 * @create: 2019-03-25 15:50
 */
public class DeadLock {

    volatile boolean flag = Boolean.TRUE;
    Object lock1 = new Object();
    Object lock2 = new Object();

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
    * @Description:
     * m2 一直使用说lock2，m1一直拿不到
    * @param
    * @return
    * @throws
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

    public static void main(String[] args) {
        test1();
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
}
