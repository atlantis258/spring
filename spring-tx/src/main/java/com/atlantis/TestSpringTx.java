package com.atlantis;

import com.atlantis.dao.UserDAO;
import com.atlantis.entity.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpringTx {

    /**
     * 用于测试：Spring的事务处理
     */
    @Test
    public void test() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
        UserService userService = (UserService) ctx.getBean("userService");

        User user = new User();
        user.setName("haha");
        userService.register(user);

    }
}
