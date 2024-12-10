package com.atlantis.dynamic.springdynamic;

public class OrderServiceImpl implements OrderService {
    @Override
    public void showOrder() {
        System.out.println("OrderServiceImpl.showOrder + 业务运算 + DAO");
    }
}
