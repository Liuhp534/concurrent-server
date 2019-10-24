/**
 * synchronized关键字
 * 锁对象变更问题
 * 同步代码一旦加锁后，那么会有一个临时的锁引用执行锁对象，和真实的引用无直接关联。
 * 在锁未释放之前，修改锁对象引用，不会影响同步代码的执行。
 * 不要在线程中修改对象锁的引用，引用被改变会导致锁失效。（内存地址会发生改变）。
 * 在线程中修改了锁对象的属性，而不修改引用则不会引起锁失效，不会产生线程安全的问题。（可以set属性，不可以new）。
 */
package t01;

import cn.liuhp.SleepUtils;

public class Test_13 {
	Dog o = new Dog("1");

	int i = 0;
	int a(){
		try{
			/*
			 * return i ->
			 * int _returnValue = i; // 0;
			 * return _returnValue;
			 */
			return i;
		}finally{
			i = 10;
		}
	}
	
	void m(){
		System.out.println(Thread.currentThread().getName() + " start");
		synchronized (o) {
			while(true){
				SleepUtils.sleepMillis(2000);
				System.out.println(Thread.currentThread().getName() + " - " + o);
			}
		}
	}

	void m1(){
		System.out.println(Thread.currentThread().getName() + " start");
		synchronized (o) {
			while(true){
				SleepUtils.sleepMillis(100);
				System.out.println(Thread.currentThread().getName() + " - " + o);
			}
		}
	}
	
	public static void main(String[] args) {
		test1();
	}

	/*
	* 这就很奇怪了
	* */
	private static void test1() {
		Test_13 t = new Test_13();
		new Thread(() -> t.m(), "thread1").start();
		SleepUtils.sleepSeconds(3);
		Thread thread2 = new Thread(() -> t.m1(), "thread2");
		t.o = new Dog("2");
		thread2.start();
	}

	/*
	* 先return，然后赋值。
	* */
	private static void test2() {
		Test_13 t = new Test_13();
		System.out.println(t.i);
		System.out.println(t.a());
		System.out.println(t.i);
	}
	
}

class Dog {
	private String name;
	Dog (String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "name=" + this.name;
	}
}