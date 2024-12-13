package com.atlantis.pureaop;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestAnnotation4 {

    @Test
    public void test() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        UserService userService = (UserService) ctx.getBean("userServiceImpl");

        userService.register();
        userService.login();
    }
}
