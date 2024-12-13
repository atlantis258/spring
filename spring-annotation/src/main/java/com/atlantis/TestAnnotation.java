package com.atlantis;

import com.atlantis.entity.User;
import com.atlantis.injection.Category;
import com.atlantis.injection.UserService;
import com.atlantis.lazy.Account;
import com.atlantis.scope.Customer;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAnnotation {

    /**
     * 测试 @Component
     */
    @Test
    public void test() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
        User user = (User) ctx.getBean("u");

        System.out.println("user id: " + user.getId());
    }

    /**
     * 测试 @Scope
     */
    @Test
    public void test2() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
        Customer customer = (Customer) ctx.getBean("customer");
        Customer customer2 = (Customer) ctx.getBean("customer");

        System.out.println("customer = " + customer);
        System.out.println("customer2 = " + customer2);
    }

    /**
     * lazy注解
     */
    @Test
    public void test3() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
        Account account = (Account) ctx.getBean("account");
    }

    /**
     * 生命周期 注解
     */
    @Test
    public void test4() {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
        ctx.close();
    }

    /**
     * Autowired注解
     */
    @Test
    public void test5() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
        UserService userServiceImpl = (UserService) ctx.getBean("userServiceImpl");

        userServiceImpl.register();
    }

    /**
     * 测试：Value
     */
    @Test
    public void test6() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
        Category category = (Category) ctx.getBean("category");
        System.out.println("category.getId() = " + category.getId());
        System.out.println("category.getName() = " + category.getName());
    }

    @Test
    public void test7() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
        String[] beanDefinitionNames = ctx.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println("beanDefinitionName = " + beanDefinitionName);
        }
    }
}
