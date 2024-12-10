package com.atlantis;

import com.atlantis.dao.UserDAO;
import com.atlantis.entity.User;
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
