package t06;

import java.util.concurrent.SynchronousQueue;

/**
 * @description: 练习同步转换队列
 * @author: liuhp534
 * @create: 2019-04-13 11:53
 */
public class PracticeSynchronusQueue {

    public static void main(String[] args) {
        m1();
    }

    private static void m1()  {
        final SynchronousQueue<String> synchronousQueue = new SynchronousQueue<>();
        //synchronousQueue.add("add e");报错
        System.out.println(synchronousQueue.offer("offer e"));//失败
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronousQueue.put("put e");//阻塞，不设置到队列中
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {//都可以消费
                System.out.println(synchronousQueue);
                //System.out.println(synchronousQueue.poll());
                try {
                    System.out.println(synchronousQueue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
