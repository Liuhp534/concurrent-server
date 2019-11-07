package cn.classLoader;

import cn.liuhp.SleepUtils;

public class ClassLoaderTest {


    public static void main(String[] args) {
        //fun1();
        //fun2();
        //fun3();
        //fun4();
        fun5();
    }


    /*
     * Extension ClassLoader 加载的路径
     * */
    private static void fun1() {
        System.out.println(System.getProperty("java.ext.dirs"));
    }

    /*
     * System ClassLoader 加载的路径
     * */
    private static void fun2() {
        System.out.println(System.getProperty("java.class.path"));
    }

    /*
     * 显示加载class对象
     * */
    private static void fun3() {
        try {
            ClassLoaderTest classLoaderTest = new ClassLoaderTest();
            System.out.println("parent classloader=" + classLoaderTest.getClass().getClassLoader().getParent());
            System.out.println("this classloader=" + classLoaderTest.getClass().getClassLoader());
            Class sleepUtils = classLoaderTest.getClass().getClassLoader().loadClass("cn.liuhp.SleepUtils");
            System.out.println(sleepUtils.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*
     * 获取当前类的classpathc
     * file:/D:/idea-git-workspace/custome-git-idea-workspace/concurrent-server/target/classes/
     * */
    private static void fun4() {
        ClassLoaderTest classLoaderTest = new ClassLoaderTest();
        System.out.println(classLoaderTest.getClass().getClassLoader().getResource("").toString());
    }

    /*
    * 自定义类加载器，加载class文件
    * */
    private static void fun5() {
        CustomClassLoader.fun1();
        SleepUtils.sleepSeconds(10);
        CustomClassLoader.fun1();
    }
}
