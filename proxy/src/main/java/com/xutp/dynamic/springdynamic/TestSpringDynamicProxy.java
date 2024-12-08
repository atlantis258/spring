package com.xutp.dynamic.springdynamic;

import com.xutp.static1.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpringDynamicProxy {

    /**
     * 用于测试：Spring的动态代理
     */
    @Test
    public void test() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
        UserService userService = (UserService) ctx.getBean("userService");

        userService.login("root", "root");
        userService.register(new User());
    }

    /**
     * 用于测试：OrderService动态代理
     */
    @Test
    public void test2() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
        OrderService orderService = (OrderService) ctx.getBean("orderService");

        orderService.showOrder();
    }
}
