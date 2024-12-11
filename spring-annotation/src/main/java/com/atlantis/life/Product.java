package com.atlantis.life;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class Product {

    @PostConstruct
    public void myInit() {
//        System.out.println("Product.myInit");
    }

    @PreDestroy
    public void myDestroy() {
//        System.out.println("Product.myDestroy");
    }
}
