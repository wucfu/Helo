package com.wucfu.helo.patterns.singleton;

/**
 * @ClassName DoubleCheckedSingleton
 * @Description: 双重校验单例模式
 * @Author wuchangfu
 * @Date 2019/12/17 14:22
 **/
public class DoubleCheckedSingleton {

    /**
     * 定义实例但是不直接初始化,volatile禁止重排序操作，避免空指针异常：
     *
     * 主要在于instance = new Singleton()这句，这并非是一个原子操作，事实上在JVM中该语句会分为三个步骤：
     * 1.分配空间：给 instance 分配内存
     * 2.初始化：调用 Singleton 的构造函数来初始化成员变量
     * 3.引用赋值：将instance对象指向分配的内存空间（执行完这步 instance 就为非 null 了）
     * 由于在JVM的即时编译器中存在指令重排序的优化。也就是说上面的第二步和第三步的顺序是不能保证的，
     * 最终的执行顺序可能是 1-2-3 也可能是 1-3-2。
     * 如果是后者，那么在 3 执行完毕，2 未执行之前，被线程二抢占了，这时 instance 已经是非 null 了（但却没有初始化），
     * 所以线程二会直接返回 instance，一旦使用便引发空指针异常。
     * 特别注意在 Java 5 以前的版本使用了 volatile 的双检锁还是有问题的。其原因是 Java 5 以前的 JMM （Java 内存模型）是存在缺陷的，即时将变量声明成 volatile 也不能完全避免重排序，主要是 volatile 变量前后的代码仍然存在重排序问题。这个 volatile 屏蔽重排序的问题在 Java 5 中才得以修复，所以在这之后才可以放心使用 volatile。
     */
    private static volatile DoubleCheckedSingleton instance = null;

    /**
     * 私有构造
     */
    private DoubleCheckedSingleton(){
    }
    public static DoubleCheckedSingleton getInstance(){
        if(null==instance){
            synchronized (DoubleCheckedSingleton.class){
                if (null==instance) {
                    instance = new DoubleCheckedSingleton();
                }
            }
        }
        return instance;
    }
}
