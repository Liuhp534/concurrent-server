package cn.lock;

import java.util.concurrent.ConcurrentHashMap;

public class Sequence {

	private MyLock lock = new MyLock();

	private int value;

	private static ConcurrentHashMap<Integer, Integer> commonRecord = new ConcurrentHashMap<>();

	public int getNext() {
		lock.lock();
		value++;
		lock.unlock();
		return value;

	}
	
	public static void main(String[] args) {
		
		Sequence s = new Sequence();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					int value = s.getNext();
					if (null != commonRecord.get(value)) {
						System.out.println("出现相同的=" + value);
					} else {
						//System.out.println("出现不相同的=" + commonInt);
						commonRecord.put(value, value);
					}
				}
			}
		}).start();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					int value = s.getNext();
					if (null != commonRecord.get(value)) {
						System.out.println("出现相同的=" + value);
					} else {
						//System.out.println("出现不相同的=" + commonInt);
						commonRecord.put(value, value);
					}
				}
			}
		}).start();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					int value = s.getNext();
					if (null != commonRecord.get(value)) {
						System.out.println("出现相同的=" + value);
					} else {
						//System.out.println("出现不相同的=" + commonInt);
						commonRecord.put(value, value);
					}
				}
			}
		}).start();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					int value = s.getNext();
					if (null != commonRecord.get(value)) {
						System.out.println("出现相同的=" + value);
					} else {
						//System.out.println("出现不相同的=" + commonInt);
						commonRecord.put(value, value);
					}
				}
			}
		}).start();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					int value = s.getNext();
					if (null != commonRecord.get(value)) {
						System.out.println("出现相同的=" + value);
					} else {
						//System.out.println("出现不相同的=" + commonInt);
						commonRecord.put(value, value);
					}
				}
			}
		}).start();
	}

}
