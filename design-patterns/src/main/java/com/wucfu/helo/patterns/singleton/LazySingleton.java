package com.wucfu.helo.patterns.singleton;

/**
 * @ClassName LazySingleton
 * @Description: 懒汉模式
 * 优点：懒加载
 * 缺点：线程不安全，多线程环境下不能保证单例的唯一性
 * 注意点：为了达到线程安全，可以为getInstance方法加上synchronized关键字。
 * 但是会导致同一时间内只有一个线程能够调用getInstance方法，
 * 即使只需要获取实例也要过锁，使得性能不佳。
 * @Author wuchangfu
 * @Date 2019/12/17 14:19
 **/
public class LazySingleton {

    /**
     * 定义实例但是不直接初始化
     */
    private static LazySingleton instance = null;

    /**
     * 私有构造函数不允许外部new
     */
    private LazySingleton() {
    }

    public static LazySingleton getInstance() {
        // 多线程环境下多个线程同时到达这一步，且instance=null时将会创建多个实例
        if (null == instance) {
            instance = new LazySingleton();
        }
        return instance;
    }
}
