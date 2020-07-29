package com.wucfu.helo.proxy.jdk;


/**
 * @ClassName UserServiceImpl
 * @Description:
 * @Author wuchangfu
 * @Date 2019/12/16 19:32
 **/
public class UserServiceImpl implements UserService {
    @Override
    public void select() {
        System.out.println("查询 selectById");
    }
    @Override
    public void update() {
        System.out.println("更新 update");
    }
}