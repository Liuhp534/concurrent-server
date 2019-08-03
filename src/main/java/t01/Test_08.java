/**
 * synchronized关键字
 * 同步方法 - 锁与异常
 * 当同步方法中发生异常的时候，自动释放锁资源。不会影响其他线程的执行。
 * 注意，同步业务逻辑中，如果发生异常如何处理。
 */
package t01;

import cn.liuhp.SleepUtils;

public class Test_08 {
	int i = 0;
	synchronized void m(){
		System.out.println(Thread.currentThread().getName() + " - start");
		while(true){
			i++;
			System.out.println(Thread.currentThread().getName() + " - " + i);
			SleepUtils.sleepSeconds(2);
			if(i == 5){
				i = 1/0;
			}
		}
	}
	
	public static void main(String[] args) {
		test1();
	}

	private static void test1() {
		Test_08 t = new Test_08();
		new Thread(() -> t.m(), "t1").start();
		new Thread(() -> t.m(), "t2").start();
	}
	
}
