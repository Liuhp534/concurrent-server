package cn.juc.queue;

import cn.liuhp.SleepUtils;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description: 生产和消费利用阻塞队列
 * @author: liuhp534
 * @create: 2020-02-17 16:54
 */
public class ProAndConTest {


    public static void main(String[] args) {
        fun1();
    }

    private static void fun1() {
        ProductResource productResource = new ProductResource();
        productResource.setBlockingQueue(new ArrayBlockingQueue<>(5));
        new Thread(() -> {
            productResource.product();
        }).start();

        new Thread(() -> {
            productResource.consumer();
        }).start();
    }





}

class ProductResource {

    private volatile boolean FLAG = Boolean.TRUE;

    private BlockingQueue<String> blockingQueue;

    private AtomicInteger atomicInteger = new AtomicInteger();

    public void product() {
        String data = null;
        while (FLAG) {
            try {
                //SleepUtils.sleepSeconds(1);
                data = atomicInteger.incrementAndGet() + "";
                if (blockingQueue.offer(data, 1, TimeUnit.SECONDS)) {
                    System.out.println("生产产品=" + data);
                }
            } catch (InterruptedException e) {
                System.out.println("生产出错");
                e.printStackTrace();
            }
        }
    }

    public void consumer() {
        String data = null;
        while (FLAG) {
            try {
                //SleepUtils.sleepSeconds(5);
                data = blockingQueue.poll(2, TimeUnit.SECONDS);
                System.out.println("获取产品=" + data);
            } catch (InterruptedException e) {
                System.out.println("消费出错");
                e.printStackTrace();
            }
        }
    }


    public BlockingQueue<String> getBlockingQueue() {
        return blockingQueue;
    }

    public void setBlockingQueue(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }
}