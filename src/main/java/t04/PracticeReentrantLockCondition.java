package t04;

import cn.liuhp.SleepUtils;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description: 重入锁condtion的使用
 * @author: hz16092620
 * @create: 2019-04-17 16:48
 */
public class PracticeReentrantLockCondition {

    public static void main(String[] args) {
        //m1();
        m2();
        //m3();
    }
    /*通过condition，按照abc顺序打印
    * 1、开启三个线程，每个线程对应自己的打印目标；
    * 2、三个线程共用打印变量，打印完成，打印变量的值变为下一个顺序值；
    * 3、每个线程需要while(true)，条件的判断需要while去，用if只会执行一次，条件不满足就挂起，满足就打印并唤醒其他线程。
    * */
    private static void m3() {
        PrintUtil printUtil = new PrintUtil();
        new PrintThread("A", printUtil).start();
        new PrintThread("B", printUtil).start();
        new PrintThread("C", printUtil).start();
    }

    /*通过synchronized实现交替打印*/
    private static final void m2() {
        final Object obj = new Object();
        for (int i = 0; i < 3; i ++) {
            new Thread(() -> {
                synchronized (obj) {
                    System.out.println(Thread.currentThread().getName() + " 进入锁");
                    while (true) {
                        obj.notifyAll();
                        SleepUtils.sleepMillis(1000);
                        System.out.println(Thread.currentThread().getName() + "- abc");
                        try {
                            System.out.println(Thread.currentThread().getName() + " 进入等待");
                            obj.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, "" + i).start();
            SleepUtils.sleepSeconds(5);
        }
    }

    /*通过 condition实现线程轮询打印abc*/
    private static final void m1() {
        final Lock reenLock = new ReentrantLock();
        final Condition con1 = reenLock.newCondition();

        for (int i = 0; i < 3; i ++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        reenLock.lock();
                        //System.out.println(Thread.currentThread().getName() + " 进入锁");
                        while (true) {
                            con1.signalAll();
                            SleepUtils.sleepMillis(500);
                            System.out.println(Thread.currentThread().getName() + "- abc");
                            con1.await();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        reenLock.unlock();
                    }
                }
            }).start();
        }
    }


}

/*打印线程*/
class PrintThread extends Thread {

    private static final Lock reenLock = new ReentrantLock();
    private static final Condition con1 = reenLock.newCondition();

    private String threadStr = "";

    private PrintUtil printUtil = null;

    public PrintThread(String threadStr, PrintUtil printUtil) {
        this.threadStr = threadStr;
        this.printUtil = printUtil;
    }

    @Override
    public void run() {
        synchronized (this.printUtil) {
            while (true) {
                while (this.threadStr.equals(this.printUtil.getPrintStr())) {
                    SleepUtils.sleepMillis(5000);
                    this.printUtil.printData();
                    this.printUtil.setNextPrintStr(this.threadStr);
                    this.printUtil.notifyAll();//可以唤醒所有的，没有all只会唤醒一个
                }
                try {
                    this.printUtil.wait();
                    System.out.println(Thread.currentThread().getName()+"-唤醒");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        /*try {
            reenLock.lock();
            while (true) {
                while (!this.threadStr.equals(this.printUtil.getPrintStr())) {
                    con1.await();
                }
                SleepUtils.sleepMillis(500);
                this.printUtil.printData();
                this.printUtil.setNextPrintStr(this.threadStr);
                con1.signalAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reenLock.unlock();
        }*/
    }
}
/*打印工具类*/
class PrintUtil {
    private String printStr = "A";

    public void setNextPrintStr(String printStr) {
        if ("A".equals(printStr)) {
            this.printStr = "B";
        } else if ("B".equals(printStr)) {
            this.printStr = "C";
        } else if ("C".equals(printStr)) {
            this.printStr = "A";
        }

    }

    public String getPrintStr() {
        return this.printStr;
    }

    public void printData() {
        System.out.println(this.printStr);
        if ("C".equals(this.printStr)) {
            //System.out.println();
        }
    }
}