package t06;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

/**
 * @description: 转换队列转换队列
 * @author: liuhp534
 * @create: 2019-04-13 11:23
 */
public class PracticeTransferQueue {



    public static void main(String[] args) {
        m1();
    }

    private static void m1() {
        final TransferQueue<String> transferQueue = new LinkedTransferQueue<>();
        transferQueue.add("add e");
        try {
            transferQueue.put("put e");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //生产一个对象，通过transfer方法
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    transferQueue.transfer("transfer e");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        //消费对象
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 3; i ++) {
                        System.out.println(transferQueue.poll());
                    }
                    System.out.println(transferQueue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        System.out.println(transferQueue);

    }
}
