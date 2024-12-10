package com.atlantis;

import com.atlantis.static1.*;
import org.testng.annotations.Test;

public class TestStaticProxy {

    /**
     * 用于测试：静态代理的代码
     */
    @Test
    public void test() {
        UserService userService = new UserServiceProxy();
        userService.login("xutp", "123456");
        userService.register(new User());

        System.out.println("-------------------------");

        OrderService orderService = new OrderServiceProxy();
        orderService.showOrder();
    }
}
