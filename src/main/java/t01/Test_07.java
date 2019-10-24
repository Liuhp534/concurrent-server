/**
 * synchronized关键字
 * 同步方法 - 继承
 * 子类同步方法覆盖父类同步方法。可以指定调用父类的同步方法。
 * 相当于锁的重入。
 */
package t01;

import java.util.concurrent.TimeUnit;

public class Test_07 {

	synchronized void m1(){ // 锁this
		System.out.println(Thread.currentThread().getName() + " m1 start");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		m2();
		System.out.println(Thread.currentThread().getName() + " m1 end");
	}
	synchronized void m2(){ // 锁this
		System.out.println(Thread.currentThread().getName() + " m2 start");
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " m2 end");
	}

	/*
	 * synchronised 锁可以冲入
	 * */
	private static void test1() {
		Test_07 test = new Test_07();
		new Thread(() -> test.m2()).start();

		new Thread(() -> test.m1()).start();
	}
	
	synchronized void m(){
		System.out.println("Super Class m start");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Super Class m end");
	}

	private static void test2() {
		new Sub_Test_07().m();
	}
	
	public static void main(String[] args) {
		//test1();
		test2();
	}
	
}

class Sub_Test_07 extends Test_07{
	synchronized void m(){
		System.out.println("Sub Class m start");
		super.m();
		System.out.println("Sub Class m end");
	}
}
