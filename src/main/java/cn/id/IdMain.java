package cn.id;

import cn.hutool.core.lang.Snowflake;
import cn.liuhp.SleepUtils;

import java.text.DecimalFormat;
import java.util.concurrent.*;

public class IdMain {



    private static ConcurrentHashMap<String, String> commonRecord = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        //fun2();
        //fun3();
        //fun4();
        System.out.println("P1911281744442462024".length());
        System.out.println(SnowFlakeUtils.generateId().length());//P1199967449366269952001
    }

    private static void fun4() {
        DecimalFormat format = new DecimalFormat("000");
        for (int i = 0; i < 1010; i ++) {
            System.out.println(format.format(i));
        }
    }

    /*
    * 测试雪花算法产品唯一id
    * */
    private static void fun2() {
        System.out.println("生产id测试");
        long workerId = 1L;
        long datacenterId = 1L;
        Snowflake snowflake = new Snowflake(workerId, datacenterId);
        ExecutorService executorService = new ThreadPoolExecutor(1000, 1000, 10L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());//核心线程数2，最大线程数4，线程多久空闲销毁，工作队列
        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                while(true) {
                    //String value = generateAnnuityNum();
                    String value = SnowFlakeUtils.generateId();
                    if (null != commonRecord.get(value)) {
                        System.out.println("出现相同的=" + value + ", size=" + commonRecord.size());
                    } else {
                        //System.out.println("出现不相同的=" + commonInt);
                        commonRecord.put(value, value);
                    }
                }
            });
        }
    }
    private static void fun3() {
        System.out.println("生产id测试");
        long workerId = 1L;
        long datacenterId = 1L;
        Snowflake snowflake = new Snowflake(workerId, datacenterId);
        ExecutorService executorService = new ThreadPoolExecutor(1000, 1000, 10L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());//核心线程数2，最大线程数4，线程多久空闲销毁，工作队列
        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                while(true) {
                    //String value = generateAnnuityNum();
                    String value = SnowFlakeUtils.generateId1();
                    if (null != commonRecord.get(value)) {
                        System.out.println("出现相同的=" + value + ", size=" + commonRecord.size());
                    } else {
                        //System.out.println("出现不相同的=" + commonInt);
                        commonRecord.put(value, value);
                    }
                }
            });
        }
    }


    private static void fun1() {
        IdWorker worker = new IdWorker(1,1,1);
        ExecutorService executorService = new ThreadPoolExecutor(100, 100, 10L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());//核心线程数2，最大线程数4，线程多久空闲销毁，工作队列
        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                while(true) {
                    SleepUtils.sleepMillis(100);
                    //String value = generateAnnuityNum();
                    String value = worker.nextId() + "";
                    if (null != commonRecord.get(value)) {
                        System.out.println("出现相同的=" + value + ", size=" + commonRecord.size());
                    } else {
                        //System.out.println("出现不相同的=" + commonInt);
                        commonRecord.put(value, value);
                    }
                }
            });
        }
    }
}
