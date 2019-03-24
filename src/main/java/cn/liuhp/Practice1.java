package cn.liuhp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @description: 小练习
 * @author: liuhp534
 * @create: 2019-03-24 16:46
 */
public class Practice1 {

    List<String> list = new ArrayList<String>();//只要没有volatile 的确不能共享变量，但是奇怪的是stopFlag加了也会影响其他的,前提对应的方法使用了，也就是
    //说一个方法只要使用了volatile的变量，会是其他没有使用的也有效果

    Container container = new Container();

    volatile boolean stopFlag = Boolean.TRUE;

    void m1Add() {
        System.out.println("m1Add start");
        try {
            for (int i = 0; i < 10; i++) {
                TimeUnit.SECONDS.sleep(1);
                container.add(i + "");
                list.add(i + "");
                System.out.println("add " + i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("m1Add stop");
    }

    void m2Stop() {
        System.out.println("m2Stop start");
        while (true) {
            if (list.size() >= 5) {
                //stopFlag = Boolean.FALSE;
                System.out.println(container.size());
                break;
            }
        }
        System.out.println("m2Stop stop");
    }

    public static void main(String[] args) {
        final Practice1 practice1 = new Practice1();
        new Thread(new Runnable() {
            @Override
            public void run() {
                practice1.m2Stop();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                practice1.m1Add();
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

