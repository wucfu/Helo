package com.wucfu.helo.collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wuchangfu on 2019/5/7.
 */
public class CollectionDemo {
    public static void main(String[] args) throws InterruptedException {
        Map<String, String> map = new ConcurrentHashMap<String, String>();
        String a = map.remove("a");
        System.out.println(a);
        List<String> list = new ArrayList<String>();
        for (int i = 0; i <= 145920; i++) {
            list.add(i + "");
        }
        int pageSize = 10000;
        int count = list.size();
        int page = count / pageSize;
        int last = count % pageSize;
        for (int i = 0; i <= page; i++) {
            List<String> subList;
            if (i == page) {
                subList = list.subList(i * pageSize, i * pageSize + last);
            } else {
                subList = list.subList(i * pageSize, (i + 1) * pageSize);
            }
            System.out.println(subList.get(0) + "," + subList.get(subList.size() - 1));
        }
    }
}
