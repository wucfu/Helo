package com.wucfu.helo.patterns.singleton;

/**
 * @ClassName EnumSingleton
 * @Description: 单例模式的枚举写法《Effective Java》作者力推的方式，jdk1.5以上才适用
 * @Author wuchangfu
 * @Date 2019/12/17 14:24
 **/
public enum  EnumSingleton {
    INSTANCE;
    public void method(){
        //do something
    }
    public static EnumSingleton getInstance(){
        return INSTANCE;
    }
}
