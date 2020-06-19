package com.wucfu.helo.proxy.cglib;

/**
 * @ClassName UserDao
 * @Description:
 * @Author wuchangfu
 * @Date 2019/12/16 20:03
 **/
public class UserDao {
    public void select() {
        System.out.println("UserDao 查询 selectById");
    }
    public void update() {
        System.out.println("UserDao 更新 update");
    }
}