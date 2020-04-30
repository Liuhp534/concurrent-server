package cn.biglist;

import cn.liuhp.SleepUtils;
import cn.threadpool.PracticeThreadPoolExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/*
* , new ThreadPoolExecutor.DiscardPolicy() {
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println("被拒接的任务");
            if (!executor.isShutdown()) {//改变future的状态，让其不会一直阻塞get
                if (r != null && r instanceof FutureTask) {
                    ((FutureTask) r).cancel(true);
                }
            }
        }
    }
* */
public class DealBigList {

    //4-耗时=4587
    //10-耗时=1998
    //50-耗时=417
    //100-耗时=220
    static ExecutorService executorService = new ThreadPoolExecutor(50, 50, 60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r,"bigList-thread");
        }
    });//核心线程数2，最大线程数4，线程多久空闲销毁，工作队列


    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().availableProcessors());
        long start = System.currentTimeMillis();
        try {
            fun1();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("fun1 异常");
        }
        System.out.println("耗时=" + (System.currentTimeMillis() - start));
    }

    /*
     * 如果1和2两个步骤的异常，没有捕获，那么就会导致3不会执行完全
     * 好吧3try/catch也不能执行完，原来是catch的异常没有捕获到，导致的，直接Exception就可以捕获所有的
     * 耗时=40303,pageSize=100,totalRecord=999
     * */
    private static void fun1() {

        int pageSize = 1;
        int totalRecord = 999;
        int pageCount = (totalRecord  +  pageSize  - 1) / pageSize;
        List<Integer> bigList = new ArrayList<>(totalRecord);
        initBigList(bigList, totalRecord);
        int start = 0;
        int end = 0;
        //1、分配任务，这一步异常则不会执行任何任务
        System.out.println("------------------执行第1步------------------");
        List<DealBigListHandler> taskHandlerList = new ArrayList<>(pageCount);
        DealBigListHandler taskHandler = null;
        try {
            for (int i = 1; i <= pageCount; i++) {
                start = (i - 1) * pageSize;
                end = i * pageSize;
                if (end > totalRecord) {
                    end = totalRecord;
                }
                taskHandler = new DealBigListHandler(bigList.subList(start, end));
                taskHandlerList.add(taskHandler);
            }
        } catch (Exception e) {
            System.out.println("创建任务异常");
            return;
        }
        //2、提交任务，如果不捕获or没有捕获到，那么方法不放下执行。这一步异常则会执行已提交的任务
        System.out.println("------------------执行第2步------------------");
        List<Future<Integer>> dealResult = new ArrayList<>();
        try {
            for (DealBigListHandler dealBigListHandler : taskHandlerList) {
                dealResult.add(executorService.submit(dealBigListHandler));
            }
        } catch (Exception e) {
            System.out.println("提交任务异常，提交数量=" + dealResult.size());
        }
        //3、提取执行情况，如果不捕获or没有捕获到，那么方法不放下执行。这一步异常则不能正确获取执行的结果，但是不影响执行
        System.out.println("------------------执行第3步------------------");
        int result = 0;
        for (Future<Integer> integerFuture : dealResult) {
            try {
                result += integerFuture.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("完成情况=" + result);
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
                SleepUtils.sleepMillis(400);//处理100毫秒
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
