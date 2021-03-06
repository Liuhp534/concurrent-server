/**
 * AtomicXxx
 * 同步类型
 * 原子操作类型。 其中的每个方法都是原子操作。可以保证线程安全。
 */
package t01;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Test_11 {

	AtomicInteger count = new AtomicInteger(0);

	void m(){
		for(int i = 0; i < 10000; i++){
			/*if(count.get() < 1000)*/
				count.incrementAndGet();
		}
	}
	
	public static void main(String[] args) {

	}

	private static void test1() {
		Test_11 t = new Test_11();
		List<Thread> threads = new ArrayList<Thread>();
		for(int i = 0; i < 10; i++){
			threads.add(new Thread(() -> t.m()));
		}
		for(Thread thread : threads){
			thread.start();
		}
		for(Thread thread : threads){
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(t.count.intValue());
	}
}
