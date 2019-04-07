package t04;

import cn.liuhp.SleepUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description: 同步容器
 * 生产者消费者模型，开始生产的时候有来有回，但是两个生产线程阻塞之后，唤醒之后两个线程交叉进行和synchronize不同，没有加fair=true
 * @author: hz16092620
 * @create: 2019-03-25 16:49
 */
public class PracticeReentrantLockContainer {

    public static void main(String[] args) {
        final ReentrantLockContainer container = new ReentrantLockContainer();
        Thread consumer1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        SleepUtils.sleepSeconds(2);
                        System.out.println("消费产品 : " + container.getE());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        consumer1.start();
        Thread productor1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 20; i++) {
                        SleepUtils.sleepMillis(300);
                        container.addE(i+"");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Thread productor2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 20; i++) {
                        SleepUtils.sleepMillis(300);
                        container.addE(i+"");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        productor1.start();
        productor2.start();
    }

}

/**
 * ReentrantLock同步容器
 */
class ReentrantLockContainer {

    private int maxSize = 10;

    private List<String> list = new LinkedList<String>();

    private Lock lock = new ReentrantLock(true);

    private Condition consumer = lock.newCondition();
    private Condition productor = lock.newCondition();


    /**
     * 添加元素
     */
    public void addE(String str) throws Exception {
        try {
            lock.lock();
            while (this.size() >= 10) {//需要用while配置wait/notify使用，这样可以持续判断
                productor.await();//达到最大个数的时候阻塞状态
            }
            System.out.println(Thread.currentThread().getName() + "生产产品 : " + str);
            this.list.add(Thread.currentThread().getName() + " 生产产品 : " + str);
            consumer.signalAll();//唤醒其他线程
        } finally {
            lock.unlock();
        }
    }

    /**
     * 获取元素
     */
    public String getE() throws InterruptedException {
        try {
            lock.lock();
            while (this.size() <= 0) {
                consumer.await();//没有产品的时候阻塞状态
            }
            String temp = null;
            temp = this.list.get(0);
            this.list.remove(temp);
            productor.signalAll();//唤醒其他线程
            return temp;
        } finally {
            lock.unlock();
        }
    }

    public int size() {//需不要加锁？？？
        System.out.println("----------------------------------" + list.size() + "--------------------------------");
        return list.size();
    }

    public ReentrantLockContainer() {
    }

    public ReentrantLockContainer(int maxSize) {
        this.maxSize = maxSize;
    }
}


