/**
 * 可打断
 * 
 * 阻塞状态： 包括普通阻塞，等待队列，锁池队列。
 * 普通阻塞： sleep(10000)， 可以被打断。调用thread.interrupt()方法，可以打断阻塞状态，抛出异常。
 * 等待队列： wait()方法被调用，也是一种阻塞状态，只能由notify唤醒。无法打断
 * 锁池队列： 无法获取锁标记。不是所有的锁池队列都可被打断。
 *  使用ReentrantLock的lock方法，获取锁标记的时候，如果需要阻塞等待锁标记，无法被打断。
 *  使用ReentrantLock的lockInterruptibly方法，获取锁标记的时候，如果需要阻塞等待，可以被打断。
 * 
 */
package cn.lock;

import cn.liuhp.SleepUtils;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockInterruptTest {
	Lock lock = new ReentrantLock();

	public static void main(String[] args) {

	}

	/*
	* 这个获取到锁之后的中断，那是睡眠的中断
	* */
	private static void fun1() {
		Thread t3 = new Thread(() -> {
			new LockInterruptTest().m3();
		});
		t3.start();
		t3.interrupt();
	}

	/*
		这个获取到锁之后的中断，那是睡眠的中断
	* 中断只会执行一次，由于异常已经捕获，所以代码正常执行
	* */
	private static void fun2() {
		LockInterruptTest lockInterruptTest = new LockInterruptTest();
		Thread t1 = new Thread(() -> {
			lockInterruptTest.m1();
		});
		t1.start();
		SleepUtils.sleepSeconds(1);
		t1.interrupt();//中断只会执行一次
	}

	/*
	* 这个是获取锁的时候中断，通过两种实现
	*
	* */
	private static void fun3() {
		LockInterruptTest t = new LockInterruptTest();
		Thread t1 = new Thread(() -> {
			t.m1();
		});
		t1.start();
		SleepUtils.sleepSeconds(1);//等待t1先执行
		Thread t2 = new Thread(() -> {
			t.m2();
		});
		t2.start();
		SleepUtils.sleepSeconds(1);
		t2.interrupt();// 打断线程休眠。非正常结束阻塞状态的线程，都会抛出异常。
	}
	
	void m1(){
		try{
			lock.lock();
			for(int i = 0; i < 5; i++){
				SleepUtils.sleepSeconds(5);
				System.out.println("m1() method " + i);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
	}
	
	void m2(){
		System.out.println("m2获取锁中。。。。");
		/*//中断之一，有时间阻塞的方式
		try {
			if (!lock.tryLock(3, TimeUnit.SECONDS)) {
				return;
			}
		} catch (InterruptedException e) {
			System.out.println("tryLock 3s获取失败");
			e.printStackTrace();
			return;
		}*/
		//中断之二，打断的方式
		/*try {
			lock.lockInterruptibly(); // 可尝试打断，阻塞等待锁。可以被其他的线程打断阻塞状态
		} catch (InterruptedException e) {
			System.out.println("Interruptibly获取失败");
			e.printStackTrace();
			return;
		}*/
		lock.lock();//正常模式，不能中断
		try{
			System.out.println("m2() method");
		}catch(Exception e){
			System.out.println("m2() method interrupted");
		}finally{
			lock.unlock();
		}
	}

	private  void m3() {
		synchronized (this) {
			SleepUtils.sleepSeconds(5);
			System.out.println("中断之后的代码");//异常捕获了，可以执行
		}
	}
	

}
