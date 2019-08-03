/**
 * synchronized关键字
 * 同步方法 - 多方法调用原子性问题（业务）
 * 同步方法只能保证当前方法的原子性，不能保证多个业务方法之间的互相访问的原子性。
 * 注意在商业开发中，多方法要求结果访问原子操作，需要多个方法都加锁，且锁定统一个资源。
 * 
 * 一般来说，商业项目中，不考虑业务逻辑上的脏读问题。
 */
package t01;

import cn.liuhp.SleepUtils;

import java.util.concurrent.TimeUnit;

public class Test_05 {
	private int d = 10;
	public synchronized void m1(){
		if (d > 0) {
            System.out.println("执行任务");
		    SleepUtils.sleepSeconds(3);//复杂的业务处理
            d = d - 2;//消费
        }
	}
	
	public int m2(){
	    this.d = 0;
		return this.d;
	}


    public int m3(){
        return this.d;
    }

	public static void main(String[] args) {
		test1();
	}

	/*
	* lambda 8可以不用显示的声明final
	* 两个业务方法之间会有脏读的问题，如果两个方法之间没有互斥
	* */
	private static void test1() {
		Test_05 t = new Test_05();
		new Thread(() -> t.m1()).start();

        SleepUtils.sleepSeconds(1);
		System.out.println(t.m2());
		SleepUtils.sleepSeconds(3);
		System.out.println(t.m3());
	}
}
