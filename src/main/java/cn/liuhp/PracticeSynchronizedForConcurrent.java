package cn.liuhp;

/**
 * @description: test
 * @author: liuhp534
 * @create: 2019-03-26 21:59
 */
public class PracticeSynchronizedForConcurrent {

    public static void main(String[] args) {
        final PracticeSynchronizedForConcurrent test = new PracticeSynchronizedForConcurrent();
        //1、设置值线程后执行，然后设置值，最后等m3打印设值后的变量
        SleepUtils.sleepSeconds(1);//等1执行起来
        new Thread(new Runnable() {
            @Override
            public void run() {
                test.m2();
            }
        }).start();
        //2、等1设置值之后再执行，需要等最起码5秒
        SleepUtils.sleepSeconds(6);
        new Thread(new Runnable() {
            @Override
            public void run() {
                test.m3();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                test.m4();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                test.m5();
            }
        }).start();
    }

    volatile boolean flag = true;
    volatile int count = 0;

    synchronized void m2 () {
        System.out.println("m2 start");
        SleepUtils.sleepSeconds(5);
        this.flag = false;
        this.count = 10;
        SleepUtils.sleepSeconds(5);
        System.out.println("m2 end");
    }

    /**
    * @Description:
    * 如果两个方法都加了synchronize，执行只能先m2后m3，说明存在资源占用，占用的资源就是当前对象
    * @param
    * @return
    * @throws
    * @author Liuhp534
    * @date 2019/3/26 22:21
    */
    synchronized void m3 () {
        System.out.println("m3 start flag=" + flag + " count=" + count);
    }

    Object lock = new Object();
    /**
     * @Description:
     * 如果两个方法都加了synchronize，执行只能先m2后m3，说明存在资源占用，占用的资源就是当前对象，如果synchronize的是其他资源
     * 那么不会出现同步
     * @param
     * @return
     * @throws
     * @author Liuhp534
     * @date 2019/3/26 22:21
     */
    void m4 () {
        synchronized (lock) {
            System.out.println("m4 start flag=" + flag + " count=" + count);
        }
    }
    /**
     * @Description:
     * 如果两个方法都加了synchronize，执行只能先m2后m3，说明存在资源占用，占用的资源就是当前对象，如果没有synchronize
     * 那么不会出现同步
     * @param
     * @return
     * @throws
     * @author Liuhp534
     * @date 2019/3/26 22:21
     */
    void m5 () {
        System.out.println("m5 start flag=" + flag + " count=" + count);
    }
}
