/**
 * 并发容器 - LinkedBlockingQueue
 *  阻塞容器。
 *  put & take - 自动阻塞。
 *  put自动阻塞， 队列容量满后，自动阻塞
 *  take自动阻塞方法， 队列容量为0后，自动阻塞。
 */
package t06;

import cn.liuhp.SleepUtils;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Test_04_LinkedBlockingQueue {
	
	final BlockingQueue<String> queue = new LinkedBlockingQueue<String>(2);
	final Random r = new Random();
	
	public static void main(String[] args) {
		final Test_04_LinkedBlockingQueue t = new Test_04_LinkedBlockingQueue();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					try {
						t.queue.put("value"+t.r.nextInt(1000));
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					t.queue.offer("");
					try {
						t.queue.offer("", 300, TimeUnit.MILLISECONDS);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(t.queue);
					t.queue.add("");

				}
			}
		}, "producer").start();
		
		/*for(int i = 0; i < 3; i++){
			new Thread(new Runnable() {
				@Override
				public void run() {
					while(true){
						*//*System.out.println(Thread.currentThread().getName() +
								" - " + t.queue.take());*//*
						//SleepUtils.sleepMillis(300);
						try {
							System.out.println(Thread.currentThread().getName() +
									" - " + t.queue.poll(300, TimeUnit.MILLISECONDS));
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}, "consumer"+i).start();
		}*/
	}

}
