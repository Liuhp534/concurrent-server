/**
 * synchronized关键字
 * 锁对象。synchronized(this)和synchronized方法都是锁当前对象。
 */
package t01;

import cn.liuhp.SleepUtils;

import java.util.concurrent.TimeUnit;

public class Test_01 {
	private int count = 0;
	private Object o = new Object();
	
	public void testSync1(){
		synchronized(o){
			System.out.println(Thread.currentThread().getName() 
					+ " count = " + count++);
		}
	}
	
	public void testSync2(){
		synchronized(this){
			System.out.println(Thread.currentThread().getName() 
					+ " count = " + count++);
		}
	}
	
	public  void testSync3(){
		System.out.println(Thread.currentThread().getName() 
				+ " count = " + count++);
		//SleepUtils.sleepMillis(3000);
	}

	private static int staticCount = 0;

	private static Object staticObj = new Object();

	/*
	*如果是static方法那，因为static方式没有对象只有类，这个时候什么情况研究下（1、方法上；2、通过锁住class对象；3、通过静态的锁对象）
	* */
	public static synchronized void testSync4() {
		//synchronized(staticObj) {
			System.out.println(Thread.currentThread().getName()
					+ " count = " + staticCount++);
			//SleepUtils.sleepMillis(3000);
		//}
	}
	
	public static void main(String[] args) {
		//test1();
		test2();
	}

	/*
	* 静态方法加锁
	* */
	private static void test2() {
		//new Thread(() -> Test_01.testSync4()).start();
		//new Thread(() -> Test_01.testSync4()).start();

		for (int i=0; i < 10000; i ++) {
			new Thread(() -> Test_01.testSync4()).start();
		}
		SleepUtils.sleepMillis(3000);
		System.out.println(Test_01.staticCount);//不加锁的情况下，不能保证原子性操作
	}

	/*
	*
	* */
	private static void test1() {
		final Test_01 t = new Test_01();
		//new Thread(() -> t.testSync3()).start();
		//new Thread(() -> t.testSync3()).start();

		for (int i=0; i < 1000; i ++) {//count 不一定是1000
			new Thread(() -> t.testSync3()).start();
		}
		SleepUtils.sleepMillis(3000);
		System.out.println(t.count);
	}
	
}
