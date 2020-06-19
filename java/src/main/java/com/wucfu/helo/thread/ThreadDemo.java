package com.wucfu.helo.thread;

/**
 * Created by wuchangfu on 2019/5/13.
 */
public class ThreadDemo {
    static Integer i = 9;
    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            try {
                synchronized (i){
                    i.wait();
                    System.out.println(Thread.currentThread().getName()+"进入...");
                    int j = 100;
                    while (j>0){
                        j--;
                        i++;
                        Thread.sleep(50);
                    }
                    System.out.println(Thread.currentThread().getName()+"结束..."+i);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t1.setName("线程1");

        Thread t2 = new Thread(() -> {
            synchronized (i){
                System.out.println(Thread.currentThread().getName()+"释放等待...");
                i.notifyAll();
                System.out.println(Thread.currentThread().getName()+"释放结束...");
            }

        });
        t2.setName("线程2");

        Thread t3 = new Thread(() -> {
            synchronized (i){
                System.out.println(Thread.currentThread().getName()+"进入等待...");
                try {
                    i.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"等待结束...");
            }

        });
        t3.setName("线程3");

        t1.start();
       // t3.start();
         Thread.sleep(500);
        System.out.println(i+","+(i+=10));
        i.notifyAll();
      //  t2.start();

    }
}
