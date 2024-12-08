package com.xutp.dynamic.springdynamic;

import com.xutp.static1.User;

public interface UserService {

    void register(User user);

    boolean login(String name, String password);
}
