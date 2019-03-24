/**
 * volatile关键字
 * volatile的可见性
 * 通知OS操作系统底层，在CPU计算过程中，都要检查内存中数据的有效性。保证最新的内存数据被使用。
 * 
 */
package t01;

import java.util.concurrent.TimeUnit;

public class Test_09 {
	
	boolean b = true;
	volatile CustomeBoolean customeBoolean = new CustomeBoolean();
	
	void m(){
		System.out.println("start");
		while(customeBoolean.b){}
		System.out.println("end");
	}
	
	public static void main(String[] args) {
		final Test_09 t = new Test_09();
		new Thread(new Runnable() {
			@Override
			public void run() {
				t.m();
			}
		}).start();
		
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		t.customeBoolean.b = Boolean.FALSE;
		t.b = false;
	}
	class CustomeBoolean {
		boolean b = true;
	}
	
}
