package com.atlantis.static1;

public class OrderServiceProxy implements OrderService {
    private OrderServiceImpl orderService = new OrderServiceImpl();

    @Override
    public void showOrder() {
        System.out.println("----额外功能log-----");
        orderService.showOrder();
    }
}
