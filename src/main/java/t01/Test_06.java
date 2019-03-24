/**
 * synchronized关键字
 * 同步方法 - 调用其他同步方法
 * 锁可重入。 同一个线程，多次调用同步代码，锁定同一个锁对象，可重入。
 */
package t01;

import java.util.concurrent.TimeUnit;

public class Test_06 {
	
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
	
	public static void main(String[] args) {
		
		//new Test_06().m1();
		final Test_06 test = new Test_06();
		new Thread(new Runnable() {
			@Override
			public void run() {
				test.m2();
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				test.m1();
			}
		}).start();
		
	}
	
}
