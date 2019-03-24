package cn.liuhp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @description: 门闩练习
 * @author: liuhp534
 * @create: 2019-03-24 18:05
 */
public class PracticeCountDown {

    static CountDownLatch latch = new CountDownLatch(5);//加上5次闩


    public static void main(String[] args) {
        final Container container = new Container();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("size start...");
                if (container.size() != 5) {
                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(container.size());
                System.out.println("size end...");
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("add start...");
                for (int i=0; i < 10; i++) {
                    container.add(i + "");
                    latch.countDown();
                    System.out.println("add " + i);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("add end...");
            }
        }).start();
    }

    static class Container {
        List<String> list = new ArrayList<String>();

        public void add(String str) {
            list.add(str);
        }

        public int size() {
            return list.size();
        }
    }

}
