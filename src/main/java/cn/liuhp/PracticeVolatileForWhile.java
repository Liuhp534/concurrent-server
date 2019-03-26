package cn.liuhp;

/**
 * @description: test
 * @author: liuhp534
 * @create: 2019-03-26 21:59
 */
public class PracticeVolatileForWhile {

    public static void main(String[] args) {
        final PracticeVolatileForWhile practiceVolatileForWhile = new PracticeVolatileForWhile();
        //1、初始值线程先执行
        new Thread(new Runnable() {
            @Override
            public void run() {
                practiceVolatileForWhile.m1();
            }
        }).start();
        //2、设置值线程后执行，然后设置值，最后等m3打印设值后的变量
        SleepUtils.sleepSeconds(1);//等1执行起来
        new Thread(new Runnable() {
            @Override
            public void run() {
                practiceVolatileForWhile.m2();
            }
        }).start();
        //3、等2设置值之后再执行，需要等最起码5秒
        SleepUtils.sleepSeconds(6);
        new Thread(new Runnable() {
            @Override
            public void run() {
                practiceVolatileForWhile.m3();
            }
        }).start();
    }

    volatile boolean flag = true;
    volatile int count = 0;

    void m1() {
        System.out.println("m1 start flag=" + flag + " count=" + count);
        while (flag) {
            //System.out.println("-----------");//也就是说在while循环中做点儿什么事情，和volatile效果是一样的
        }
        System.out.println("m1 while to count flag=" + flag + " count=" + count);
        while (count <= 0) {

        }
        System.out.println("m1 end flag=" + flag + " count=" + count);
    }

    synchronized void m2 () {
        System.out.println("m2 start");
        SleepUtils.sleepSeconds(5);
        this.flag = false;
        this.count = 10;
        SleepUtils.sleepSeconds(5);
        System.out.println("m2 end");
    }

    void m3 () {
        System.out.println("m3 start flag=" + flag + " count=" + count);
    }
}
