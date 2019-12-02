package cn.lock;

import cn.threadpool.ThreadPoolExecutorUtils;

import java.util.concurrent.ConcurrentHashMap;

public class Sequence {

	private MyLock lock = new MyLock();

	private int value;

	private static ConcurrentHashMap<Integer, Integer> commonRecord = new ConcurrentHashMap<>();

	/*
	*这样写同样有线程安全问题，返回值不是原子操作，尽管相加是的
	* */
	public int getNext() {
		lock.lock();
		value++;
		lock.unlock();
		return value;
	}


	/*
	 *这样写同样有线程安全问题，返回值不是原子操作，尽管相加是的
	 * */
	public int getNextNoSafe() {
		value++;
		return value;
	}

	public static void main(String[] args) {
		

	}

}
