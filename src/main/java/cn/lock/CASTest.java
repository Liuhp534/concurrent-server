package cn.lock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @description: cas测试
 * @author: liuhp534
 * @create: 2020-02-15 19:49
 */
public class CASTest {

    public static void main(String[] args) {
        AtomicInteger ai = new AtomicInteger();
        System.out.println(ai.incrementAndGet());
        boolean iflag = ai.compareAndSet(0, 1);
        System.out.println(iflag);

        AtomicReference<Boolean> ato = new AtomicReference<>(true);

        boolean flag = ato.compareAndSet(false, true);

        System.out.println(flag);
    }
}
