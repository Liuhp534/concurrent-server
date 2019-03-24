/**
 * synchronized关键字
 * 同步方法 - 原子性
 * 加锁的目的： 就是为了保证操作的原子性
 */
package t01;

import java.util.ArrayList;
import java.util.List;

public class Test_03 implements Runnable {

	private int count = 1;
	
	@Override
	public /*synchronized*/ void run() {
		synchronized(this) {
			System.out.println(Thread.currentThread().getName()
					+ " count = " + count++);
		}
	}
	
	public static void main(String[] args) {
		Test_03 t = new Test_03();
		/*Thread t1 = new Thread(t);
		Thread t2 = new Thread(t);
		Thread t3 = new Thread(t);
		Thread t4 = new Thread(t);
		Thread t5 = new Thread(t);
		t2.start();
		t5.start();
		t1.start();
		t3.start();
		t4.start();*/
		List<Thread> threads = new ArrayList<Thread>();
		for(int i = 1; i <= 10000; i++){
			threads.add(new Thread(t, "Thread - " + i));
		}
		for (Thread thread : threads) {
			thread.start();
		}
	}
	
}
