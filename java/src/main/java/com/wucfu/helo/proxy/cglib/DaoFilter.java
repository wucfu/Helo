package com.wucfu.helo.proxy.cglib;

import net.sf.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;

/**
 * @ClassName DaoFilter
 * @Description:
 * @Author wuchangfu
 * @Date 2019/12/16 20:26
 **/
public class DaoFilter implements CallbackFilter {
    @Override
    public int accept(Method method) {
        // return 1表示 Callback 列表第1个拦截器,return 2 则为第3个，以此类推
        return  "select".equals(method.getName()) ? 0 : 1;
    }
}