package com.xutp.static1;

public class UserServiceProxy implements UserService {

    private UserServiceImpl userService = new UserServiceImpl();

    @Override
    public void register(User user) {
        System.out.println("----额外功能log-----");
        userService.register(user); // 原始类的功能
    }

    @Override
    public boolean login(String name, String password) {
        System.out.println("----额外功能log-----");
        return userService.login(name, password); // 原始类的功能
    }
}
