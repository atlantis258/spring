package com.xutp.dynamic.jdk;


import com.xutp.static1.User;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TestJDKProxy {
    public static void main(String[] args) {
        // 创建原始对象
        UserService userService = new UserServiceImpl();
        // invocationHandler的作用：用于书写额外功能
        InvocationHandler handler = new InvocationHandler() {  // 借用一个类加载器，创建代理类的Class对象，进而可以创建代理对象
            @Override
            // Object：原始方法的返回值
            // Proxy：忽略掉，代表的是代理对象
            // Method：额外功能 所增加给的那个原始方法
            // Object[]：原始方法的参数
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                // 调用原始方法
                Object ret = method.invoke(userService, args);

                return ret;
            }
        };

        // JDK创建动态代理
        UserService userServiceProxy = (UserService) Proxy.newProxyInstance(TestJDKProxy.class.getClassLoader(),
                userService.getClass().getInterfaces(),
                handler);

        userServiceProxy.login("root","root");
        userServiceProxy.register(new User());
    }
}
