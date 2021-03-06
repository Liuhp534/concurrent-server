package t04;

import cn.liuhp.SleepUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description: 同步容器
 * 生产者消费者模型，开始生产的时候有来有回，但是两个生产线程阻塞之后，唤醒之后只有一个线程有优先权？？？
 * @author: hz16092620
 * @create: 2019-03-25 16:49
 */
public class PracticeConcurrentContainer {

    public static void main(String[] args) {
        final ConcurrentContainer container = new ConcurrentContainer();
        Thread consumer1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        SleepUtils.sleepSeconds(1);
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
                        SleepUtils.sleepMillis(100);
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
                        SleepUtils.sleepMillis(100);
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
 * synchronized同步容器
 */
class ConcurrentContainer {

    private int maxSize = 10;

    private List<String> list = new LinkedList<String>();

    /**
     * 添加元素
     */
    public void addE(String str) throws Exception {
        synchronized (this) {
            while (this.size() >= 10) {//需要用while配置wait/notify使用，这样可以持续判断
                this.wait();//达到最大个数的时候阻塞状态
            }
            System.out.println(Thread.currentThread().getName() + "生产产品 : " + str);
            this.list.add(Thread.currentThread().getName() + " 生产产品 : " + str);
            this.notifyAll();//唤醒其他线程
        }
    }

    /**
     * 获取元素
     */
    public String getE() throws InterruptedException {
        synchronized (this) {
            while (this.size() <= 0) {
                this.wait();
            }
            String temp = null;
            temp = this.list.get(0);
            this.list.remove(temp);
            this.notifyAll();//唤醒其他线程
            return temp;
        }
    }

    public int size() {//需不要加锁？？？
        System.out.println("----------------------------------" + list.size() + "--------------------------------");
        return list.size();
    }

    public ConcurrentContainer() {
    }

    public ConcurrentContainer(int maxSize) {
        this.maxSize = maxSize;
    }
}




