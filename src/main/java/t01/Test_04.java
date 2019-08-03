/**
 * synchronized关键字
 * 同步方法 - 同步方法和非同步方法的调用
 * 同步方法只影响锁定同一个锁对象的同步方法。不影响其他线程调用非同步方法，或调用其他锁资源的同步方法。
 */
package t01;

import cn.liuhp.SleepUtils;

public class Test_04 {
	Object o = new Object();
	public synchronized void m1(){ // 重量级的访问操作。这里锁的是this当前对象
		System.out.println("public synchronized void m1() start");
		SleepUtils.sleepSeconds(3);
		System.out.println("public synchronized void m1() end");
	}
	
	public void m2(){
		synchronized(o){
			System.out.println("public void m2() start");
			SleepUtils.sleepSeconds(1);
			System.out.println("public void m2() end");
		}
	}
	
	public void m3(){
		System.out.println("public void m3() start");
		SleepUtils.sleepSeconds(2);
		System.out.println("public void m3() end");
	}
	

	
	public static void main(String[] args) {
		Test_04 t = new Test_04();
		new Thread(() -> t.m1()).start();
		new Thread(() -> t.m2()).start();
		new Thread(() -> t.m3()).start();
	}
	
}
