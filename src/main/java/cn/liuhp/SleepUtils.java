package cn.liuhp;

import java.util.concurrent.TimeUnit;

/**
 * @description: 睡眠工具
 * @author: liuhp534
 * @create: 2019-03-24 21:49
 */
public class SleepUtils {

    public static void sleepSeconds(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (Exception e) {
            System.out.println("睡眠异常");
            e.printStackTrace();
        }
    }
}
