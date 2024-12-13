package com.atlantis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.sql.Connection;
import java.sql.DriverManager;

@Configuration
public class AppConfig {

    /**
     * 简单对象
     *
     * @return
     */
    @Bean("u")
//    @Scope("prototype")
    public User user() {
        return new User();
    }

    /**
     * 创建复杂对象
     * @return 不能直接通过 new 创建
     */
    @Bean
    public Connection conn() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/spring_mybatis?useSSL=false", "root", "root");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    @Bean
    public Connection conn2() {
        Connection conn2 = null;
        try {
            ConnectionFactoryBean factoryBean = new ConnectionFactoryBean();
            conn2 = factoryBean.getObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return conn2;
    }
}
