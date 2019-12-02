package cn.advance;

import cn.id.IdWorker;
import cn.liuhp.SleepUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.*;

public class GenerateNumUtils {

    /**
     * 获取指定长度的随机数字字符串
     *
     * @param length
     * @return
     */
    public static String getRandomNumber(int length) {
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < length; i++) {
            buffer.append(random.nextInt(10));
        }
        return buffer.toString();
    }

    /*
     * 生成年金业务号
     * */
    public static synchronized String generateAnnuityNum() {
        Date now = new Date();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyMMddHH");
        return dateformat.format(now) + getRandomNumber(6);
    }


}
