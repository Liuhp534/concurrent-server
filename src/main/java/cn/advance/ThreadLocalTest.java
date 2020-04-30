package cn.advance;

public class ThreadLocalTest {


    static ThreadLocal<String> threadLocal = new ThreadLocal<>();


    public static void main(String[] args) {
        fun1();
    }


    private static void fun1() {
        threadLocal.set("1");

    }

}
