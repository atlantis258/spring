package com.atlantis.dynamic.springdynamic;

import com.atlantis.static1.User;

public class UserServiceImpl implements UserService {
    @Override
    public void register(User user) {
        System.out.println("UserServiceImpl.register + 业务运算 + DAO");
    }

    @Override
    public boolean login(String name, String password) {
        System.out.println("UserServiceImpl.login");
        return false;
    }
}
