package com.wucfu.helo.collection.concurrent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ListDemo {
    /**
     * 并发List:
     * Vector 或者 CopyOnWriteArrayList ,线程安全的List实现。ArrayList 不是线程安全的。
     * 注意：避免在多线程环境中使用ArrayList。若必须使用的，则考虑使用Collections.synchronizedList(List list)进行包装
     * ------
     * Vector:
     * 读写的方法均使用了synchronized，高并发的情况下，大量的锁竞争会拖累系统性能
     * 写操作上，CopyOnWriteArrayList 的写操作性能不如Vector。
     * ------
     * CopyOnWriteArrayList: 读写分离，利用对象的不变性。
     * 进行写操作时，加了锁(ReentrantLock)，并使用Arrays.copyOf()进行了数组复制，写操作的性能开销很大。适合读多写少场景。
     * ------
     * Collections.synchronizedList(List list) 进行包装后，复写了list的读写方法，读写方法上增加了synchronized。
     */
    private static AtomicInteger rmOperation = new AtomicInteger(0);
    private static AtomicInteger addOperation =  new AtomicInteger(0);
    public static void main(String[] args) {

        List<String> collection = new ArrayList<String>();
        Iterator<String> iterator = collection.iterator();
        collection.add("a");
        iterator.next();
        /*List<String> syn = Collections.synchronizedList(new ArrayList<>());
        Iterator<String> iterator1 = syn.iterator();
        syn.add("a");
        iterator1.next();*/


        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(300);
        // 线程安全的list，多线程读写没有问题，Collections.synchronizedList进行包装后，读写方法（get、add、remove）都进行了同步
        /*
        List<String> syncList = Collections.synchronizedList(new ArrayList<String>());
        List<String> syncList = Collections.synchronizedList(new ArrayList<String>());
        for (int i=0; i<50; i++) {
            fixedThreadPool.execute(new MyRunnable("Jack"+i, syncList));
        }
        for (int i=0; i<50; i++) {
            fixedThreadPool.execute(new RmRunnable("Rose"+i, syncList));
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("add times: "+addOperation.get() );
        System.out.println("rm times: "+rmOperation.get() );
        System.out.println("list size is "+ syncList.size());
        */

        /*CopyOnWriteArrayList<String> cowList = new CopyOnWriteArrayList<>();
        for (int i=0; i<50; i++) {
            fixedThreadPool.execute(new MyRunnable("Jack"+i, cowList));
        }
        for (int i=0; i<50; i++) {
            fixedThreadPool.execute(new RmRunnable("Rose"+i, cowList));
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("add times: "+addOperation.get() );
        System.out.println("rm times: "+rmOperation.get() );
        System.out.println("list size is "+ cowList.size());*/

        // 线程不安全的list  多线程读写会报错ConcurrentModificationException
        /*
        List<String> list = new ArrayList();
        for (int i=0; i<50; i++) {
            fixedThreadPool.execute(new MyRunnable("Jack"+i, list));
        }
        for (int i=0; i<50; i++) {
            fixedThreadPool.execute(new RmRunnable("Tom"+i, list));
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("add times: "+addOperation.get() );
        System.out.println("rm times: "+rmOperation.get() );
        System.out.println("list size is "+ list.size());
        */

    }

    static class MyRunnable implements Runnable {
        private String name;
        private List<String> list ;

        public MyRunnable(String name, List<String> list) {
            this.name = name;
            this.list = list;
        }

        @Override
        public String toString() {
            return "MyRunnable{" +
                    "name='" + name + '\'' +
                    ", list=" + list +
                    '}';
        }
        @Override
        public void run() {
            // add
            list.add(name);
            // get
            System.out.println(this+" -> added " + name + ",No." + addOperation.incrementAndGet() + " add");
        }
    }

    static class RmRunnable implements Runnable {
        private String name;
        private List<String> list ;

        public RmRunnable(String name, List<String> list) {
            this.name = name;
            this.list = list;
        }

        @Override
        public String toString() {
            return "RmRunnable{" +
                    "name='" + name + '\'' +
                    ", list=" + list +
                    '}';
        }
        @Override
        public void run() {
            try {
                String s0 = "not get";
                if (!list.isEmpty()){
                    s0 = list.get(0);
                    list.remove(0);
                    System.out.println(this + " -> removed "+ s0 + ",No." + rmOperation.incrementAndGet() + " rm");
                }
            } catch (Exception e){
                System.err.println("removed failed " + e);
            }
        }
    }

}
