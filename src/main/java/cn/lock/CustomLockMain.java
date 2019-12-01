package cn.lock;

import cn.threadpool.ThreadPoolExecutorUtils;

import java.util.concurrent.ConcurrentHashMap;

public class CustomLockMain {

    private static ConcurrentHashMap<Integer, Integer> commonRecord = new ConcurrentHashMap<>();

    private static volatile boolean threadFlag = Boolean.TRUE;

    public static void main(String[] args) {
        fun1();
        //fun2();
    }


    private static void fun1() {
        Sequence sequence = new Sequence();
        CustomLock lock = new CustomLock();
        for (int i = 0; i < 500; i ++) {
            ThreadPoolExecutorUtils.executorService.submit(() -> {
                System.out.println(Thread.currentThread().getName());
                while (threadFlag) {
                    lock.lock();
                    Integer commonInt = sequence.getNext();
                    lock.unlock();
                    if (null != commonRecord.get(commonInt)) {
                        System.out.println("出现相同的=" + commonInt);
                        threadFlag = Boolean.FALSE;
                    } else {
                        //System.out.println("出现不相同的=" + commonInt);
                        commonRecord.put(commonInt, commonInt);
                    }
                }
            });
        }
    }
    /*
    * 会有线程安全问题
    * */
    private static void fun2() {
        Sequence s = new Sequence();

        for (int i = 0; i < 500; i ++) {
            ThreadPoolExecutorUtils.executorService.submit(() -> {
                while(true) {
                    int value = s.getNext();
                    if (null != commonRecord.get(value)) {
                        System.out.println("出现相同的=" + value);
                    } else {
                        //System.out.println("出现不相同的=" + commonInt);
                        commonRecord.put(value, value);
                    }
                }
            });
        }
    }
}
