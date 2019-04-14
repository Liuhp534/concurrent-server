/**
 * 普通容器
 */
package t07;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

public class Test_01 {
	
	//static List<String> list = new ArrayList<String>();
	static List<String> list = new Vector<>();
	static final ReentrantLock lock = new ReentrantLock();
	static final  Object obj = new Object();
	static final CountDownLatch latch = new CountDownLatch(10);
	
	static{
		for(int i = 0; i < 10000; i++){
			list.add("String " + i);
		}
	}
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		for(int i = 0; i < 10; i++){
			new Thread(new Runnable() {
				@Override
				public void run() {
					while(list.size() > 0){
						System.out.println(Thread.currentThread().getName() + " - " + list.remove(0));
					}
					latch.countDown();
				}
			}, "Thread" + i).start();
		}
		try {
			latch.await();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(System.currentTimeMillis() - start);
		/*start = System.currentTimeMillis();
		for(int i = 0; i < 10; i++){
			new Thread(new Runnable() {
				@Override
				public void run() {
					while(list.size() > 0){
						synchronized (obj) {//多个线程同一个资源，只要拿到资源的线程才能够运行
							if (list.size() > 0) {//由于其他线程不能操作list，所以这个时候size是稳定的
								System.out.println(Thread.currentThread().getName() + " - " + list.remove(0));
							}
						}
					}
					latch.countDown();
				}
			}, "Thread" + i).start();
		}*/

		/*for(int i = 0; i < 10; i++){
			new Thread(new Runnable() {
				@Override
				public void run() {
					while(list.size() > 0){
						lock.lock();
						try {
							if (list.size() > 0) {
								System.out.println(Thread.currentThread().getName() + " - " + list.remove(0));
							}
						} finally {
							lock.unlock();
						}
					}
				}
			}, "Thread" + i).start();
		}*/
		
		/*for(int i = 0; i < 10; i++){
			new Thread(new Runnable() {
				@Override
				public void run() {
					while(true){
						synchronized (list) {
							if(list.size() <= 0){
								break;
							}
							System.out.println(Thread.currentThread().getName() + " - " + list.remove(0));
						}
					}
				}
			}, "Thread" + i).start();
		}*/
	}

}
