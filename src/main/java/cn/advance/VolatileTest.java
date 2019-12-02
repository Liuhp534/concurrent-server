package cn.advance;

import cn.liuhp.SleepUtils;

public class VolatileTest {



    public static void main(String[] args) {
        /*while (true) {
            fun1();
        }*/
        //fun2();
    }



    /*
    *
    * 验证volatile
    * 1、在多线程的环境中拿到的数据非volatile，是工作内存中的数据，那么打印a=1
    * 2、在多线程的环境中拿到的数据volatile，是主内存中的数据，那么打印a=100
    * 但是模拟不出来，这样操作不行
    * */
    int a = 1;

    private static void fun1() {
        VolatileTest volatileTest = new VolatileTest();
        volatileTest.a = 100;
        /*new Thread(() -> {
            SleepUtils.sleepMillis(1);
            volatileTest.a = 100;
        }).start();*/
        new Thread(() -> {
            if (volatileTest.a != 100) {
                System.out.println(volatileTest.a);
            }
        }).start();
    }

     boolean flag = Boolean.TRUE;

    /*
    * 1、通过while(flag) + volatile能够达到，可见性效果
    * 2、问题是单while中有代码的时候，加不加volatile，都可以有可见性效果
    * 解释：由于while循环执行空语句，因此导致flag访问频率过高，isRunning不能及时的被写入主存中。
    * 这就是volatile可见性的一个原因，如果使用volatile则修改的值会立即被更新主存中。而增加一条语句System.out.print之后，
    * 访问isRunning具有一定的间隔，主内存就会有时间刷新工作内存中的inRunning的值。
    *
    * */
    private static void fun2() {
        VolatileTest volatileTest = new VolatileTest();
        new Thread(() -> {
            SleepUtils.sleepMillis(100);
            volatileTest.flag = Boolean.FALSE;
            SleepUtils.sleepMillis(1000);
        }).start();
        new Thread(() -> {
            while(volatileTest.flag) {
                //SleepUtils.sleepMillis(10);
                //System.out.println("运行中");
            }
        }).start();
    }

}
