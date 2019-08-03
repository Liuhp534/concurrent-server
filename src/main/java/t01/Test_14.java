/**
 * synchronized关键字
 * 常量问题
 * 在定义同步代码块时，不要使用常量对象作为锁对象。
 * https://blog.csdn.net/qq_28240551/article/details/81194981
 */
package t01;

public class Test_14 {
	String s1 = "hello";//不要使用字符串常量作为对象锁去同步代码，不同包不同类中的值相同的字符串常量引用的是同一个字符串对象，其他地方引用了，可能会造成死锁问题
	String s2 = new String("hello"); // new关键字，一定是在堆中创建一个新的对象。
	Integer i1 = 111;
	Integer i2 = 111;
	void m1(){
		synchronized (i1) {
			System.out.println("m1()");
			while(true){
				
			}
		}
	}
	
	void m2(){
		synchronized (i2) {
			System.out.println("m2()");
			while(true){
				
			}
		}
	}
	
	public static void main(String[] args) {
		test1();
	}

	private static void test1() {
		Test_14 t = new Test_14();
		new Thread(() -> t.m1()).start();
		new Thread(() -> t.m2()).start();
	}
	
}
