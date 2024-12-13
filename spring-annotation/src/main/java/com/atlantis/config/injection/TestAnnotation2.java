package com.atlantis.config.injection;

import com.atlantis.config.AppConfig;
import com.atlantis.config.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.Connection;

public class TestAnnotation2 {

    /**
     * 测试：配置bean
     */
    @Test
    public void test() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
//        ApplicationContext ctx = new AnnotationConfigApplicationContext("com.atlantis.config");
    }

    /**
     * 测试：配置@Bean注解
     */
    @Test
    public void test2() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
//        User user = (User) ctx.getBean("user");
//        System.out.println("user = " + user);

//        Connection conn = (Connection) ctx.getBean("conn");
//        System.out.println("conn = " + conn);

        Connection conn2 = (Connection) ctx.getBean("conn2");
        System.out.println("conn2 = " + conn2);
    }

    /**
     * 测试：对象的创建次数
     */
    @Test
    public void test3() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

        User u1 = (User) ctx.getBean("u");
        User u2 = (User) ctx.getBean("u");

        System.out.println("u1 = " + u1);
        System.out.println("u2 = " + u2);
    }

    /**
     * 用于测试：@Bean注解的注入
     */
    @Test
    public void test4() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig2.class);

        UserService userService = (UserService) ctx.getBean("userService");
        userService.register();
    }

    /**
     * @Bean JDK类型注入
     */
    @Test
    public void test5() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig2.class);

        Customer customer = (Customer) ctx.getBean("customer");
        System.out.println("customer.getId() = " + customer.getId());
        System.out.println("customer.getName() = " + customer.getName());
    }
}
