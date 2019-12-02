package cn.id;

import cn.hutool.core.lang.Snowflake;

/*
* 雪花算法工具
* */
public class SnowFlakeUtils {


    private static final Snowflake snowflake = new Snowflake(1L, 1L);


    private static final Snowflake snowflake1 = new Snowflake(2L, 1L);
    /*
    * 生成唯一id
    * */
    public  static String generateId() {
        return snowflake.nextIdStr();
    }

    /*
     * 生成唯一id
     * */
    public  static String generateId1() {
        return snowflake1.nextIdStr();
    }
}
