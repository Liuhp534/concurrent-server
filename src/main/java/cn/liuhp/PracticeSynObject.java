package cn.liuhp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @description: 练习锁对象来处理问题
 * @author: liuhp534
 * @create: 2019-03-24 17:46
 */
public class PracticeSynObject {

    Container container = new Container();

    Object object = new Object();//锁对象，资源

    void m1Add() {
        synchronized (object) {
            System.out.println("m1Add start");
            try {
                for (int i = 0; i < 10; i++) {
                    TimeUnit.SECONDS.sleep(1);
                    container.add(i + "");
                    System.out.println("add " + i);
                    if (i == 4) {
                        object.notifyAll();
                        object.wait();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("m1Add stop");
        }
    }

    void m2Stop() {
        synchronized (object) {
            System.out.println("m2Stop start");
            if (container.size() != 5) {
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            object.notifyAll();
            System.out.println(container.size());
            System.out.println("m2Stop stop");
        }
    }

    public static void main(String[] args) {
        final PracticeSynObject practiceSynObject = new PracticeSynObject();
        new Thread(new Runnable() {
            @Override
            public void run() {
                practiceSynObject.m2Stop();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                practiceSynObject.m1Add();
            }
        }).start();
    }



    private class Container {
        List<String> list = new ArrayList<String>();

        public void add(String str) {
            list.add(str);
        }

        public int size() {
            return list.size();
        }
    }
}
