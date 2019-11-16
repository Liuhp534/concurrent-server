package cn.biglist;

import cn.liuhp.SleepUtils;
import cn.threadpool.PracticeThreadPoolExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class DealBigList {

    //4-耗时=4587
    //10-耗时=1998
    //50-耗时=417
    //100-耗时=220
    static ExecutorService executorService = new ThreadPoolExecutor(200, 200, 60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>());//核心线程数2，最大线程数4，线程多久空闲销毁，工作队列


    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().availableProcessors());
        long start = System.currentTimeMillis();
        fun1();
        System.out.println("耗时=" + (System.currentTimeMillis() - start));
    }


    private static void fun1() {
        List<Future<Integer>> dealResult = new ArrayList<>();
        int pageSize = 100;
        int totalRecord = 100000;
        int pageCount = (totalRecord  +  pageSize  - 1) / pageSize;
        List<Integer> bigList = new ArrayList<>(totalRecord);
        initBigList(bigList, totalRecord);
        int start = 0;
        int end = 0;
        for (int i = 1; i <= pageCount; i ++) {
            start = (i - 1) * pageSize;
            end = i * pageSize;
            /*if (end > totalRecord) {
                end = totalRecord - 1;
            }*/
            DealBigListHandler handler = new DealBigListHandler(bigList.subList(start, end));
            try {
                dealResult.add(executorService.submit(handler));
            } catch (Exception e) {
                System.out.println("异常");
            }

        }

        //打印执行情况
        int result = 0;
        for (Future<Integer> integerFuture : dealResult) {
            try {
                result += integerFuture.get();
                System.out.println("result : " + result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        System.out.println(result);
        /*try {
            int result = 0;
            for (Future<Integer> integerFuture : dealResult) {
                result += integerFuture.get();
                System.out.println(result);
            }
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/
        System.out.println("over");
    }

    private static void initBigList(List<Integer> bigList, int size) {
        for (int i=0; i < size; i++) {
            bigList.add(i);
        }
    }

    /*
     * 任务被线程池拒绝处理器
     * */
    private static class ThreadPoolRejectHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println("被拒接的任务");
            if (!executor.isShutdown()) {//改变future的状态，让其不会一直阻塞get
                if (r != null && r instanceof FutureTask) {
                    ((FutureTask) r).cancel(true);
                }
            }
        }
    }
}

/*
 * 处理任务类
 * */
class DealBigListHandler implements Callable<Integer> {

    private List<Integer> taskList;//任务列表

    public DealBigListHandler(List<Integer> taskList) {
        this.taskList = taskList;
    }

    @Override
    public Integer call() throws Exception {
        int result = 0;
        System.out.println(Thread.currentThread().getName() + "执行中，size=" + this.taskList.size());
        if (null != this.taskList) {
            for (Integer temp : taskList) {
                SleepUtils.sleepMillis(1);//处理100毫秒
                /*if (temp == 500) {
                    throw new RuntimeException("异常了");
                }*/
            }
            result = this.taskList.size();
        }
        System.out.println(Thread.currentThread().getName() + "执行完成，size=" + this.taskList.size());
        return result;
    }
}
