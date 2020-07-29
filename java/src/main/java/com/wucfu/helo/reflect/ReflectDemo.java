package com.wucfu.helo.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * @ClassName ReflectDemo
 * @Description:
 * @Author wuchangfu
 * @Date 2019/11/29 16:27
 **/
public class ReflectDemo {
    public static void main(String[] args) throws Exception {
        //解析<bean .../>元素的id属性得到该字符串值为"sqlSessionFactory"
        String idStr = "person";
        //解析<bean .../>元素的class属性得到该字符串值为"org.mybatis.spring.SqlSessionFactoryBean"
        String classStr = "com.demo.common.bean.Person";
        //利用反射知识，通过classStr获取Class类对象
        Class cls = Class.forName(classStr);
        //实例化对象
        Object obj = cls.newInstance();
        //container表示Spring容器
        HashMap<String, Object> container = new HashMap<>();

        container.put(idStr, obj);

        //当一个类里面需要用另一类的对象时，我们继续下面的操作

        //解析<property .../>元素的name属性得到该字符串值为“dataSource”
        String nameStr = "person";
        //解析<property .../>元素的ref属性得到该字符串值为“dataSource”
        String refStr = "person";
        //生成将要调用setter方法名
        String setterName = "set" + nameStr.substring(0, 1).toUpperCase()
                + nameStr.substring(1);
        setterName = "setName";
        //获取spring容器中名为refStr的Bean，该Bean将会作为传入参数
        Object paramBean = container.get(refStr);
        //获取setter方法的Method类，此处的cls是刚才反射代码得到的Class对象
        Method setter = cls.getMethod(setterName, String.class);
        //调用invoke()方法，此处的obj是刚才反射代码得到的Object对象
        setter.invoke(paramBean, "你的名字");
        System.out.println(paramBean);

    }
}
