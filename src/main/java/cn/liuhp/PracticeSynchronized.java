package cn.liuhp;

/**
 * @description: 锁对象和锁方法效果一样的
 * @author: liuhp534
 * @create: 2019-03-24 14:24
 */
public class PracticeSynchronized {

    private int count = 0;

    public void m1() {
        synchronized (this) {//锁住当前对象，那么该对象的其他属性应该不能访问，可以访问的 效果和锁下面的方法是一样的
            count ++;
            System.out.println(Thread.currentThread().getName() + " count start " + count);
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " count end " + count);
        }
    }

    public void m2() {
        System.out.println(Thread.currentThread().getName() + " count " + count);
    }

    public static void main(String[] args) {
        final PracticeSynchronized test1 = new PracticeSynchronized();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                test1.m1();
            }
        });

/*        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                test1.m2();
            }
        });
        t1.start();
        t2.start();

    }

}
