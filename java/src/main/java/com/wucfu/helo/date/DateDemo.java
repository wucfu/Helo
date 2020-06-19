package com.wucfu.helo.date;

import java.util.Calendar;

public class DateDemo {
    public static void main(String[] args) {
        Integer hour = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        System.out.println(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
        System.out.println(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        // 获取周一
        Calendar today = Calendar.getInstance();
        today.add(Calendar.DAY_OF_WEEK, -(Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 2));
        System.out.println(today.getTime());
        // 获取1号
        today = Calendar.getInstance();
        today.add(Calendar.DAY_OF_MONTH, -(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 1));
        System.out.println(today.getTime());


    }
}
