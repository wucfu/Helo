package com.wucfu.helo.thread;

import java.util.Calendar;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolDemo {
    /**
     * 1.Java通过Executors提供四种线程池，分别为：
     *  newCachedThreadPool 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
     *  newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
     *  newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
     *  newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
     * 2.对于服务端长期运行的程序，创建线程池应该直接使用ThreadPoolExecutor的构造方法
     */
    public static void main(String[] args) {
        /**
         * newCachedThreadPool
         * 缓存线程池
         * 适用场景：快速处理大量耗时较短的任务，如Netty的NIO接受请求
         */
       /* ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
       for (int i=0; i<5; i++) {
            cachedThreadPool.execute(new MyRunnable());
        }*/
        /**===================================================================================================*/
        /**
         * newFixedThreadPool
         * 创建固定大小的线程池
         * 如果任务提交速度持续大余任务处理速度，会造成队列大量阻塞。因为队列很大，很有可能在拒绝策略前，内存溢出.
         * 任务执行是无序的
         * 适用场景：可用于Web服务瞬时削峰，但需注意长时间持续高峰情况造成的队列阻塞
         */
       /* ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);
        for (int i=0; i<4; i++) {
            fixedThreadPool.execute(new MyRunnable(""+ ((char)(65+i))));
        }
        System.out.println("fixedThreadPool is shutdown :"+fixedThreadPool.isShutdown());
        fixedThreadPool.shutdown();
        System.out.println("after shut, fixedThreadPool is shutdown :"+fixedThreadPool.isShutdown());*/

        /**===================================================================================================*/
        /**
         * newScheduledThreadPool
         * 定时执行的线程池
         */
        /*
        System.out.println("before scheduledThreadPool execute : " + Calendar.getInstance().getTime());
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(2);
        for (int i=0; i<3; i++) {
            scheduledThreadPool.schedule(new MyRunnable(), 3, TimeUnit.SECONDS);
        }
        // 第一次延迟2秒执行，后面定时每3秒执行一次已提交的线程
        scheduledThreadPool.scheduleAtFixedRate(new MyRunnable("A"), 2, 3,  TimeUnit.SECONDS);
        // 第一次延迟3秒，此后都延迟2秒
        scheduledThreadPool.scheduleWithFixedDelay(new MyRunnable("A"), 3, 2,  TimeUnit.SECONDS);
        */
        /**===================================================================================================*/
        /**
         * newSingleThreadExecutor
         * 创建只有一个线程的线程池
         */
        /*
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        for (int i=0; i<3; i++) {
            singleThreadExecutor.execute(new MyRunnable(""+ ((char)(65+i))));
        }*/
        /**===================================================================================================*/
        /**
         * ThreadPoolExecutor
         * 构造函数参数：
         * int corePoolSize, 线程池长期维持的线程数，即使线程处于Idle状态，也不会回收。除非设置了allowCoreThreadTimeOut
         * int maximumPoolSize, 线程数上限
         * long keepAliveTime, 当线程数超过corePoolSize时，空闲线程将等待的时长
         * TimeUnit unit, keepAliveTime时长单位
         * BlockingQueue<Runnable> workQueue, 用于保存提交的线程，即线程排队队列
         * ThreadFactory threadFactory, 新线程产生的方式
         * RejectedExecutionHandler handler 拒绝策略
         * 工作顺序：corePoolSize -> 任务队列 -> maximumPoolSize -> 拒绝策略
         * @see ThreadPoolExecutor
         * 线程池提供的几种常见拒绝策略:
         * AbortPolicy	抛出RejectedExecutionException, 默认的策略，非受检异常，容易忘记捕获
         * DiscardPolicy	什么也不做，直接忽略
         * DiscardOldestPolicy	丢弃执行队列中最老的任务，尝试为当前提交的任务腾出位置
         * CallerRunsPolicy	直接由提交任务者执行这个任务
         */
        // int poolSize = Runtime.getRuntime().availableProcessors() * 2;
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(2);
        queue.add(new MyRunnable("queRun"));
        RejectedExecutionHandler policy = new ThreadPoolExecutor.DiscardPolicy();
        RejectedExecutionHandler myReject = new MyRejectedHandler();
        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(
                        2,
                        4,
                        0,
                        TimeUnit.SECONDS,
                        queue,
                        Executors.defaultThreadFactory(),
                        myReject);
        for (int i=0; i<10; i++) {
            threadPoolExecutor.execute(new MyRunnable(createAbcNameByNum(i)));
        }


    }

    static String createAbcNameByNum(int i){
        return ""+ ((char)(65+i));
    }

    static class MyRunnable implements Runnable{
        private String name;
        @Override
        public void run() {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Calendar.getInstance().getTime()+","+Thread.currentThread().getName()+"-"+name+"-run...");
        }
        public MyRunnable(String name) {
            super();
            this.name = name;
        }
        public MyRunnable(){
            super();
        }
    }
    static class MyThread extends Thread {

    }

    /**
     * 线程工厂
     */
    static class MyThreadFactory implements ThreadFactory{
        private final AtomicInteger mThreadNum = new AtomicInteger(1);
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "my-thread-" + mThreadNum.getAndIncrement());
            System.out.println(t.getName() + " has been created");
            return t;
        }
    }

    /**
     * 拒绝策略
     */
    static class MyRejectedHandler implements RejectedExecutionHandler{

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.err.println(r + "is rejected ");
        }
    }

}
