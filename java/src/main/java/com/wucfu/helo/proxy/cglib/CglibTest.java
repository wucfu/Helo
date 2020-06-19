package com.wucfu.helo.proxy.cglib;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

/**
 * @ClassName CglibTest
 * @Description:
 * @Author wuchangfu
 * @Date 2019/12/16 20:05
 **/
public class CglibTest {
    public static void main(String[] args) {
        UserDaoProxy userDaoProxy = new UserDaoProxy();
        UserDaoProxy userDaoProxy2 = new UserDaoProxy();
        Enhancer enhancer = new Enhancer();
        // 设置超类，cglib是通过继承来实现的
        enhancer.setSuperclass(UserDao.class);
        enhancer.setCallback(userDaoProxy);

//        enhancer.setCallbacks(new Callback[]{userDaoProxy, userDaoProxy2, NoOp.INSTANCE});   // 设置多个拦截器，NoOp.INSTANCE是一个空拦截器，不做任何处理
//        enhancer.setCallbackFilter(new DaoFilter());

        // 创建代理类
        UserDao dao = (UserDao)enhancer.create();
        dao.update();
        dao.select();
    }
}
