package com.atlantis.dynamic.cglib;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class TestCglib {

    public static void main(String[] args) {
        // 1. 创建原始对象
        UserService userService = new UserService();

        /**
         * 2. 通过cglib方法创建动态代理对象
         * Proxy.newProxyInstance(classloader, interface, invocationhandler)
         *
         * Enhancer enhancer = new Enhancer();
         * enhancer.setClassLoader();
         * enhancer.setSuperclass();  ---->  MethodInterceptor(cglib)
         * enhancer.create() --> 代理
         */

        Enhancer enhancer = new Enhancer();
        enhancer.setClassLoader(TestCglib.class.getClassLoader());
        enhancer.setSuperclass(userService.getClass());

        MethodInterceptor interceptor = new MethodInterceptor() {
            // 等同于 InvocationHandler --- invoke
            // method 原始方法
            // objects 原始方法的参数
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                // 额外功能
                System.out.println("----cglib log-----");

                // 调用原始方法
                Object ret = method.invoke(userService, objects);
                return ret;
            }
        };
        enhancer.setCallback(interceptor);

        UserService userServiceProxy = (UserService) enhancer.create();
        userServiceProxy.login("xutp", "123456");
        userServiceProxy.register(new User());
    }
}
