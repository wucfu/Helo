package com.wucfu.helo.string;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class StringDemo {
    public static void main(String[] args) throws Exception {

        // String 的不可变性
        // changeStringValue();

        // UUID
        // System.out.println(UUID.randomUUID().toString());

        // 核心数
        // System.out.println(Runtime.getRuntime().availableProcessors());
        //
//        System.out.println(10 >> 8);
        System.out.println("123456".substring(0,6));

        LocalDate startDate = LocalDate.parse("2021-08-01");
        LocalDate endDate = LocalDate.parse("2021-08-11");
        System.out.println(startDate.until(endDate, ChronoUnit.DAYS));
    }


    /**
     * String的不可变性
     */
    public static void changeStringValue() throws Exception {
        /** 通过反射修改 **/
        String s = "Hello World";
        System.out.println("---------------------------");
        System.out.println("modify string value by reflection >>> ");

        // 输出字符串内容和哈希值
        System.out.println("s = " + s + " ,hash = [" + s.hashCode() + "]");
        // 获取String类中的value字段
        Field valueFieldOfString = String.class.getDeclaredField("value");
        // 改变value属性的访问权限
        valueFieldOfString.setAccessible(true);
        // 获取s对象上的value属性的值
        char[] value = (char[]) valueFieldOfString.get(s);
        // 改变value所引用的数组中的第5个字符
        value[5] = '_';
        // 输出字符串内容和哈希值
        System.out.println("after reflection modify: ");
        System.out.println("s = " + s + " ,hash = [" + s.hashCode() + "]");

        /** 直接赋值修改 **/
        System.out.println("---------------------------");
        System.out.println("direct modify >>> ");
        String s2 = "Hello World";
        System.out.println("s = " + s2 + " ,hash = [" + s.hashCode() + "]");
        s2 = "Halo";
        System.out.println("s = " + s2 + " ,hash = [" + s.hashCode() + "]");

        /** 字符串常量 **/
        System.out.println("---------------------------");
        String a = "abc";
        String b = "abc";
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        System.out.println("a == b ? " + (a == b));
        System.out.println("a -> hashcode = " + a.hashCode());
        System.out.println("b -> hashcode = " + b.hashCode());

        Field valueFieldOfA = String.class.getDeclaredField("value");
        valueFieldOfA.setAccessible(true);
        char[] valuesOfA = (char[]) valueFieldOfString.get(a);
        valuesOfA[1] = 'X';
        System.out.println("modify value of {a} by reflection: ");
        System.out.println("a = " + a);
        System.out.println("b = " + b);
    }


}
