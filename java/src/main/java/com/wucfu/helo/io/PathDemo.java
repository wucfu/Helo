package com.wucfu.helo.io;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author wuchangfu
 * @description
 * @date 2020-06-28 10:52
 */
public class PathDemo {
    public static void main(String[] args) throws Exception {
        // java8一行代码读取文件
        Files.lines(Paths.get("D:\\jd.txt"), StandardCharsets.UTF_8).forEach(System.out::println);
    }

    public void readPath() throws Exception {
        System.out.println("*****************直接路径*****************");

        File f1 = new File("helo");
        System.out.println("'helo'的绝对路径：" + f1.getAbsoluteFile() + " ,文件是否存在：" + f1.exists());

        File f2 = new File("/helo");
        System.out.println("'/helo'的绝对路径：" + f2.getAbsoluteFile() + " ,文件是否存在：" + f2.exists());

        File f3 = new File("/helo");
        System.out.println("'./helo'的绝对路径：" + f3.getAbsoluteFile() + " ,文件是否存在：" + f3.exists());

        System.out.println("***********根据类加载器获取路径************");
        ClassLoader cl = PathDemo.class.getClassLoader();
        URL url = cl.getResource("helo");
        File f4 = new File(url.getPath());
        System.out.println("'helo'的路径：" + url.getPath() + " ,文件是否存在：" + f4.exists());

        System.out.println("**********遍历指定目录下的所有文件***********");
        File temp = new File(PathDemo.class.getClassLoader().getResource("temp").getPath());
        listFiles(temp);

    }

    public void showURL() throws IOException {

        // 第一种：获取类加载的根路径
        File f = new File(this.getClass().getResource("/").getPath());
        System.out.println(f);

        // 获取当前类的所在工程路径; 如果不加“/”  获取当前类的加载目录
        File f2 = new File(this.getClass().getResource("").getPath());
        System.out.println(f2);

        // 第二种：获取项目路径
        File directory = new File("");// 参数为空
        String courseFile = directory.getCanonicalPath();
        System.out.println(courseFile);


        // 第三种
        URL xmlpath = this.getClass().getClassLoader().getResource("");
        System.out.println(xmlpath);


        // 第四种
        System.out.println(System.getProperty("user.dir"));
        /*
         * 获取当前工程路径
         */

        // 第五种：  获取所有的类路径 包括jar包的路径
        System.out.println(System.getProperty("java.class.path"));

    }

    public static void listFiles(File file) throws IOException {

        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            System.out.println("路径：" + file.getParent() + ",文件：" + file.getName());
        }

        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                listFiles(f);
            }
        }
    }
}
