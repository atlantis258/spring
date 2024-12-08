package com.xutp;

import com.xutp.dao.UserDAO;
import com.xutp.entity.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class TestMybatisSpring {
    @Test
    public void test() throws IOException {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
        UserDAO userDAO = (UserDAO) ctx.getBean("userDAO");

        User user = new User();
        user.setName("111");

        userDAO.save(user);
    }
}
