package com.wucfu.helo.patterns.singleton;

/**
 * @ClassName HungerSingleton
 * @Description: 饿汉模式
 * 优点：写法简单，可以保证多个线程下的唯一实例，getInstance方法性能较高。
 * 缺点：无懒加载，类加载的时候单例对象就产生了，如果类成员占有的资源比较多，这种方法较为不妥。
 * 注意点：实现了Serializable接口后，反序列化时单例会被破坏。
 * 如果实现Serializable接口，那么需要重写readResolve，才能保证其反序列化依旧是单例
 * @Author wuchangfu
 * @Date 2019/12/17 14:17
 **/
public class HungerSingleton {


    private static final HungerSingleton instance = new HungerSingleton();

    /**
     * 私有构造
     */
    private HungerSingleton(){
    }

    public static HungerSingleton getInstance(){
        return instance;
    }

    /*
    // 如果实现了Serializable, 必须重写这个方法
    private Object readResolve() throws ObjectStreamException {
        return instance;
    }*/
}
