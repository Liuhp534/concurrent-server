package cn.lock;

import cn.threadpool.ThreadPoolExecutorUtils;

import java.util.concurrent.ConcurrentHashMap;

public class CustomLockMain {


    private static Sequence sequence = new Sequence();

    private static ConcurrentHashMap<Integer, Integer> commonRecord = new ConcurrentHashMap<>();

    private static volatile boolean threadFlag = Boolean.TRUE;


    public static void main(String[] args) {
        //fun1();
        fun2();
    }


    private static void fun1() {
        for (int i = 0; i < 5; i ++) {
            ThreadPoolExecutorUtils.executorService.submit(() -> {
                while (threadFlag) {
                    Integer commonInt = sequence.getNext();
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

    private static void fun2() {
        for (int i = 0; i < 5; i ++) {
            ThreadPoolExecutorUtils.executorService.submit(() -> {
                while (threadFlag) {
                    Integer commonInt = sequence.getNext();
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
}
