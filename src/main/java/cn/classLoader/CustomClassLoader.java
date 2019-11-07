package cn.classLoader;

import cn.liuhp.SleepUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/*
* 自定义类加载器
* */
public class CustomClassLoader extends ClassLoader {




    /*
    * 1、将TestClassLoaderConstant.class 作为文件加载到内存中；
    * 2、通过类名和文件生成class对象
    * 3、操作class对象
    * 4、不同的加载器能加载相同的class文件
    * */
    public static void fun1() {
        String clazzPath = "D:\\idea-git-workspace\\custome-git-idea-workspace\\concurrent-server\\target\\classes\\";
        clazzPath += "cn\\classLoader\\";
        String className = "cn.classLoader.TestClassLoaderConstant";
        CustomClassLoader customClassLoader = new CustomClassLoader(clazzPath, className);
        Class clazz = customClassLoader.findClass("TestClassLoaderConstant.class");
        //Class clazz1 = customClassLoader.findClass("TestClassLoaderConstant.class");//java.lang.LinkageError
        System.out.println(clazz);
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                try {
                    System.out.println(field.getInt(clazz));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String classPath;

    private String className;

    public CustomClassLoader (String classPath, String className) {
        this.classPath = classPath;
        this.className = className;
    }



    @Override
    protected Class<?> findClass(String clazzName)  {
        byte[] classByte = getData(clazzName);
        if (null != classByte) {
            return defineClass(className, classByte, 0, classByte.length);
        }
        return null;
    }

    /*
    * 获取文件的字节数据
    * */
    private byte[] getData(String className) {
        String path = classPath + className;
        try {
            System.out.println(path);
            InputStream input = new FileInputStream(path);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int local = 0;
            byte[] tempBuffer = new byte[1024];//读取数据的缓存
            while ((local = input.read(tempBuffer)) != -1) {//是否能读到数据，并记录位置大小
                out.write(tempBuffer, 0, local);//将缓存放到输出流中
            }
            return out.toByteArray();//输出流转换为字节数组
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
