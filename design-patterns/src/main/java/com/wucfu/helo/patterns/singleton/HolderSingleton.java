package com.wucfu.helo.patterns.singleton;

/**
 * @ClassName HolderSingleton
 * @Description: 静态内部类
 * 静态内部类实现单例模式，通过类加载机制保证了单例对象的唯一性
 * 因为实例的建立是在类加载时完成，所以天生对多线程友好，getInstance() 方法也无需使用同步关键字。
 * @Author wuchangfu
 * @Date 2019/12/17 14:23
 **/
public class HolderSingleton {
    private HolderSingleton(){

    }
    private static class Holder{
        private static HolderSingleton instance = new HolderSingleton();
    }
    public  static  HolderSingleton getInstance(){
        return Holder.instance;
    }
}
