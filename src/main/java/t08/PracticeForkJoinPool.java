package t08;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @description: 练习分工线程池
 * @author: liuhp534
 * @create: 2019-04-14 11:33
 */
public class PracticeForkJoinPool {

    private static  final Integer[] adds = new Integer[1000000];

    static {
        for (int i = 0; i < 1000000; i ++) {
            adds[i] = new Random().nextInt(1000);
        }
    }

    public static void main(String[] args) {
        //System.out.println(1/2);
        long temp = 0L;
        for (int i = 0; i < adds.length; i ++) {
            temp += adds[i];
        }
        System.out.println(temp);
        m1();
    }

    private static final void m1() {
        ForkJoinPool pool = new ForkJoinPool();
        BranchTask branchTask = new BranchTask(0, adds.length, adds);
        ForkJoinTask<Long> result = pool.submit(branchTask);
        try {
            System.out.println(result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
class BranchTask extends RecursiveTask<Long> {

    private int start;

    private int end;

    private int Max_Value = 50000;

    private Integer[] adds = null;

    public BranchTask(int start, int end, Integer[] adds) {
        this.start = start;
        this.end = end;
        this.adds = adds;
    }

    @Override
    protected Long compute() {
        if ((end - start) <= this.Max_Value) {
            long temp = 0L;
            for (int i = start; i < end; i ++) {
                temp += adds[i];
            }
            return  temp;
        } else {
            int middle = start + (end - start)/2;
            BranchTask b1 = new BranchTask(start, middle, this.adds);
            BranchTask b2 = new BranchTask(middle, end, this.adds);
            b1.fork();
            b2.fork();

            return b1.join() + b2.join();
        }
    }
}
